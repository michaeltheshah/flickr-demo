plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    defaultConfig {
        applicationId = "com.cvshealth.flickrdemo"
        compileSdk = 35
        defaultConfig {
            minSdk = 26
        }
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }

    kotlin {
        jvmToolchain(17)
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }

    namespace = "com.cvshealth.flickrdemo"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.preference.ktx)
    //Android Support Libraries
    api(libs.lifecycle.viewmodel.ktx)

    implementation(libs.coil.compose)
    //Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui.text.android)
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.androidx.activity.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.hilt.navigation.compose)
    //Coroutines
    implementation(libs.kotlinx.coroutines.android)
    //Datastore
    implementation(libs.androidx.datastore.preferences)
    //DateTime
    implementation(libs.kotlinx.datetime)
    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    //Koin
    implementation(libs.koin.android)
    //Kotlinx.serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    //OkHttp
    implementation(libs.okhttp.logging.interceptor)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.coroutines.adapter)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Testing

    // Coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    // JUnit
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core.testing)

    // Mockito
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.androidx.test.ext.junit)
    implementation(libs.mockk)
}
