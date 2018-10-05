#!/bin/bash
source ~/.bash_profile

SWARMER_VERSION="0.2.4"
curl --fail --location https://jcenter.bintray.com/com/gojuno/swarmer/swarmer/${SWARMER_VERSION}/swarmer-${SWARMER_VERSION}.jar --output /tmp/swarmer.jar

# Sometimes emulators don't stop only swarmer
adb devices | grep emulator | cut -f1 | while read line; do adb -s $line emu kill; done

# wait stoping all emulators
java -jar /tmp/swarmer.jar stop

