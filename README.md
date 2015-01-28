[![Build Status](https://travis-ci.org/simple-recipe-manager/srm-api.svg?branch=master)](https://travis-ci.org/simple-recipe-manager/srm-api)

This is the API repo for Simple Recipe Manager

For a local run

'mvn package'

'java -jar target\SRM-API.jar server local.yaml'

Ensure you've updated local.yaml with the credentials for Dynamo.

When running on EC2, it prefers the instance credentials over the locally
provided credentials. This does imply that EC2 instances should have a role
that access to the resources needed.
