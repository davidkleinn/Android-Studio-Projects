import org.gradle.kotlin.dsl.support.classPathBytesRepositoryFor

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    dependencies {

        classpath("com.google.gms:google-services:4.3.15")

    }

}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}