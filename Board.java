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


public class Board {
 
 String[][] player_board;  //Board array for one player
 int remaining_carrier_spaces;
 int remaining_battleship_spaces;
 int remaining_destroyer_spaces;
 int remaining_submarine_spaces;
 int remaining_patrol_boat_spaces;
 
 public Board(){
  
  player_board = new String[10][10];
  
  //Iterates through each nested array and sets each of it's values to 'S'
  for ( int i = 0; i<10; i++ ){
   
   Arrays.fill(player_board[i], "S");
   
  }
  this.printBoard();
  this.placeAllShips();
 }
 
 public static void main(String[] args) {
   Board b = new Board();
   while(b.allShipsSunk() == false){
     b.printBoard();
     System.out.println(b.takeFire(b.toIntArray(b.getUserString("Fire!").split(",",2))));
     
   }
   System.out.println("All ship have been sunk, You win! And you lose! You're a winner but you suck!");
 }
 
 
 //Takes input from a player and returns whether the shot hit a ship or missed
 //Calls changeSpace to edit board accordingly
 //Handles validation
 public String takeFire(int[] shot_coords ){
  
   String shot_result = "Error";
   
   if( this.isValidCoordinate(shot_coords) ){
     
     switch(this.getCoordinate(shot_coords)){
       
       case "S":
         shot_result = "Miss";
         this.changeSpace(shot_coords, 'M');
         break;
       
       case "X":
         shot_result = "Miss";
         break;
       
       case "M":
         shot_result = "Miss";
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
 
 
 //Method to place a ship on the board
 //Must be passed an array of the ships data
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
   }
   else
   {
     System.out.println("Inputs not valid.");
     this.placeShip(ship_name,ship_size,ship_char);
   }
  
 }
 
 
 //This method displays the board within the console (for Testing purposes)
 public void printBoard(){
   
   System.out.println("     0  1  2  3  4  5  6  7  8  9");
   System.out.println();
   
   for(int i = 0; i< 10; i++){
     System.out.print(i + "    ");
     
     for (int j = 0; j < 10; j++){
       System.out.print(player_board[j][i] + "  ");
     }
   System.out.println();
   }
 }
 
 //Method to test if a ship exists on a set of spaces
 //Only used for ship placement, not for firing/gameplay
 //Needs direction, starting space, and length of spaces to be passed
 //The starting space must be a two value array and be formatted as [Horizontal, Vertical]
 //The placement is relative to space (0,0) being located in the top left corner
 //Valid directions are Up, Down, Left, and Right
 public boolean areSpacesFree( String direction, int[] starting_space, int distance ){
  
  boolean spaces_are_free = true;
  
  if( starting_space.length == 2 ){ //Make sure array is proper length
   
   switch( direction.toLowerCase() ){
   
   case "up":
     if (distance <= starting_space[1]){
       for( int i = (starting_space[1] - distance); i < starting_space[1]; i++ ){
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
       for( int i = starting_space[1] + distance ; i > starting_space[1]; i-- ){
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
       for( int i = starting_space[0] + distance ; i >  starting_space[0]; i-- ){
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
       for( int i = starting_space[0] - distance; i > starting_space[0]; i++ ){
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
 
 
 //Sequentially makes the player place ships on the board
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
 
 //Gets user input as a String
 public String getUserString(String message){
   
   Scanner user_input = new Scanner(System.in);
   System.out.print(" " + message);
   
   return user_input.nextLine();
 }
 
 //Changes a single space to a specified character
 public void changeSpace(int[] coords_to_change, char change_to){
   
   player_board[coords_to_change[0]][coords_to_change[1]] = Character.toString(change_to);
   
 }
 
 
 //Changes multiple spaces by looping calls of changeSpace
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
 
 //Test if given String is a vaild ship name and returns a true/false value
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
 
 //Tests is given direction is valid
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
 
 //Checks if string input is a valid digit
 //Handles NumberFormatException
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
 
 //Converts string coordinate array (User input) into int array
 public int[] toIntArray(String[] coords_string){
   
   int[] array_to_return;
   
   try{
   
   array_to_return = new int[]{Integer.parseInt(coords_string[0]), Integer.parseInt(coords_string[1])};
   
   }catch( Exception e ){
     
     array_to_return = null;
     
   }
   
   return array_to_return;
   
 }
 
 //Checks if a coordinate is valid
 //Uses validDigit
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
 
 //retrieves coordinate from current board
 public String getCoordinate(int[] coord){
   return player_board[coord[0]][coord[1]];
 }
 
 //Checks if a ship has been sunk
 //Displays corresponding message
 private void shipSunk(int remaining_spaces, String ship_name){
   
   if(remaining_spaces == 0){
     System.out.println("You sunk opponents " + ship_name + "!");
   }
 }
 
 
 //Outputs all ships that have been sunk on the current board
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
}


