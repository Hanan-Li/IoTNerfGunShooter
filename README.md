# IoTNerfGunShooter
Silly project to turn a nerf gun into a bad IoT device where you can shoot it remotely with your phone. This is a pretty dumb project that I built out of boredom.

## Requirements
- Any Raspberry Pi with internet connection (Raspberry Pi 3 Model B used in this case)
- Servo Motor (Tower Pro MG995 used in this case)
- Android Phone
- Nerf gun
- Custom rig to hold nerf gun and servo motor in place

## Raspberry Pi
This serves as the IoT device. A simple python socket server is set up on the RPi, listening to commands, and will respond with moving the servo motor by some angle, or shutting down the server, based on incoming commands. The servo motor is connected with power supply cable connected to GPIO pin 4, Ground cable connected to GPIO pin 6, and PWM signal cable connected to GPIO pin 11. External power supplies for the servo is also doable, but for the purpose of this, its not necessary.

## Android App
Consists of 2 buttons, one that will send a command to the RPi socket server to shoot the nerf gun, one that will send a command to the RPi socket server to shut down.

## Demo
