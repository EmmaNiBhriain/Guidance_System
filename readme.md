# Guidance System 

The Guidance System is a project developed with the aim of helping people with mild/moderate levels of Dementia to navigate a building safely. When they go into an area that is considered to be unsafe, a notification is sent to a care assistant via e-mail. The wearable device also vibrates to let them know that they have not reached their destination. 

If a user takes too long to reach their destination, the care assistant is also notified and the location of the user is sent with a message asking the care assistant to check on them.

In the event of a user deciding that they require assistance, pressing the button on the wearable device will also trigger a notification to the care assistant. 


## Components
* LoPy microcontroller
* Pycom expansion board
* A USB - micro USB cable
* WiFi connection
* Bluetooth Beacons
* Adafruit account (Free)
* IFTTT account (Free)
* Vibrating disk motor
* Breadboard
* Male-Male connection wires
* Atom IDE
* Android Mobile Application (Kotlin)
* Firebase Realtime database
* Firebase Authentication

## Setup
### Setting up the hardware
1. Connect the Pycom board to the Pysense expansion board.
2. Using the breadboard, connect pin 9 of the expansion board to the positive connection of the vibrating motor and connect the negative connection to the GND pin.
3. Power the device. This can be done by connecting the device to your laptop/PC using the USB-micro USB cable.


### Setting up the software
#### MicroPython software
To run the code, you will first need to install Atom if you have not done so already.
Within the IDE, install the Pymakr plugin. This allows you to connect to your LoPy microcontroller and 
to run code written within the editor.

Run the file mymain.py to view the main functionality of the program.

#### Mobile Application 
To run the mobile application, install Android Studio and connect an Android device to the computer or power up an Android emulator. 
