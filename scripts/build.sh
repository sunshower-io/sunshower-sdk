#!/bin/bash

rm -rf ~/.m2/repository
source /scripts/set-version.sh

mvn clean install deploy -f bom/pom.xml -Denv.version=1.0.0-SNAPSHOT
#mvn versions:set -DnewVersion=${VERSION} -f bom/pom.xml
gradle clean build pTML publish -i \
-PmavenRepositoryUrl=${MVN_REPO_URL} \
-PmavenRepositoryUsername=${MVN_REPO_USERNAME} \
-PmavenRepositoryPassword=${MVN_REPO_PASSWORD} \
-Pversion=1.0.0-SNAPSHOT \
-Penv.version=1.0.0-SNAPSHOT \
--refresh-dependencies
    
