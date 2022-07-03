# Snake_Game
This is a snake game using java. Used Random class to generate apples at random locations within the game panel. Making grids using Graphics drawline helped with the 
coordination of various elements.Collisions with the body or walls is checked at intervals of 75ms using Timer object. ActionListener is implemented and thus using 
addKeyListener method, we bind the keyboard input using MyKeyAdapter object that extends KeyAdapter abstract class. Overriding keypressed method handles the passed 
KeyEvent object representing keys pressed on keyboard. Only four directional keys are considered for changing the direction of snake which is represented by character 
variable. Emphasis has been given on not allowing 360 degree turns i.e. direction reversal as that would result in undesired collisions. 
