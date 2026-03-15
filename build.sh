#!/bin/bash

# Build script for Ultimate Life Simulator
# Usage: ./build.sh

echo "Building Ultimate Life Simulator..."

# Check if ANDROID_HOME is set
if [ -z "$ANDROID_HOME" ]; then
    echo "ANDROID_HOME not set. Please set it to your Android SDK path."
    exit 1
fi

# Build debug APK
./gradlew assembleDebug

# Check if build was successful
if [ $? -eq 0 ]; then
    echo "Build successful!"
    echo "APK location: app/build/outputs/apk/debug/app-debug.apk"
    
    # Copy to output directory
    mkdir -p output
    cp app/build/outputs/apk/debug/app-debug.apk output/
    
    # Create zip for transfer
    cd output
    zip -r ../UltimateLifeSimulator.zip app-debug.apk
    cd ..
    
    echo "ZIP file created: UltimateLifeSimulator.zip"
    echo "You can find it in the project root directory."
else
    echo "Build failed!"
    exit 1
fi
