Artificial Intelligence Assignment (50%) 2018 - Using Simulated Annealing to Break a Playfair Cipher
Rebecca Kane / G00320698

Requirements for the Application  
- A copy of 4grams.txt, in the same folder as playfair.jar.
- A .txt file for decryption, in the same folder as playfair.jar.

Running the Application
Open a command prompt in the same directory as the playfair.jar file.
Run java –cp ./playfair.jar ie.gmit.sw.ai.CipherBreaker in the command prompt.
Follow the instructions on screen.

----------------------------------------------------------------------------------------------------
Main Features of the Application  

This application is menu driven, giving the user three options upon start -
1. Decrypt Using a Known Key  
2. Decrypt Using Simulated Annealing
3. Exit Program

When the user enters '1' or '2' at the main menu, they will be asked what file they would like to decrypt. The file must be in the same folder as playfair.jar, and the user can enter the file with or without its '.txt.' extension, as the program will check if the extension is present in the entered filename and add it if not found. For example, if the user wants to decrypt 'thehobbit.txt', they can enter 'thehobbit' or 'thehobbit.txt'. If the file is not found, an error message is returned and the user is asked to enter a filename again. Once the user has entered a valid file, the file is parsed to a String, which is used in various methods across the application. Alternatively, the user can enter "M" to return to the main menu.

1. Decrypt Using a Known Key  
Once the user has entered an existing valid file, they will be asked for a 25 letter key to decrypt with. The String the user enters is parsed to uppercase and any non-letters are removed. If the resulting key does not contain 25 characters, the user will be asked to re-enter a key. Once a valid key is entered, the program will pass the file contents to the decryption method in PlayfairImpl.java, which will decrypt the text based on the three rules of playfair decryption. The first hundred characters of the decrypted text is outputted and the full decrypted text is written to a new file called 'Decrypted-filename.txt', where filename is the name of the file the user entered. A success message is displayed back to the user along with the time it took to decrypt the file. The main menu is then displayed to the user again.  

2. Decrypt Using Simulated Annealing
Once the user has entered an existing valid file, no further input is required. The program will run, starting with a random key to be modified slightly on each transition and compared against the current best key. The "best key" is determined by the score of the key's decrypted text, which is broken into 4-letter segments and scored using the probabilities of those segments in the 4grams.txt file. The simulated annealing process will run y times for every x iteration (y transitions for x temperature), where the number of iterations (temperature) is dependent on the size of the file to be decrypted, calculated by 10 + 0.087 * (length of text - 84). Once all iterations of the process are complete, the temperature, number of transitions and total number of iterations (temperature * transitions) are outputted. The best key, its score and the first hundred characters of decrypted text will be displayed to the user. The full decrypted text will be written to a new file called 'Decrypted-filename.txt', where filename is the name of the file the user entered, and a success message is displayed back to the user. The main menu is then displayed to the user.

3. Exit Program
Terminates the program. In order to start the program again, the user must run java –cp ./playfair.jar ie.gmit.sw.ai.CipherBreaker in the command line.