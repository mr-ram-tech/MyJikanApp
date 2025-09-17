plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    namespace = "com.example.myjikanapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myjikanapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Compose
    implementation("androidx.compose.ui:ui:1.9.1")
    implementation("androidx.compose.material:material:1.9.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.9.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.9.1")
    implementation("androidx.navigation:navigation-compose:2.9.4")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    // Retrofit + OkHttp + Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Coil for Compose
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Room
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Lifecycle / ViewModel
    implementation(libs.androidx.lifecycle.runtime.ktx.v262)
    implementation(libs.lifecycle.viewmodel.ktx)

    // YouTube Android Player (third-party)
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}