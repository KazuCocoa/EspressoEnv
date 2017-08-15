package com.example.kazuaki.espressoenv

import android.os.Build
import android.support.test.filters.SdkSuppress
import java.io.File

@SdkSuppress(minSdkVersion = Build.VERSION_CODES.GINGERBREAD)
class Chmod {
    fun plusR(file: File) {
        file.setReadable(true)
    }

    fun plusRWX(file: File) {
        file.setReadable(true)
        file.setExecutable(true)
        file.setWritable(true)
    }
}
