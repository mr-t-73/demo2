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
Uploading to 01234567897abcdef0123456789  9027778 / 9027778.0  (100.00%)<br/>
Successfully packaged artifacts and wrote output template to file output-sam.yaml.<br/>
Execute the following command to deploy the packaged template<br/>
aws cloudformation deploy --template-file /<somepath>/output-sam.yaml --stack-name <YOUR STACK NAME><br/>
```

```
$ aws cloudformation deploy --template-file /<somepath>/output-sam.yaml --stack-name test --region <region_name> --capabilities CAPABILITY_IAM --profile <username><br/>
Waiting for changeset to be created..<br/>
Waiting for stack create/update to complete<br/>
Successfully created/updated stack - test<br/>
```

```
$ aws cloudformation describe-stacks --region <region_name> --stack-name test --query 'Stacks[0].Outputs[*].{Service:OutputKey,Endpoint:OutputValue}' --profile <username><br/>
[<br/>
    {<br/>
        "Service": "JerseySampleApi",<br/>
        "Endpoint": "https://abcdef0123.execute-api.someregion.amazonaws.com/Prod/test/"<br/>
    }<br/>
]<br/>
```

```
## Test the result:

$ curl https://abcdef0123.execute-api.someregion.amazonaws.com/Prod/test/something<br/>
Returning something. It worked!
```
