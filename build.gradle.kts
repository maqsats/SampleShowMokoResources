plugins {
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.cocoapods).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.libres).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.parcelize).apply(false)
    alias(libs.plugins.sqlDelight).apply(false)
    alias(libs.plugins.resource.generator).apply(false)
}

buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}