plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    namespace = "np.com.lashman.learnit"
    compileSdk = 35

    defaultConfig {
        applicationId = "np.com.lashman.learnit"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.firebase:firebase-common-ktx:21.0.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    //compose destination
    val destinationVersion = "1.9.52"
    implementation("io.github.raamcosta.compose-destinations:core:$destinationVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$destinationVersion")

    // Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson converter for JSON parsing
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room
    val roomVersion = "2.5.2"
    //noinspection GradleDependency
    implementation("androidx.room:room-runtime:$roomVersion")
    //noinspection GradleDependency
    ksp("androidx.room:room-compiler:$roomVersion")
    //noinspection GradleDependency
    implementation("androidx.room:room-ktx:$roomVersion")

    //Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:hilt-android-compiler:2.48.1")
    ksp("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.7.5")

    //Desugaring
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.2")

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

}