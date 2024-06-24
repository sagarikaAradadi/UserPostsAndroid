    plugins {
        id("com.android.application")
        id("org.jetbrains.kotlin.android")
        id("kotlin-kapt")
        id("com.google.dagger.hilt.android")
    }

    android {
        namespace = "com.samplerecyclerview"
        compileSdk = 34

        defaultConfig {
            applicationId = "com.samplerecyclerview"
            minSdk = 24
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
                buildConfigField("String", "SERVER_URL", "\"https://jsonplaceholder.typicode.com/\"")
            }
            debug {
                buildConfigField("String", "SERVER_URL", "\"https://jsonplaceholder.typicode.com/\"")
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
        buildFeatures {
            buildConfig =true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.1"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
        dataBinding {
            enable = true
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    dependencies {

        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.9.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.test.uiautomator:uiautomator:2.3.0")
        implementation("androidx.test:rules:1.5.0")
//        implementation(project(":moblibrary"))
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

        testImplementation("org.mockito:mockito-core:3.12.4")

        androidTestImplementation ("androidx.test.espresso:espresso-contrib:3.5.1")


        //HiltDependencyInjection
        implementation("com.google.dagger:hilt-android:2.51.1")
        kapt("com.google.dagger:hilt-android-compiler:2.51.1")

        //RetrofitApi
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")

        // coroutines
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

        //Lifecycle
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.2")
        implementation("androidx.lifecycle:lifecycle-common-java8:2.8.2")
        implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
        // optional - helpers for implementing LifecycleOwner in a Service
        implementation("androidx.lifecycle:lifecycle-service:2.8.2")


        implementation("androidx.activity:activity-ktx:1.3.1")

        //Splash Screen
        implementation("androidx.core:core-splashscreen:1.2.0-alpha01")
    }
    kapt {
        correctErrorTypes = true
    }