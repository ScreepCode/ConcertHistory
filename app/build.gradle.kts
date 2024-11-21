import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

val localProperties = readProperties(file("$rootDir/local.properties"))

fun readProperties(propertiesFile: File) = Properties().apply {
    if(propertiesFile.exists()){
        propertiesFile.inputStream().use { fis ->
                load(fis)
        }
    }
}

fun generateVersion(
    hotfix: Int = localProperties.getProperty("hotfix.id")?.toInt() ?: 0
): String {
    val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val date = Date()
    return "${dateFormat.format(date)}.$hotfix"
}

android {
    namespace = "de.buseslaar.concerthistory"
    compileSdk = 35

    val version = System.getenv("VERSION_CODE") ?: generateVersion()

    defaultConfig {
        applicationId = "de.buseslaar.concerthistory"
        minSdk = 26
        targetSdk = 35
        versionCode = version.replace(".", "").toInt()
        versionName = version

        setProperty("archivesBaseName", "concertHistory-$versionCode")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", localProperties.getProperty("API_KEY"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            versionNameSuffix = "-debug"
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
        buildConfig = true
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

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.material3.adaptive.navigation.suite.android)

    // Settings
    implementation(libs.compose.prefs3)
    implementation(libs.androidx.datastore.preferences)

    // Room
    implementation(libs.room)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
    implementation(libs.coroutines)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}
