#!/bin/bash

docker build -t sunshower-sdk -f Dockerfile .   
docker run -e MVN_REPO_USERNAME=myMavenRepo \
-e MVN_REPO_PASSWORD=lid-DOG-bin-123 -it --rm \
-e MVN_REPO_URL=https://mymavenrepo.com/repo/IRgrTxMdF4OnnbNAkfnJ \
--name "sunshower-sdk" "sunshower-sdk" \


