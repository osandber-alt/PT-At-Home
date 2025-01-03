plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    //id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.ptathome"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ptathome"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    hilt {
        enableAggregatingTask = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
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

    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")
    //ksp("com.google.dagger:hilt-android-compiler:2.51.1")
    //implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    kapt("androidx.hilt:hilt-compiler:1.0.0")
    //ksp("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation(libs.gson)
    implementation("androidx.core:core-ktx:1.9.0")

    implementation(libs.squareup.retrofit.retrofit)
    implementation(libs.squareup.retrofit.converter)
    implementation(libs.squareup.retrofit.scalar.converter)

    implementation("androidx.paging:paging-runtime:3.3.5")
    implementation("androidx.paging:paging-compose:3.3.5")
    implementation ("androidx.paging:paging-runtime-ktx:3.3.5")



    implementation("org.jsoup:jsoup:1.14.3")

    implementation("io.coil-kt:coil-compose:2.4.0")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}