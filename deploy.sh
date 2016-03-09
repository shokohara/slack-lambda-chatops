#!/bin/bash -eu
# Required variables
# FUNCTION_NAME
# AWS_ID
# LAMBDA_ROLE
# LAMBDA_HANDLER
projectName=$(sbt -no-colors name | tail -n1 | cut -d' ' -f2)
projectVersion=$(sbt -no-colors version | tail -n1 | cut -d' ' -f2)

# S3_BUCKET
sbt assembly && \
aws s3 cp target/scala-2.11/$projectName-assembly-${projectVersion}.jar s3://${S3_BUCKET}/${projectVersion}.jar && \
aws lambda update-function-code --function-name $FUNCTION_NAME --s3-bucket $S3_BUCKET --s3-key ${projectVersion}.jar

#sbt clean assembly && \
#aws lambda update-function-code \
#  --function-name $FUNCTION_NAME \
#  --zip-file fileb://target/scala-2.11/$projectName-assembly-${projectVersion}.jar


#fileUrl="fileb://target/scala-2.11/${projectName}-assembly-${projectVersion}.jar"
#sbt clean assembly && \
#aws lambda create-function \
#  --function-name $FUNCTION_NAME \
#  --zip-file fileb://target/scala-2.11/${projectName}-assembly-${projectVersion}.jar \
#  --role arn:aws:iam::$AWS_ID:role/$LAMBDA_ROLE \
#  --handler $LAMBDA_HANDLER \
#  --runtime java8 \
#  --timeout 30 \
#  --memory-size 512
