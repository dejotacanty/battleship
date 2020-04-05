import java.lang.*;
import java.util.*;


public class ComputerBoard extends Board{
  
  String[][] player_board;
  String[][] player_display_board;
  int remaining_carrier_spaces;
  int remaining_battleship_spaces;
  int remaining_destroyer_spaces;
  int remaining_submarine_spaces;
  int remaining_patrol_boat_spaces;

  public ComputerBoard(){
    player_board = new String[10][10];
    player_display_board = player_board;
  
  //Iterates through each nested array and sets each of it's values to 'S'
    for ( int i = 0; i<10; i++ ){
      for (int j = 0; j < 10; j++){
           player_board[i][j] = "S";
      }
    }
    this.placeAllShips();
  }

  public static void main(String[] args){

     ComputerBoard b = new ComputerBoard();
    // b.placeAllShips();
  }
  
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


  public int[] fireRandomShot(){

    return this.generateRandomCoordinate();

  }


  public void placeShip( String ship_name, int ship_size, char ship_char ){
  
    String direction;
    int[] coords_int;
    
    coords_int = this.generateRandomCoordinate();
    
    direction = this.returnRandomDirection();
    
    if(this.areSpacesFree(direction, coords_int, ship_size)){
      System.out.println(direction + ";   Coords = "+ coords_int[0] + ", "+coords_int[1] + ";  ship_size = " +ship_size + ";  AreSpacesFree = " +this.areSpacesFree(direction, coords_int, ship_size));
      this.changeSpaces(coords_int, ship_char, ship_size, direction);
      System.out.println("Oh poop I changed a space");
    }
    else
    {
      System.out.println("Whoops I fucked up");
      this.placeShip(ship_name,ship_size,ship_char);
      System.out.println("Oh damn I cahnged a space!");
    }
   
  }

  public int[] generateRandomCoordinate(){

    return new int[]{this.generateRandomInt(10) , this.generateRandomInt(10)};

  }

  public int generateRandomInt(int max_number) {

    int int_to_return = (int)Math.ceil(Math.random() * Math.ceil(max_number));
  
    int_to_return--;

    if(int_to_return < 0){
      int_to_return++;
    }

    return int_to_return;

  }

  public String returnRandomDirection(){

    String[] directions = new String[]{"left", "right", "up", "down"};

    return directions[this.generateRandomInt(3)];

  }

   public void cloakBoard(){

    String[][] cloaked_board = new String[10][10];
    boolean hide_character = true;

    for(int i = 0; i < 10; i++){
      for(int j = 0; j < 10; j++){
        

          if(player_board[i][j].equals("M")){
            System.out.println("M");
            hide_character = false;
          } else if (player_board[i][j].equals("X")){
            System.out.println("X");
            hide_character = false;
          }

        if(!hide_character){

          cloaked_board[i][j] = player_board[i][j];

        }else{
            
          cloaked_board[i][j] = "S";  
          
        }
      }
    }
    
    player_display_board = cloaked_board;
  }


   public void printBoard(){
     this.cloakBoard();
     this.printBoard(player_display_board); //Aka, the board to display to the human player
   }
   
 

}