FROM openjdk:8-jdk-alpine

ENV VERSION=
ENV NEXT_VERSION=
ENV GPG_PASSPHRASE=
ENV GIT_USERNAME=
ENV GIT_PASSWORD=
ARG GIT_EMAIL=
ARG FULL_NAME=

ARG MAVEN_VERSION=3.3.9
ARG USER_HOME_DIR="/root"
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

VOLUME /tmp

COPY secret.key /secret.key
COPY settings.xml "$MAVEN_CONFIG/settings.xml"
COPY maven-release.sh /maven-release.sh

# Install Maven, Git and gpg
RUN apk add --no-cache curl tar bash git gnupg
RUN mkdir -p /usr/share/maven && \
	curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
RUN git config --global user.email ${GIT_EMAIL} && \
 	git config --global user.name ${FULL_NAME}

# Configure gpg
RUN gpg --allow-secret-key-import --batch --import secret.key
RUN export GPG_TTY=/dev/console
RUN export GPG_OPTS='--pinentry-mode loopback'
RUN echo "pinentry-mode loopback" > ~/.gnupg/gpg.conf

RUN ["chmod", "+x", "/maven-release.sh"]
ENTRYPOINT /maven-release.sh
