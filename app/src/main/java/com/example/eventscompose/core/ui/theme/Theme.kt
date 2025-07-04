package com.example.eventscompose.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFE53935), // Red status bar
    onPrimary = Color.White,
//    primaryContainer = Color(0xFFB71C1C),
    primaryContainer = Color(0xFF444444), //0xFF444444
    onPrimaryContainer = Color.White,

    surface = Color(0xFF1C1C1C), // 0xFF2C2C2C
    onSurface = Color.White, // White event titles
    onSurfaceVariant = Color(0xFFB0B0B0), // Gray descriptions/times

    background = Color(0xFF2C2C2C), // Very dark main background
    onBackground = Color.White, // White "Events" title and date headers

    outline = Color(0xFF444444)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE53935), // Red status bar
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFEBEE),
    onPrimaryContainer = Color(0xFFB71C1C),

    surface = Color.White, // White event cards
    onSurface = Color.Black, // Black event titles
    onSurfaceVariant = Color(0xFF666666), // Gray descriptions/times

    background = Color(0xFFF5F5F5), // Light gray main background
    onBackground = Color.Black, // Black "Events" title and date headers

    outline = Color(0xFFE0E0E0)
)

@Composable
fun EventsComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}