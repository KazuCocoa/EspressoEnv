#!/bin/bash
source ~/.bash_profile

SWARMER_VERSION="0.2.4"
curl --fail --location https://jcenter.bintray.com/com/gojuno/swarmer/swarmer/${SWARMER_VERSION}/swarmer-${SWARMER_VERSION}.jar --output /tmp/swarmer.jar

java -jar /tmp/swarmer.jar start \
--emulator-name test1 \
--package "system-images;android-26;google_apis;x86_64" \
--android-abi google_apis/x86_64 \
--path-to-config-ini scripts/emulator_config.ini \
--emulator-start-options -netdelay none -netspeed full -screen touch -no-audio -no-screen -prop persist.sys.language=en -prop persist.sys.country=US
--emulator-name test2 \
--package "system-images;android-26;google_apis;x86_64" \
--android-abi google_apis/x86_64 \
--path-to-config-ini scripts/emulator_config.ini \
--emulator-start-options -netdelay none -netspeed full -screen touch -no-audio -no-screen -prop persist.sys.language=en -prop persist.sys.country=US
--emulator-name test3 \
--package "system-images;android-26;google_apis;x86_64" \
--android-abi google_apis/x86_64 \
--path-to-config-ini scripts/emulator_config.ini \
--emulator-start-options -netdelay none -netspeed full -screen touch -no-audio -no-screen -prop persist.sys.language=en -prop persist.sys.country=US
