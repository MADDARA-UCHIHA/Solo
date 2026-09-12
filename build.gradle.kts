buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
    // kapt o'rniga KSP ni qo'shamiz (versiyasi Kotlin 2.0.0 ga mos bo'lishi kerak):
    id("com.google.devtools.ksp") version "2.0.0-1.0.21" apply false
}