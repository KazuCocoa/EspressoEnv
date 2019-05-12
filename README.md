# EspressoEnv

[![Build Status](https://dev.azure.com/kazucocoa/EspressoEnv/_apis/build/status/KazuCocoa.EspressoEnv?branchName=master)](https://dev.azure.com/kazucocoa/EspressoEnv/_build/latest?definitionId=5?branchName=master)

Templates to help Espresso tests.

# Running
- Collect and manage the results
    - Composer
        - https://github.com/gojuno/composer

# Example

```
$ ./gradlew test
$ ./gradlew connectedAndroidTest
$ ./gradlew benchmarkReport # benchmark
```

# To run benchmark against arbitrary module
- Add the target module into the benchmark module as a library
    - https://developer.android.com/studio/projects/android-library#Convert
- Or add benchmark dependencies into the target module
    - Make sure below attributes is in the test Manidest in order to disable debug features which affects performance
    ```
    <application
        android:debuggable="false"
        tools:ignore="HardcodedDebugMode"
        tools:replace="android:debuggable" />
    ```
    - https://www.youtube.com/watch?v=ZffMCJdA5Qc&feature=youtu.be&t=1859
