#!/bin/bash

# Run tests with multiple modules in parallel

# Wait all sub-processes
function wait_and_get_exit_codes() {
    children=("$@")
    EXIT_CODE=0
    for job in "${children[@]}"; do
       echo "PID => ${job}"
       CODE=0;
       wait ${job} || CODE=$?
       if [[ "${CODE}" != "0" ]]; then
           echo "At least one test failed with exit code => ${CODE}" ;
           EXIT_CODE=1;
       fi
   done
}

# To manage test results of sub-processes
EXIT_CODE=0
children_pids=()

# Run tests by modules
TEST_TARGET_MODULES=("module1" "module2" "module3")
EMULATOR_PORT=5554

# setup
# Start ${#TEST_TARGET_MODULES[@]} emulators
./script/setup_emulator.sh

./gradlew assembleDebugAndroidTest -PdisablePreDex

for module in ${TEST_TARGET_MODULES[@]}; do
    java -jar /tmp/composer.jar \
      --apk path/to/app-debug.apk \
      --test-apk ${module}/build/outputs/apk/androidTest/debug/${module}-debug-androidTest.apk \
      --output-directory artifacts/composer-output-${module} \
      --instrumentation-arguments disableAnalytics true \ # any options of instrumentation
      --devices emulator-${EMULATOR_PORT} \ # specify devices
      --shard false \
      &
    children_pids+=("$!")

    EMULATOR_PORT=$((EMULATOR_PORT += 2)) # An emulator use two consecutive port number like 5554 and 5555
done

wait_and_get_exit_codes "${children_pids[@]}"

# teardown
# Stop all emulators
java -jar /tmp/swarmer.jar stop

echo "EXIT_CODE => ${EXIT_CODE}"
exit "${EXIT_CODE}"
