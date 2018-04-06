# Artificial Intelligence Assignment (50%) 2018  

## *Using Simulated Annealing to Break a Playfair Cipher*  

You are required to use the simulated annealing algorithm to break a Playfair Cipher. Your
application should have the following minimal set of features:  
- A menu-driven command line UI that enables a cipher-text source to be specified (a
file or URL) and an output destination file for decrypted plain-text.  
- Decrypt cipher-text with a simulated annealing algorithm that uses a log-probability
and n-gram statistics as a heuristic evaluation function.  

#### Breakdown of Marks / 100  

**60** &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Robustness. The application executes correctly.  
**15** &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Packaging & Distribution  
**25** &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Documented (and relevant) extras.  
  
*Project due Sunday 8th April*  

### Requirements for Running the Application  
- A copy of 4grams.txt, in the same folder as playfair.jar. E.g. if playfair.jar is in a folder called "test", 4grams must also be in the "test" folder. This was a requirment in the project specification.  
- A .txt file for decryption, in the same folder as playfair.jar.  

### Running the Application  
Open a command prompt in the same directory as the playfair.jar file.  
Run `java –cp ./playfair.jar ie.gmit.sw.ai.CipherBreaker` in the command prompt.  
Follow the instructions on screen.  

The [Finished Application](https://github.com/rebeccabernie/Simulated-Annealling-Playfair/tree/master/Finished%20Application) folder in this repository contains the official submission (`Submission.zip`), as well as the `playfair.jar` file, `4grams.txt` and a sample `examtips.txt` file for decryption. You can download this folder, open a command line in the folder and run the above command to run the application.  

## Main Features of the Application  

This application is menu driven, giving the user three options upon start -  

**1. Decrypt Using a Known Key**  
**2. Decrypt Using Simulated Annealing**  
**3. Exit Program**  

When the user enters '1' or '2' at the main menu, they will be asked what file they would like to decrypt. The file must be in the same folder as playfair.jar, and the user can enter the file with or without its `.txt.` extension, as the program will check if the extension is present in the entered filename and add it if not found. For example, if the user wants to decrypt `thehobbit.txt`, they can enter `thehobbit` or `thehobbit.txt`. If the file is not found, an error message is returned and the user is asked to enter a filename again. Once the user has entered a valid file, the file is parsed to a String, which is used in various methods across the application. Alternatively, the user can enter `M` to return to the main menu.  

**1. Decrypt Using a Known Key**  
Once the user has entered an existing valid file, they will be asked for a 25 letter key to decrypt with. The String the user enters is parsed to uppercase and any non-letters are removed. If the resulting key does not contain 25 characters, the user will be asked to re-enter a key. Once a valid key is entered, the program will pass the file contents to the decryption method in PlayfairImpl.java, which will decrypt the text based on the three rules of playfair decryption. The first hundred characters of the decrypted text is outputted and the full decrypted text is written to a new file called 'Decrypted-filename.txt', where filename is the name of the file the user entered. A success message is displayed back to the user along with the time it took to decrypt the file. The main menu is then displayed to the user again. This process takes around 10 milliseconds.  

**2. Decrypt Using Simulated Annealing**  
Once the user has entered an existing valid file, no further input is required. The console will display a "Decrypting..." message along with a "percentage complete" value, updating by 10% for every temperature executed. 
The program will start with a random key, to be modified slightly on each transition and compared against the current best key. The "best key" is determined by the score of the key's decrypted text, which is broken into 4-letter segments and scored using the probabilities of those segments in the 4grams.txt file. The initial temperature is set to 20 and transitions set to 30,000, resulting in a near perfect key most times, after 30-90 seconds. When a "best key" has been found or the temp limit has been reached, the process ends and the number of iterations, transitions and total tests carried out are outputted to the user. The final key and its score are also outputted, and the total length of time taken to decrypt is displayed. The full decrypted text will then be written to a new file called 'Decrypted-filename.txt', where filename is the name of the file the user entered, and a success message is displayed back to the user. The main menu is then displayed to the user.  

**3. Exit Program**  
Terminates the program. In order to start the program again, the user must run `java –cp ./playfair.jar ie.gmit.sw.ai.CipherBreaker` in the command line.  

----  

### Notes

### Exit Loop Prematurely - Design Choice  

Running the program a number of times on the Encrypted AI Exam Tips file with temperature 20, transitions 30,000 suggested that the application will either find the key within the first 5-7 temps, or not at all.  
The reasoning behind this behaviour is due to the simulated annealing algorithm being less likely to accept a worse key at a lower temperature (due to delta calculations) - after a certain temperature, the algorithm becomes more similar to a hill climbing search and will only look for better keys, possibly resulting in getting caught at a local maximum.  
To avoid the user waiting for the full 20 temps to complete, I implemented a break point where
 - the key hasn't changed for 3 temps and there has been at least 4 temps, OR  
 - 10 temps have executed  
the simulated annealing process will end and return the best key at that point.  

### Non-Perfect Keys - Minor Issue  

The application will not always find the perfect key. It may find a perfect key sometimes, but it will often return an almost-perfect key to decrypt text enough for it to be readable. The application sometimes returns a bad key. A full list of variations of temperature/transitions that the program was tested with can be found in a multi-line comment at the top of the `SimulatedAnnealing.java` file, but the main variations used to test were a temperature of 20, with transitions 30k, 40k and 50k. The overall accuracy of the program did not vary much between the three, but 30k was slightly quicker than 40k or 50k, thus I chose this option.  

### File Size Issues - Issue  

Due to the limitations of String sizes in Java, the program cannot decrypt a large file. I had tried implementing a limit but various solutions tried caused issues when decrypting in the PlayfairImpl.java file. I decided the best option was to simply only accept relatively small files, with a suggested size of around 750 bytes, or 750 characters.   
