## Introduction

This second demo takes our code from demo1, where we wrote a simple JAX-RS webservice in a Jersey framework  / Embedded Tomcat, replacing Tomcat with a JerseyLambdaContainerHandler. So, we still create a jersey ResourceConfig, but instead of "feeding" that to Tomcat, its provided to AWS using the getAwsProxyHandler static method: 

```
private static final JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler
            = JerseyLambdaContainerHandler.getAwsProxyHandler(jerseyApplication);
```



## Getting started

```
$ gradle clean

$ gradle build

$ gradle shadowJar
```

```
$ aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket <bucketname> --profile <username><br/>
Uploading to 01234567897abcdef0123456789  9027778 / 9027778.0  (100.00%)
Successfully packaged artifacts and wrote output template to file output-sam.yaml.
Execute the following command to deploy the packaged template
aws cloudformation deploy --template-file /<somepath>/output-sam.yaml --stack-name <YOUR STACK NAME>
```

```
$ aws cloudformation deploy --template-file /<somepath>/output-sam.yaml --stack-name test --region <region_name> --capabilities CAPABILITY_IAM --profile <username>
Waiting for changeset to be created..
Waiting for stack create/update to complete
Successfully created/updated stack - test
```

```
$ aws cloudformation describe-stacks --region <region_name> --stack-name test --query 'Stacks[0].Outputs[*].{Service:OutputKey,Endpoint:OutputValue}' --profile <username><br/>
[
    {
        "Service": "JerseySampleApi",
        "Endpoint": "https://abcdef0123.execute-api.someregion.amazonaws.com/Prod/test/"
    }
]
```

```
## Test the result:

$ curl https://abcdef0123.execute-api.someregion.amazonaws.com/Prod/test/something
Returning something. It worked!
```
