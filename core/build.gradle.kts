import java.util.Properties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.parcelize)
}

apply("../shared_dependencies.gradle")

val apikeyPropertiesFile = rootProject.file("config.properties")
val apikeyProperties = Properties().apply {
    load(apikeyPropertiesFile.inputStream())
}

android {
    namespace = "febri.uray.bedboy.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        multiDexEnabled = true
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "GIT_TOKEN", apikeyProperties["GIT_TOKEN"] as String)
        buildConfigField("String", "BASE_URL", apikeyProperties["BASE_URL"] as String)
        buildConfigField("String", "HEADER_APP", apikeyProperties["HEADER_APP"] as String)
        buildConfigField("String", "API_VERSION", apikeyProperties["API_VERSION"] as String)
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-opt-in=kotlin.RequiresOptIn")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.kotlin.stdlib)

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.room.testing)

    //retrofit
    api(libs.retrofit)
    api(libs.converterGson)
    api(libs.loggingInterceptor)

    //coroutine
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)

    api(libs.androidx.lifecycle)
    api(libs.androidx.activityKTX)
    api(libs.androidx.fragmentKTX)

    //paging
    api(libs.androidx.pagingRuntime)
}