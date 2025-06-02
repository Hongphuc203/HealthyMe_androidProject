plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.project"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.project"
        minSdk = 24
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
    implementation ("com.jakewharton.threetenabp:threetenabp:1.4.4")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    implementation ("com.github.alamkanak:android-week-view:1.2.6")
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("androidx.camera:camera-extensions:1.2.0")
    implementation ("androidx.camera:camera-core:1.2.0")
    implementation ("androidx.camera:camera-camera2:1.2.0")
    implementation ("androidx.camera:camera-lifecycle:1.2.0")
    implementation ("androidx.camera:camera-view:1.2.0")
    implementation ("androidx.viewpager2:viewpager2:1.1.0")
    implementation ("com.google.android.material:material:1.10.0")
    // Firebase BoM quản lý version tự động
    implementation(platform("com.google.firebase:firebase-bom:33.14.0"))

    // Các thư viện bạn dùng
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    implementation ("com.google.android.gms:play-services-auth:21.0.0") // Google Sign-In
    implementation ("com.google.android.material:material:1.12.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}