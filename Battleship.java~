/**
 * 
 */
//package battleship;

/**
 * @author DJ
 *
 */

import java.util.Scanner;

public class Battleship {

  Board player1;
  ComputerBoard player2;

  public Battleship(){

    System.out.println("Welcome to Battleship!");
    System.out.println("Coded by DJ Canty, special thanks to his python Jeff and his fish Meme Pappa");
    System.out.println("*");
    System.out.println("*");
    System.out.println("*");
    System.out.println("Now to kick things off, would you like an easy game, or a hard game?");
    
    String difficulty;
    do{
      difficulty =  this.getUserString("Choose Easy or Hard: ");
      difficulty = difficulty.toLowerCase();
    }while( !(difficulty.equals("easy")) && !(difficulty.equals("hard")));
  
     if(difficulty.equals("easy")){
      
      player1 = new Board();
      player2 = new ComputerBoard();
      
      int player_who_goes_first = player2.generateRandomInt(1); //0 means human player goes first, 1 means computer player goes first

      if(player_who_goes_first == 0){

        System.out.println();
        System.out.println("You go first!");
        System.out.println();

      }
      else
      {

        System.out.println();
        System.out.println("Computer goes first!");
        System.out.println();

      }

      player1.printBoard();

      while(player1.allShipsSunk() != player2.allShipsSunk()){
        
        if(player_who_goes_first == 0){

          player2.takeFire(this.getShotCoordinates());
          System.out.println();
          System.out.println("Computer Board:");
          System.out.println();
          player2.printBoard();
          player1.takeFire(player2.generateRandomCoordinate());
          System.out.println();
          System.out.println("Your Board:");
          player1.printBoard();

        }else{

          player1.takeFire(player2.generateRandomCoordinate());
          player2.takeFire(this.getShotCoordinates());

        }
  
      }  
    }

  }
  
  
  public int[] getShotCoordinates(){

    String potenial_coordinates;
    int[] coordinates_to_return = null;

    do{
      potenial_coordinates = player1.getUserString("Enter shot coordiantes as 'X,Y': ");
      coordinates_to_return = player1.toIntArray(potenial_coordinates.split(","));
    }while(coordinates_to_return == null);

  return coordinates_to_return;

  }

 public static void main(String[] args) {
  // TODO Auto-generated method stub

  Battleship b = new Battleship();

  
   
 }
 
 public void shotFiredAt(Board board_getting_shot_at, int[] coordinates){
   
   board_getting_shot_at.takeFire(coordinates);
   
 }
 
  //This method displays the board within the console (for Testing purposes)
 public void printBoard(Board board_to_print){
   
   System.out.println("     0  1  2  3  4  5  6  7  8  9");
   System.out.println();
   
   for(int i = 0; i< 10; i++){
     System.out.print(i + "    ");
     
     for (int j = 0; j < 10; j++){
       System.out.print(board_to_print.getBoard()[j][i] + "  ");
     }
   System.out.println();
   }
 }
 
 
 
  private String getUserString(String message){
   
   Scanner user_input = new Scanner(System.in);
   System.out.print(message);
   
   return user_input.nextLine();
 }
  
  
  
 public int[] toIntArray(String[] coords_string){
   
   int[] array_to_return;
   
   try{
   
   array_to_return = new int[]{Integer.parseInt(coords_string[0]), Integer.parseInt(coords_string[1])};
   
   }catch( Exception e ){
     
     array_to_return = null;
     
   }
   
   return array_to_return;
   
 }
 
 
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

}
