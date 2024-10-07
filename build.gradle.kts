// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    `version-catalog`
    `maven-publish`
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}