plugins {

    alias(libs.plugins.android.application)

}

android {

    namespace = "com.s23010920.studypal"

    compileSdk = 35

    defaultConfig {

        applicationId = "com.s23010920.studypal"

        minSdk = 25

        targetSdk = 35

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

}

dependencies {

    implementation(libs.appcompat)

    implementation(libs.material)

    implementation(libs.activity)

    implementation(libs.constraintlayout)

    

    // ✅ Google Maps SDK

    implementation("com.google.android.gms:play-services-maps:18.2.0")

    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation("com.google.android.libraries.places:places:3.3.0")
    implementation(libs.biometric)

    testImplementation(libs.junit)

    androidTestImplementation(libs.ext.junit)

    androidTestImplementation(libs.espresso.core)

}

