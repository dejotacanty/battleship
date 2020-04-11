//package battleship;

import java.util.*;
//This class generates and manages a board for a single player
//Each board will be associated with a Player object
//The Board class will generate a board with default Sea values represented by an 'S' in the array
//The board class manages placement of each ship, each ship will be marked on the board by the first letter of the ships name
//EX: Aircraft Carrier will be represented by 'A', Destroyer will be represented by 'D', etc.
//      Submarine is represented by a 'U', since 'S' is used as a Sea space
//

import java.util.*;

///////////////////////////////////
///////////Begin Class/////////////
///////////////////////////////////
public class Board {
 
 //Fields
 String[][] player_board;  //Board array for one player
 String[][] player_display_board;
 int remaining_carrier_spaces;
 int remaining_battleship_spaces;
 int remaining_destroyer_spaces;
 int remaining_submarine_spaces;
 int remaining_patrol_boat_spaces; //End Fields
 
 //Description, Directions, Uses
 
 //
 //Board Constructor
 //
 //Desc:
 //Populates the board, outputs the board, then begins placing ships
 //
 //Directions:
 //Arrays.fill() populates all first-dimension arrays with a for loop
 //Calls printBoard() to output the board to the console
 //Then calls placeAllShips() and makes each player set their ship locations
 //
 //Used by:
 //The Battleship constructor
 //
 public Board(){
  
  player_board = new String[10][10];
  player_display_board = player_board;
  
  //Iterates through each first-dimension array and sets each of it's values to 'S'
  for ( int i = 0; i<10; i++ ){
   
   Arrays.fill(player_board[i], "S");
   
  }
  this.printBoard();
  this.placeAllShips();
 }//End i
 
 //
 //Main method
 //
 //Desc:
 //Creates a new Board object
 //Lets the tester fire shots until all ships are sunk
 //
 public static void main(String[] args) {
   Board b = new Board();
   while(b.allShipsSunk() == false){
     b.printBoard();
     System.out.println(b.takeFire(b.toIntArray(b.getUserString("Fire!").split(",",2))));
     
   }
   System.out.println("All ships have been sunk, You win! And you lose! You're a winner but you suck!");
 }
 //
 //takeFire(int[])
 //
 //Description:
 //Records the process of a board getting 'hit' by  a specified coordinate from a player
 //
 //Directions:
 //Takes an 'int[]' input from a player as coordinates and returns whether the shot hit a ship or missed
 //If the coordinate is not a legal target on the board takeFire returns 'repeat' and does nothing else
 //If the coordinate would hit a ship, changeSpace() changes the coordinate location on the board to 'X'
 //If the coordinate would miss and hit the sea, then the coordinate location is changed to 'M'
 //
 //Used by:
 //The class Battleship main method
 //
 public String takeFire(int[] shot_coords ){
  
   String shot_result = "Error";
   
   if( this.isValidCoordinate(shot_coords) ){
     
     switch(this.getCoordinate(shot_coords)){
       
       case "S":
         shot_result = "Miss";
         this.changeSpace(shot_coords, 'M');
         break;
       
       case "X":
         shot_result = "Repeat";
         break;
       
       case "M":
         shot_result = "Repeat";
         break;
         
       case "A":
         shot_result = "Hit";
         this.changeSpace(shot_coords, 'X');
         remaining_carrier_spaces--;
         this.shipSunk(remaining_carrier_spaces, "Aircraft Carrier");
         System.out.println(remaining_carrier_spaces);
         break;
         
       case "B":
         shot_result = "Hit";
         this.changeSpace(shot_coords, 'X');
         remaining_battleship_spaces--;
         this.shipSunk(remaining_battleship_spaces, "Battleship");
         break;
         
       case "D":
         shot_result = "Hit";
         this.changeSpace(shot_coords, 'X');
         remaining_destroyer_spaces--;
         this.shipSunk(remaining_destroyer_spaces, "Destroyer");
         break;
         
       case "U":
         shot_result = "Hit";
         this.changeSpace(shot_coords, 'X');
          remaining_submarine_spaces--;
         this.shipSunk(remaining_submarine_spaces, "Subamrine");
         break;
         
       case "P":
         shot_result = "Hit";
         this.changeSpace(shot_coords, 'X');
         remaining_patrol_boat_spaces--;
         this.shipSunk(remaining_patrol_boat_spaces, "Patrol Boat");
         break;
         
     }
   }
      
   return shot_result;
 }
 
 //
 //placeShip(Str, int, char)
 //
 //Desc:
 //Places a single ship on the board
 //
 //Directions:
 //Takes the given ships's name as a 'String', the ship size/length as an 'int', and the letter to represent the ship as a 'char'
 //The player is prompted to input coordinates in X,Y format, which repeats until a valid coordinate is entered
 //Then the player is prompted to input a direction for the ship, which repeats until a valid coordinate is entered
 //Finally it calls areSpacesFree() to validate the input
 //  -  If valid, changeSpaces() edits the proper spaces on the board
 //  -  If invalid, this method recurs itself
 //
 //Used by:
 //Board.placeAllShips();
 //
 private void placeShip( String ship_name, int ship_size, char ship_char ){
  
   String direction;
   int[] coords_int;
   String[] coords_string;
   
   System.out.println("Format input as 'X,Y'");
   System.out.println("Valid directions are left, right, up, down");
   
   do{
     
     coords_string = this.getUserString("Choose starting coordinate of  " + ship_name + ":").split(",",2);
    
   }while(this.isValidCoordinate(this.toIntArray(coords_string))== false);
   
   
   coords_int = this.toIntArray(coords_string);
   
   
   do{
     
     direction = this.getUserString(ship_name + " direction:");
     
   }while(this.isValidDirection(direction) == false);
   
   
   
   if(this.areSpacesFree(direction, coords_int, ship_size)){
     
     this.changeSpaces(coords_int, ship_char, ship_size, direction);
     
   } else {
     
     System.out.println("Inputs not valid.");
     this.placeShip(ship_name,ship_size,ship_char);
     
   }
  
 }
 
 //
 //printBoard()
 //
 //Desc:
 //Outputs player_board & the each ship's status to the console
 //
 //Directions:
 //Nested 'for' loops are used to print the board
 //shipStatus() outputs to the right of the board, seperated from board the by hardcoded spaces
 //
 //Used by:
 //The Board constructor
 //The Battleship constructor
 //
 public void printBoard(){
   
  System.out.println();
  System.out.println();
  System.out.print("     0  1  2  3  4  5  6  7  8  9");
  System.out.print("          ");
  System.out.println("Remaining Ships:");
  System.out.println();
   
  //Start i
   for(int i = 0; i< 10; i++){
     
     System.out.print(i + "    ");
     
     //Start j
     for (int j = 0; j < 10; j++){
       
       System.out.print(player_board[j][i] + "  ");
     }
     //End j
     
     System.out.print("          ");
     
    switch(i){
      
      case 0:
        
        this.shipStatus("A");
        
        break;
        
        
      case 1:
        
        this.shipStatus("B");
        
        break;
      
        
      case 2:
        
        this.shipStatus("D");
        
        break;
      
        
      case 3:

        this.shipStatus("U");
        
        break;
        
      case 4:
        
        this.shipStatus("P")
        
        break;
   
        
      default:
        System.out.println();
    }
   }
   //End i
   
   System.out.println();
   System.out.println();
 }
 
 //
 //shipStatus(Str)
 //
 //Desc:
 //Prints a given ship's integrity to the console
 //
 //Directions:
 //If the given letter is one of the 5 different ships then the function will either
 //  -  Display the number of the remaining ships's spaces that are left (If there are more than 0 spaces remaining)
 //  -  Display that the ship has sunk (if the ship given has 0 spaces remaining)
 //
 //Used by:
 //Board.printBoard();
 public void shipStatus(String ship_letter){
   
   switch(ship_letter){
      
      case "A":
        if(remaining_carrier_spaces != 0){
          System.out.println(remaining_carrier_spaces + " Carrier Spaces");
        }else {
          System.out.println("Aircraft Carrier has been sunk.");
      }
        break;
        
      case "B":
        if(remaining_battleship_spaces != 0){
          System.out.println(remaining_battleship_spaces + " Battleship Spaces");
        }else {
          System.out.println("Battleship has been sunk.");
        }
        break;
      
      case "D":
         if(remaining_destroyer_spaces != 0){
          System.out.println(remaining_destroyer_spaces + " Destroyer Spaces");
        }else {
          System.out.println("Destroyer has been sunk.");
        }
        break;
      
      case "S":
       if(remaining_submarine_spaces != 0){
          System.out.println(remaining_submarine_spaces + " Submarine Spaces");
        }else {
          System.out.println("Submarine has been sunk.");
        }
        break;
        
      case "P:
        if(remaining_patrol_boat_spaces != 0){
          System.out.println(remaining_patrol_boat_spaces + " Patrol Boat Spaces");
        }else {
          System.out.println("Patrol Boat has been sunk.");
        }
        break;
   
      default:
        System.out.println();
    }
 }
 
 //Note this comment 'template' when creating methods
 // 1 - Description
 // 2 - Directions
 // 3 - Reasoning/Necessity
 
 //Example (This comment is not relevant to the code)
 //This polymorph of printBoard accepts a boolean that acts as a unique identifier between the two players
 //The human player is 'true' and the computer is 'false'
 //This method is necessary to run CloakedBoard
 //
 
 
 //
 //printBoard(String[][])
 //
 //Desc:
 //This method runs just like printBoard but displays a given board, which will be the computer player's cloaked board
 //
 //Directions:
 //The board being passed must be a 'String[][]'
 //Nested for loops display the given board 
 //Unlike printBoard(), this method does not call shipStatus() and the switch statement cases are hardcoded with the method
 //Displays each ship's status to the right of the board output
 //
 //Used by:
 //ComputerBoard.printCloakedBoard();
 //
 public void printBoard(String[][] board_to_print){
  System.out.println();
  System.out.println();
  System.out.println("     0  1  2  3  4  5  6  7  8  9");
  System.out.println();
  
  //Start i
  for(int i = 0; i< 10; i++){
    
    System.out.print(i + "    ");
    
    
    //Start j
    for (int j = 0; j < 10; j++){
      System.out.print(board_to_print[j][i] + "  ");
    }//End j
    
    
    switch(i){
      
      case 0:
        
        if(remaining_carrier_spaces == 0){
          System.out.println("Aircraft Carrier has been sunk.");
      }
        
        break;
        
        
      case 1:
        
        if(remaining_battleship_spaces == 0){
          System.out.println("Battleship has been sunk.");
        }
        
        break;
      
      case 2:
        
         if(remaining_destroyer_spaces == 0){
          System.out.println("Destroyer has been sunk.");
        }
         
        break;
      
        
      case 3:
        
       if(remaining_submarine_spaces == 0){
          System.out.println("Submarine has been sunk.");
        }
       
        break;
        
        
      case 4:
        
        if(remaining_patrol_boat_spaces == 0){
          System.out.println("Patrol Boat has been sunk.");
        }
        
        break;
   
        
      default:
        System.out.println();
    }//End i
    System.out.println();
  }
  System.out.println();
  System.out.println();
}

 //
 //areSpacesFree(Str, int[], int)
 //
 //Desc:
 //Tests if another ship already occupies one or more spaces that this ship would be placed on
 //
 //Directions:
 //The direction is inputted as a 'String', the beginning coordinate is inputted as an 'int[]', and the distance/length is inputted as an 'int'
 //The starting space must be a two value array and be formatted as [Horizontal, Vertical]
 //Within each case, if one of the potential spots this ship would be placed on a space that already has a another ship occupying it
 //then spaces_are_free is flipped to flase 
 //Otherwise, if the spot is free then spaces_are_free remains true
 //This method does not edit spaces on the board, it only handles validation
 //
 //Used by:
 //Board.placeShip();
 //
 public boolean areSpacesFree( String direction, int[] starting_space, int distance ){
  
  boolean spaces_are_free = true;
  
  if( starting_space.length == 2 ){ //Makes sure array is proper length
   
   switch( direction.toLowerCase() ){
   
   case "up":
     if (distance <= starting_space[1]){
       for( int i = (starting_space[1] - distance); i <= starting_space[1]; i++ ){
         if( !(player_board[starting_space[0]][i].equals("S")) ){
      
           spaces_are_free = false;
           
         }
       }
     }
     else
     {
       spaces_are_free = false;
     }
     break;
   
   case "down":
     if(distance + starting_space[1] < 9){
       for( int i = starting_space[1] + distance ; i >= starting_space[1]; i-- ){
         if( !(player_board[starting_space[0]][i].equals("S")) ){
    
           spaces_are_free = false;
      
         }
       }
     }
     else
     {
       spaces_are_free = false;
     }
     break;
    
   case "right":
     if(distance + starting_space[0] < 9){
       for( int i = starting_space[0] + distance ; i >=  starting_space[0]; i-- ){
         if( !(player_board[i][starting_space[1]].equals("S")) ){
      
           spaces_are_free = false;
      
         }
       }
     }
     else
     {
       spaces_are_free = false;
     }
     break;
    
   case "left":
     if (distance <= starting_space[0]){
       for( int i = starting_space[0] - distance; i <= starting_space[0]; i++ ){
         if( !(player_board[i][starting_space[1]].equals("S")) ){
      
           spaces_are_free = false;
      
         }
       }
     }
     else
     {
       spaces_are_free = false;
     }
    break;
    
   default:
    
    spaces_are_free = false;
   
   }//End switch statement
  }
  else
  {
    spaces_are_free = false;
  }
  
  return spaces_are_free;
  
 }
 
 
 //
 //placeAllShips()
 //
 //Desc:
 //5 calls to placeShip() to initialize each ship on the board
 //
 //Directions:
 //This method just outsources some of the code that would otherwise be in the Battleship constructor
 //
 //Used By:
 //the Battleship constructor
 public void placeAllShips(){
   
   this.placeShip("Aircraft Carrier", 5, 'A');
   remaining_carrier_spaces = 5;
   this.placeShip("Battleship", 4, 'B');
   remaining_battleship_spaces = 4;
   this.placeShip("Destroyer", 3, 'D');
   remaining_destroyer_spaces = 3;
   this.placeShip("Submarine", 3, 'U');
   remaining_submarine_spaces = 3;
   this.placeShip("Patrol Boat", 2, 'P');
   remaining_patrol_boat_spaces = 2;
   
 }
 
 //
 //getUserString(Str)
 //
 //Desc:
 //Gets user input as a String
 //
 //Directions:
 //Simpifies code within this.placeShip() by condesing a Scanner initialization
 //
 //Used by:
 //Board.placeShip();
 public String getUserString(String message){
   
   Scanner user_input = new Scanner(System.in);
   System.out.print(" " + message);
   
   return user_input.nextLine();
 }
 
 //
 //changeSpace(int[], char)
 //
 //Desc:
 //Changes a single space to a specified character
 //
 //Directions:
 //All validation must be done before callng this method
 //
 //Used by:
 //Board.changeSpaces()
 //Board.takeFire()
 //
 public void changeSpace(int[] coords_to_change, char change_to){
   
   player_board[coords_to_change[0]][coords_to_change[1]] = Character.toString(change_to);
   
 }
 
 //
 //changeSpaces(int[], char, int, direction)
 //
 //Desc:
 //Changes multiple spaces by looping calls of changeSpace
 //
 //Directions:
 //Must be passed: starting coordinates as an 'int[]', the new letter for the space must be a char, length of the ship is passed as an 'int', and the direction must be a 'String' 
 //changeSpaces() will follow the given direction and change coordinates X times, where X is the given length
 //All validation must be done prior to calling changeSpaces()
 //
 //Used by:
 //Board.placeShip();
 public void changeSpaces(int[] starting_coord, char change_to, int length, String direction){

   switch(direction){
     
     case "left":
       for(int i = 0; i<length; i++){
        this.changeSpace(new int[]{starting_coord[0] - i, starting_coord[1]}, change_to);
       }
       break;
       
     case "right":
       for(int i = 0; i<length; i++){
       this.changeSpace(new int[]{starting_coord[0] + i, starting_coord[1]}, change_to);
       }
       break;
       
     case "up":
       for(int i = 0; i<length; i++){
       this.changeSpace(new int[]{starting_coord[0], starting_coord[1] - i}, change_to);
       }
       break;
       
     case "down":
       for(int i = 0; i<length; i++){
       this.changeSpace(new int[]{starting_coord[0], starting_coord[1] + i}, change_to);
       }
       break;
       
     default:
       System.out.println("Invalid direction!");
   }
 }
 
 //
 //isValidShip(Str)
 //
 //Desc:
 //Test if a given String is a vaild ship name
 //
 //Directions:
 //If the given string equals one of the elements within ships_array, then valid_ship will be changed to true
 //Otherwise valid_ship will remain false
 //valid_ship is returned
 //
 //Used by:
 // - Unused
 //
 public boolean isValidShip(String ship_name){
  
   boolean vaild_ship = false;
   
   String[] ships_array = new String[]{"aircraft carrier", "battleship", "destroyer", "submarine", "patrol boat"};
   
   for(int i = 0; i<ships_array.length;i++){
     
     if (ships_array[i] == ship_name.toLowerCase()){
      
       vaild_ship = true;
       
     }
   }
   return vaild_ship;
 }
 
 //
 //isValidDirection(Str)
 //
 //Desc:
 //Given a string, this method tests if that string equals one of the four directions
 //
 //Directions:
 //The potential direction is matched up against four string values within an array
 //Returns a true/false value
 //
 //Used by:
 //Board.placeShip()
 //
 public boolean isValidDirection(String direction){
  
   boolean vaild_direction = false;
   
   String[] directions_array = new String[]{"up", "down", "left", "right"};
   
   for(int i = 0; i<directions_array.length;i++){
     
     if (directions_array[i].equals(direction.toLowerCase())){
      
       vaild_direction = true;
       
     }
   }
   return vaild_direction;
 }
 
 //
 //validDigit(Str)
 //
 //Desc:
 //Checks if the given string can be parsed into an integer value
 //
 //Directions:
 //Attempts to parse the given string into an integer
 //Catches NumberFormatException if the parsing fails
 //If successful, checks to see if the int is a single digit long
 //  -  If that is successful, then valid_digit is flipped to 'true'
 //If either of the if statements are unsuccessful valid-digit will remain 'false'
 //
 //Used by:
 //Unused
 //
 public boolean validDigit(String potential_digit){
   
   boolean valid_digit = true;
   
   try{
     
     if(Integer.parseInt(potential_digit) < 0){
       valid_digit = false;
     }
        
     if(Integer.parseInt(potential_digit) > 9){
       valid_digit = false; 
     }
     
   }catch( NumberFormatException e){
     
     valid_digit = false;
     
   }
   
   return valid_digit;
 }
 
 //
 //toIntArray(String[]
 //
 //Desc:
 //Converts string coordinate array (User input) into an int array
 //
 //Directions:
 //Attempts to parse each of the string elements into integers
 //If either fails then false will be returned
 //If both pass then true will be returned
 //
 //Used by:
 //placeShip()
 //
 public int[] toIntArray(String[] coords_string){
   
   int[] array_to_return;
   
   try{
   
     array_to_return = new int[]{Integer.parseInt(coords_string[0]), Integer.parseInt(coords_string[1])};
   
   }catch( Exception e ){
     
     array_to_return = false;
     
   }
   
   return array_to_return;
   
 }
 
 //
 //isValidCoordinate(int[])
 //
 //Desc:
 //Checks if an int array is formatted as a coordinate
 //
 //Directions:
 //Given an int array this methd checks
 //  -  If the int array's length equsls 2
 //  -  If either element is less than 0 or greater than 9
 //
 //Used by:
 //takeFire()
 //placeShip()
 //
 public boolean isValidCoordinate(int[] coords){
  
  boolean valid_coords = true;
  try{
    if (coords.length != 2){
      valid_coords = false;
    }
    if(coords[0] < 0 && coords[0] > 9){
      valid_coords = false;
    }
    if(coords[1] < 0 && coords[1] > 9){
      valid_coords = false;
    }
  }
  catch (Exception e){
    valid_coords = false;
  }
  return valid_coords;
 }
 
 //
 //getCoordinate()
 //
 //Desc:
 //Returns a single space from the board array
 //
 //Directions:
 //Typical getter function
 //
 //Used by:
 //Board.takeFire()
 //
 public String getCoordinate(int[] coord){
   return player_board[coord[0]][coord[1]];
 }
 
 //
 //shipSunk(int, Str)
 //
 //Desc:
 //Checks if a ship has been sunk
 //
 //Directions
 //Passed the remaining_spaces variable that matches the corresponding ship_name, which is passed as a String
 //If the ship has more than 0 spaces left then true is returned
 //Otherwise false will be returned
 //
 //Used by:
 //Board.printShipStatus()
 //Board.takeFire()
 //
 private boolean shipSunk(int remaining_spaces, String ship_name){
   
   boolean is_ship_sunk = false;
   
   if(remaining_spaces == 0){
     is_ship_sunk = true;
   }
   
   return is_ship_sunk;
 }
 
 //
 //printShipStatus(Str, int)
 //
 //Desc:
 //Prints the given ship's remaining spaces within the command line output
 //
 //Directions:
 //If a given ship has more than 0 spaces remaing then this will print the number of remaining spaces
 //Otherwise it will print that the given ship has been sunk
 //
 //Used by:
 //Unused
 //Replaced ny shipStatus()
 //
 private void printShipStatus(String ship_name, int remaining_spaces){
   
   if(this.shipSunk(remaining_spaces, ship_name) == false){
     
          System.out.println(remaining_spaces + " " + ship_name + " Spaces");
          
        } else {
          
          if(ship_name.equals("Carrier")){
            
            System.out.println("Aircraft Carrier has been sunk.");
            
          } else {
            
            System.out.println(ship_name + " has been sunk."); 
            
          }
      }
 
 }

 //
 //allShipsSunk()
 //
 //Desc:
 //Tests to see if the current player has had all their ships sunk
 //
 //Directions:
 //Returns true if all ships have been sunk on the current board (And ends the game)
 //Otherwise returns false
 //
 //Used by:
 //The Battleship constructor
 //
 public boolean allShipsSunk(){
   
   boolean all_ships_sunk = true;
   
   if(remaining_carrier_spaces != 0){
     all_ships_sunk = false;
   }
     
   if(remaining_battleship_spaces != 0){
     all_ships_sunk = false;
   }
   
   if(remaining_destroyer_spaces != 0){
     all_ships_sunk = false;
   }
   
   if(remaining_submarine_spaces != 0){
     all_ships_sunk = false;
   }
   
   if(remaining_patrol_boat_spaces != 0){
     all_ships_sunk = false;
   }
   
   return all_ships_sunk;
 }
 
 //
 //getBoard()
 //
 //Desc:
 //Returns the player_boar variable
 //
 //Used by:
 //Battleship.printBoard()
 public String[][] getBoard(){
   
   return player_board;

 }
 
}


