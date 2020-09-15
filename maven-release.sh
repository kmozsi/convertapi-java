#!/bin/sh

branch_to_release="master"

echo "Cloning convertapi-java..."
git clone https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/kmozsi/convertapi-java.git
cd convertapi-java

echo "Switching to the release branch..."
git fetch
git checkout $branch_to_release

echo "Preparing release..."
mvn release:prepare -DreleaseVersion=${VERSION} -DdevelopmentVersion=${NEXT_VERSION} -Dtag=v${VERSION} -Dresume=false -Darguments=-Dgpg.passphrase=${PASSPHRASE}

echo "Performing release..."
mvn release:perform

echo "Push the new release tag..."
git push --tags

echo "Push release commits..."
git push
