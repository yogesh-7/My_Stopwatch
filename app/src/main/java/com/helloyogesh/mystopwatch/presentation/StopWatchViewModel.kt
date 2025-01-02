@file:OptIn(ExperimentalCoroutinesApi::class)

package com.helloyogesh.mystopwatch.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class StopWatchViewModel: ViewModel() {

    // Tracks the total elapsed time in milliseconds
    private val _elapsedTime = MutableStateFlow(0L)

    // Represents the current state of the stopwatch (RUNNING, PAUSED, or RESET)
    private val _timerState = MutableStateFlow(TimerState.RESET)

    // Exposes the timer state to the UI or other components
    val timerState = _timerState.asStateFlow()

    // Formatter to convert elapsed time into a readable format like HH:mm:ss:SSS
    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS")

    // Converts elapsed time to a formatted stopwatch string that can be displayed on the UI
    val stopWatchText = _elapsedTime
        .map { millis ->
            // Convert milliseconds to a LocalTime object and format it
            LocalTime.ofNanoOfDay(millis * 1_000_000).format(formatter)
        }
        .stateIn(
            viewModelScope,                      // Tied to the lifecycle of the ViewModel
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), // Keeps the flow active while there are subscribers
            "00:00:00:000"                       // Default value shown when no time is recorded
        )

    init {
        // Observes the timer state and starts or stops the timer based on its value
        _timerState
            .flatMapLatest { timerState ->
                // Creates a timer flow only if the stopwatch is in the RUNNING state
                getTimerFlow(isRunning = timerState == TimerState.RUNNING)
            }
            .onEach { timeDiff ->
                // Accumulates the elapsed time whenever a new time difference is emitted
                _elapsedTime.update { it + timeDiff }
            }
            .launchIn(viewModelScope) // Launches the timer logic in the ViewModel's scope
    }

    // Toggles the stopwatch between running and paused states
    fun toggleIsRunning() {
        when (timerState.value) {
            TimerState.RUNNING -> _timerState.update { TimerState.PAUSED } // Pause the timer if it's running
            TimerState.PAUSED, TimerState.RESET -> _timerState.update { TimerState.RUNNING } // Start the timer if paused or reset
        }
    }

    // Resets the stopwatch to its initial state
    fun resetTimer() {
        _timerState.update { TimerState.RESET } // Set the state to RESET
        _elapsedTime.update { 0L }             // Reset the elapsed time to 0
    }

    // Produces a flow that emits time differences while the stopwatch is running
    private fun getTimerFlow(isRunning: Boolean): Flow<Long> {
        return flow {
            var startMillis = System.currentTimeMillis() // Get the current system time as the starting point
            while (isRunning) {
                val currentMillis = System.currentTimeMillis() // Get the current system time
                val timeDiff = if (currentMillis > startMillis) {
                    currentMillis - startMillis // Calculate the time difference since the last emission
                } else 0L // Handle cases where the system clock resets unexpectedly
                emit(timeDiff) // Emit the time difference to the flow
                startMillis = System.currentTimeMillis() // Update the starting point for the next iteration
                delay(10L) // Introduce a small delay to control emission frequency 10 milli sec
            }
        }
    }
}
