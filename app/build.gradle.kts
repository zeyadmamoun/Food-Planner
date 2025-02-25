plugins {
    alias(libs.plugins.android.application)
    id("androidx.navigation.safeargs")
    // Add the Google services Gradle plugin
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.foodiesapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.foodiesapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
    // Views-Fragments integration
    implementation(libs.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // viewpager2
    implementation(libs.androidx.viewpager2)

    // splash screen
    implementation(libs.androidx.core.splashscreen)
    // lottie animation
    implementation(libs.android.lottie)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    // firebase authentication library
    implementation(libs.firebase.auth)
    // firebase pre-built UI
    implementation(libs.firebaseui.firebase.ui.auth)
    // firebase firestore
    implementation(libs.firebase.firestore)

    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.adapter.rxjava3)
    implementation(libs.converter.gson)

    //Glide
    implementation (libs.github.glide)

    // RXJava
    implementation (libs.rxandroid)
    implementation (libs.rxjava)

    // youtube
    implementation (libs.core)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.rxjava3)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}