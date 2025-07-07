plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false

// removed during version integration phase
    // alias(libs.plugins.kotlin.compose) apply false

    //hilt plugins
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false

    //extra
//removed during version integration phase
//    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"

}


