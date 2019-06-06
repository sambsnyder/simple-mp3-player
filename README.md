# simple-mp3-player
A simple mp3 player that I was required to build for a Java class in college
# Summary
This program recursively traverses a directory(and its subdirectories) to find all mp3 files.    
The internal data structure inside the "SongDatabase" is a custom LinkedList that I wrote myself. 
I used JLayerlibrary for playing mp3 files: http://www.javazoom.net/javalayer/javalayer.html
and Audio tagger was used to extract info(title and artist) about .mp3 files: https://bitbucket.org/ijabz/jaudiotagger/src/master/
## Setup
This project requires a java Execution environment of 1.8
I created this project using the Eclipse IDE. If you would like to clone and run this project you must follow these instructions to run:
1. Clone Repo.
2. Open in Eclipse
3. Configure the build path to include JDK 1.8.
    Install jdk8: https://www.oracle.com/technetwork/java/javase/downloads/index.html (you need an account).
    Your project > Build Path > Configure Build Path.
    Go to Libraries tab.
    Select JRE System Library.
    Press Edit.
    Click on Installed JREs.
    You should see jdk1.8
    Press Add.
    Select Standard VM.
    Press Next.
    Locate your java folder, e.g.: C:\Program Files\Java\jdk1.7. on Windows and Library/Java/JavaVirtualMachine/jdk 1.8. You       want to open the Home folder inside the jdk directory
    Populate JRE home with it.
![config guide](https://github.com/sambsnyder/simple-mp3-player/blob/master/Setup.png)
4. There is also an executable Jar file if you wish to simply run the the application.
5. Note that the Search Feature has not been implemented and does not function at this time, I might implement the search function at a later date.

## Use
1. Click Load and select a directory that contains some .mp3 files.
2. Seclect a song.
3. Hit Play and the selected will begin to play.
4. Stop will stop the song.
5. Exit closes the application.

### External Sources/Accreditations 

The starter code GUI for this project was provided to my by my professor Olga Karpenko. https://www.usfca.edu/faculty/olga-karpenko

JLayerlibrary for playing mp3 files:
http://www.javazoom.net/javalayer/javalayer.html
Audio tagger used to extract info about .mp3 files:
https://bitbucket.org/ijabz/jaudiotagger/src/master/


