#!/bin/sh

# Usage: ./maven-release.sh v2.0 2.1-SNAPSHOT 2.0

branch_to_release="master"

echo "Cloning convertapi-java..."
git clone https://github.com/kmozsi/convertapi-java.git
cd convertapi-java

echo "Switching to the release branch..."
git fetch
git checkout $branch_to_release

echo "Pulling changes..."
git pull --ff-only

echo "Push commits..."
git push

echo "Revert uncommitted changes..."
git checkout .

echo "Cleaning..."
mvn clean

echo "Preparing release..."
mvn release:prepare -DreleaseVersion=${VERSION} -DdevelopmentVersion=${NEXT_VERSION} -Dtag=v${VERSION} -Dresume=false -Darguments=-Dgpg.passphrase=${PASSPHRASE}

echo "Performing release..."
mvn release:perform

echo "Push the new release tag..."
git push --tags

echo "Push release commits..."
git push
