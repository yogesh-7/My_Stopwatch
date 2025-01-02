/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.helloyogesh.mystopwatch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.tooling.preview.devices.WearDevices

// Main activity that serves as the entry point for the application
class MainActivity : ComponentActivity() {

    // Override the onCreate method to set up the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Installs a splash screen for the app

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault) // Set the app theme to the device default

        setContent {
            WearApp() // Composable function to define the app's UI
        }
    }
}

@Composable
private fun WearApp() {
    // Initialize the StopWatchViewModel using the ViewModel API
    val viewModel = viewModel<StopWatchViewModel>()

    // Collect the timer state and stopwatch text using lifecycle-aware state collectors
    val timerState by viewModel.timerState.collectAsStateWithLifecycle()
    val stopWatchText by viewModel.stopWatchText.collectAsStateWithLifecycle()

    // Defines the scaffold structure for the wearable UI
    Scaffold(
        timeText = {
            TimeText(
                timeTextStyle = TimeTextDefaults.timeTextStyle(
                    fontSize = 10.sp // Small font size suitable for a wearable device
                )
            )
        },
        vignette = {
            // Adds vignette effects at the top and bottom of the screen while scrolling
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        }
    ) {
        // The main UI content, which includes the stopwatch
        StopWatch(
            state = timerState,                // Current state of the stopwatch
            text = stopWatchText,              // Formatted stopwatch time
            onToggleRunning = viewModel::toggleIsRunning, // Toggles between running and paused states
            onReset = viewModel::resetTimer,   // Resets the stopwatch
            modifier = Modifier.fillMaxSize()  // Makes the stopwatch fill the available space
        )
    }
}

@Composable
private fun StopWatch(
    state: TimerState,      // Current state of the stopwatch (RUNNING, PAUSED, RESET)
    text: String,           // Formatted stopwatch time
    onToggleRunning: () -> Unit, // Callback for toggling the running state
    onReset: () -> Unit,         // Callback for resetting the stopwatch
    modifier: Modifier = Modifier // Modifier for UI customization
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Displays the formatted stopwatch time
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp)) // Adds vertical space between elements

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Button to toggle the running state of the stopwatch
            Button(onClick = onToggleRunning) {
                Icon(
                    imageVector = if (state == TimerState.RUNNING) {
                        Icons.Default.Pause // Pause icon when running
                    } else {
                        Icons.Default.PlayArrow // Play icon otherwise
                    },
                    contentDescription = null // No content description
                )
            }
            Spacer(modifier = Modifier.width(8.dp)) // Adds horizontal space between buttons

            // Button to reset the stopwatch
            Button(
                onClick = onReset, // Callback for reset
                enabled = state != TimerState.RESET, // Disabled if the timer is already reset
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface // Background color
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Stop, // Stop icon for the reset button
                    contentDescription = null // No content description
                )
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}

@Preview(device = WearDevices.RECT, showSystemUi = true)
@Composable
fun DefaultPreview1() {
    WearApp()
}

@Preview(device = WearDevices.SQUARE, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    WearApp()
}

@Preview(device = WearDevices.LARGE_ROUND, showSystemUi = true, backgroundColor =  0xFFFFFFFF)
@Composable
fun DefaultPreview3() {
    WearApp()
}
