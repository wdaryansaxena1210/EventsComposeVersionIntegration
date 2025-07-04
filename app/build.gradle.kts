plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)

    //hilt
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
//    id("com.google.devtools.ksp")
//    id("com.google.dagger.hilt.android")

    //serialization
//removed during version integration phase
//    alias(libs.plugins.kotlin.serialization)

    //extra
}

android {
    namespace = "com.example.eventscompose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.eventscompose"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

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

    //lifecycle aware state collection
    implementation(libs.androidx.lifecycle.runtime.compose)


    //navigation
    implementation(libs.androidx.navigation.compose)

    //retrofit
    implementation(libs.retrofit)


    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // For instrumentation tests
//    androidTestImplementation  ("com.google.dagger:hilt-android-testing:2.56.2")
//    kaptAndroidTest ("com.google.dagger:hilt-compiler:2.56.2")
    // For local unit tests
//    testImplementation ("com.google.dagger:hilt-android-testing:2.56.2")
//    kaptTest ("com.google.dagger:hilt-compiler:2.56.2")

    //serialization
//    implementation(libs.kotlinx.serialization.json)
//    implementation(libs.retrofit2.kotlinx.serialization.converter)


    //GSON
    implementation(libs.converter.gson)

    //http logs interceptor
    implementation(libs.logging.interceptor)

    //extra


}
