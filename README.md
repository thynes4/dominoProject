Domino Project Thomas Hynes

This is my implementation of Dominos in Java

There are two jars I uploaded for the project,
"commandline1.0_thynes4.jar" launches the commandline version
"javafx1.0_thynes4.jar" launches the java fx version

The code I uploaded asks the user if they want the commandline or the javafx version to launch upon the start,
each jar just comments out the logic for the other version.



** Commandline Version

Initial note: My version of the game will not autoplay for you if you enter an invalid move (its dominos and should hopefully be easy enough for you to figure out what move you can play), instead it will ask you to try again. If you try to play when you have to draw or try to draw when you can play it will have you try again also. At the end of the game it will calculate the your score and the computer score and print them then declare a winner, if the scores are the same the last to play will be declared the winner.

On the start of the commandline version you will see how many dominos are in the game and you will see a representation of your domino tray
with indexes above each domino so you know what index to enter when you are playing a domino. You must type "p" and press enter to play the first domino
at the start of the game.

when you type "p" and press enter to play a domino:
first you will be asked what domino you want to play you must enter an index of a domino that is shown in your rack above the dominos
then press enter

then you must enter if you want to play the domino on the left or right side of the gameboard "l" for left and "r" for right,
if there is no dominos on the gameboard at the start of the game it does not matter which you choose.

finally you must choose if you want to rotate the domino if the domino on the tray is not alligned so that it will fit the other domino correctly
you must type "y" to flip your domino and "n" to not flip your domino.

if your move was valid it will play your move and then the computer will play and you will have a chance to play again, if your move was invalid it will give you a
chance to try again.

when you type "d" to draw a domino from the boneyard:

if the boneyard isnt empty and you have no dominos that can be played on the current gameboard you will be given a domino and given a chance to play it
if you have a domino that can be played you will not be allowed to draw from the boneyard and must play a domino to continue.

when you type "q" to quit, the game will end.



** JavaFX Version

On the top left of the screen you will see how many dominos the computer has on the top right you will see how many dominos are in the boneyard, this will update as the game progresses. My version of dominos allows the user to have a 'selected domino' in order to select a domino you click on the domino in the bottom rack which is your domino rack when the domino is selected this will be marked by a yellow box around the domino you can change your selected domino at any time. When you press the flip button the selected domino will flip and this change will be displayed in your rack. Your inital move must be to select a domino and place it on the left or the right. if you have no dominos that can be played and the boneyard is not empty you must press draw. If you wish to play a domino select the domino and press either place left or place right. If the domino is not alligned correctly you must flip it before you play. After your turn the computer will automaticly play.  This version follows my ideology for the commandline version, it will NOT autoplay for you if you enter an improper move or try to draw when you can play, (again, its dominos you should be able to figure out if you can do either).

I did not want to bog down my javafx version with extranious information so if you wish to see the exact computer move that was played it will also be printed on the commandline, at the end of the game the javafx application will display either you won or you lost, if you want to see the final score this also will be printed on the commandline.
