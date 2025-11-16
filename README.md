<h1 align="center">Turtlefin Android TV</h1>
<h3 align="center">Unofficial fork of the <a href="https://jellyfin.org">Jellyfin Project</a></h3>

---

<p align="center">
<img alt="Logo banner" src="https://raw.githubusercontent.com/JustR0b0t/Turtlefin/refs/heads/main/Artwork/Turtlefin%20Banner%20Logo%20Solid.svg"/>
<br/><br/>


TurtleFin Android TV is a for of the Jellyfin client for Android TV, Nvidia Shield, and Amazon Fire TV devices.
I made a few changes to the app to customize it to my own liking. 

**Do not expect any further updates.**


## Changes

 - The OSD is not shown if Pause, Rewind or Fast Forward is pressed. 
 - The Playback Speed can now be changed to 2.5 or 3
 - Changed the Artwork to comply with the Branding Guidelines

**OSD changes:**
*CustomPlaybackOverlayFragment.java*

       private View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
    
                    // Only show overlay if certain buttons are not pressed
                    if (keyCode != KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
                            && keyCode != KeyEvent.KEYCODE_MEDIA_REWIND
                            && keyCode != KeyEvent.KEYCODE_MEDIA_FAST_FORWARD) {
                        if (!mGuideVisible) {
                            leanbackOverlayFragment.setShouldShowOverlay(true); 
                        }else {
                        leanbackOverlayFragment.setShouldShowOverlay(false);
                        leanbackOverlayFragment.hideOverlay();
                        }
                    }else{
                        leanbackOverlayFragment.setShouldShowOverlay(false);
                        leanbackOverlayFragment.hideOverlay();
                    }

**Playback Speed Changes:**
*VideoSpeedController.kt*

    enum class SpeedSteps(val speed: Float) {
    // Use named parameter so detekt knows these aren't magic values
    SPEED_0_25(speed = 0.25f),
    SPEED_0_50(speed = 0.5f),
    SPEED_0_75(speed = 0.75f),
    SPEED_1_00(speed = 1.0f),
    SPEED_1_25(speed = 1.25f),
    SPEED_1_50(speed = 1.50f),
    SPEED_1_75(speed = 1.75f),
    SPEED_2_00(speed = 2.0f),
    SPEED_2_50(speed = 2.5f),
    SPEED_3_00(speed = 3.0f),
    }

## Build Process

### Dependencies

- Android Studio

### Build

1. Clone or download this repository

   ```sh
   git clone https://github.com/JustR0b0t/Turtlefin.git
   cd Turtlefin
   ```

2. Open the project in Android Studio and run it from there or build an APK directly through Gradle:

   ```sh
   ./gradlew assembleDebug
   ```
   
   Add the Android SDK to your PATH environment variable or create the ANDROID_SDK_ROOT variable for
   this to work.

### Deploy to device/emulator

   ```sh
   ./gradlew installDebug
   ```

*You can also replace the "Debug" with "Release" to get an optimized release binary.*

