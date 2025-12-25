plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // ✅ Firebase Google Services plugin
    alias(libs.plugins.google.gms.google.services) apply false
}
