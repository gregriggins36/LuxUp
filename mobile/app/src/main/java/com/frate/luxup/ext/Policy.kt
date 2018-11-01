package com.frate.luxup.ext

import android.os.StrictMode

inline fun <T> allowRead(block: () -> T): T {
    try {
        return block()
    } finally {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .build())
    }
}
