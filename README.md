## Introduction

This second demo takes our code from demo1, where we wrote a simple JAX-RS webservice in a Jersey framework  / Embedded Tomcat, replacing Tomcat with a JerseyLambdaContainerHandler. So, we still create a jersey ResourceConfig, but instead of "feeding" that to Tomcat, its provided to AWS using the getAwsProxyHandler static method: 

private static final JerseyLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler
            = JerseyLambdaContainerHandler.getAwsProxyHandler(jerseyApplication);



## Getting started

$ git clone https://github.com/mr-t-73/demo2.git

$ aws cloudformation package --template-file sam.yaml --output-template-file output-sam.yaml --s3-bucket <YOUR S3 BUCKET NAME>

$ aws cloudformation deploy --template-file output-sam.yaml --stack-name ServerlessJerseyApi --capabilities CAPABILITY_IAM

$ aws cloudformation describe-stacks --stack-name ServerlessJerseyApi --query 'Stacks[0].Outputs[*].{Service:OutputKey,Endpoint:OutputValue}'
[
    {
		"Service": "JerseySampleApi",
		"Endpoint": "https://xxxxxxx.execute-api.us-west-2.amazonaws.com/Prod/test"
    }
]


## Run the Embedded Tomcat fat jar:

java -jar build/libs/webservice-1.0-SNAPSHOT-all.jar
May 13, 2020 4:41:55 PM org.apache.catalina.core.StandardContext setPath
WARNING: A context path must either be an empty string or start with a '/' and do not end with a '/'. The path [/] does not meet these criteria and has been changed to []
May 13, 2020 4:41:56 PM org.apache.coyote.AbstractProtocol init
INFO: Initializing ProtocolHandler ["http-nio-8222"]
May 13, 2020 4:41:56 PM org.apache.tomcat.util.net.NioSelectorPool getSharedSelector
INFO: Using a shared selector for servlet write/read
May 13, 2020 4:41:56 PM org.apache.catalina.core.StandardService startInternal
INFO: Starting service Tomcat
May 13, 2020 4:41:56 PM org.apache.catalina.core.StandardEngine startInternal
INFO: Starting Servlet Engine: Apache Tomcat/8.0.28
May 13, 2020 4:41:56 PM org.apache.coyote.AbstractProtocol start
INFO: Starting ProtocolHandler ["http-nio-8222"]


## In a separate window:

$ curl localhost:8222/json/test/blablablaaa -H "Accept: application/json"
Returning blablablaaa. It worked!
