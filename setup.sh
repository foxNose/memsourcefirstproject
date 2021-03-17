#!/bin/bash
source .env
# Install Java JDK
sudo apt install openjdk-${JAVA_VERSION}-jdk
# Install grails
curl -s https://get.sdkman.io | bash
source "${SDKMAN_DIR}/.sdkman/bin/sdkman-init.sh"
sdk install grails ${GRAILS_VERSION}
