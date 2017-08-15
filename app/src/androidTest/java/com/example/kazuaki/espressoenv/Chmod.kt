package com.example.kazuaki.espressoenv

import android.os.Build
import android.support.test.filters.SdkSuppress
import java.io.File

@SdkSuppress(minSdkVersion = Build.VERSION_CODES.GINGERBREAD)
class Chmod {
    fun plusR(file: File) {
        file.apply { setReadable(true) }
    }

    fun plusRWX(file: File) {
        file.apply {
            setReadable(true)
            setExecutable(true)
            setWritable(true)
        }
    }
}
