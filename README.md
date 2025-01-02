# WearOS Stopwatch App ⏱️  

A sleek and functional stopwatch application designed for WearOS devices, built entirely with Jetpack Compose.  

## Features  
- **Real-Time Stopwatch**: Tracks elapsed time down to milliseconds.  
- **Intuitive Controls**: Start, pause, and reset the timer with a simple and user-friendly interface.  
- **WearOS Optimized**: Designed for round screens with responsive layouts and minimalistic design.  
- **Modern Architecture**: Leverages Jetpack Compose, ViewModel, StateFlow, and best practices for Android development.  
- **Dynamic Theme**: Adjusts seamlessly to system themes for a cohesive look.  

## Screenshots

<img width="400" height = "400" alt="Screenshot 2025-01-03 at 1 21 02 AM" src="https://github.com/user-attachments/assets/72af6861-57e3-4992-af34-ea4ff02eace3" />
<img width="400" height = "400" alt="Screenshot 2025-01-03 at 1 21 32 AM" src="https://github.com/user-attachments/assets/13ecc065-4aa6-4ee7-a4b0-f8f26957c793" />
  


## How It Works  
1. **Stopwatch Controls**:  
   - Tap the play button to start or resume the timer.  
   - Use the pause button to temporarily stop the timer.  
   - Tap the stop button to reset the timer.  

2. **Real-Time Updates**:  
   - The stopwatch continuously updates its display to show elapsed time in the format `HH:mm:ss:SSS`.  

3. **Optimized for WearOS**:  
   - Includes WearOS-specific components like `TimeText` and `Vignette` for a polished experience.  

## Technology Stack  
- **Jetpack Compose**: For building the UI declaratively.  
- **StateFlow**: To manage and observe timer state efficiently.  
- **Lifecycle Awareness**: Using `collectAsStateWithLifecycle` to manage data in Compose.  

## Installation  
1. Clone this repository:  
   ```bash  
   git clone https://github.com/yogesh-7/My_Stopwatch.git  
2. Open the project in Android Studio.
3. Build and run the app on a WearOS emulator or compatible device.

## Contribution
Contributions are welcome! If you find a bug or have an idea for an enhancement, feel free to open an issue or submit a pull request.

