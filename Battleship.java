/**
 * 
 */
//package battleship;

/**
 * @author DJ
 *
 */
public class Battleship {

  public Battleship(){
    Board player1 = new Board();
    Board player2 = new Board();
  }
  
  
 public static void main(String[] args) {
  // TODO Auto-generated method stub
   Battleship b = new Battleship();
   
   while( player1.allShipsSunk() == false && player2.allShipsSunk() == false ){
     
   }
   
 }
 
 public void fireShot(Board board_getting_shot_at, int[] coordinates){
   
   board_getting_shot_at.takeFire();
   
 }
 
  //This method displays the board within the console (for Testing purposes)
 public void printBoard(Board board_to_print){
   
   System.out.println("     0  1  2  3  4  5  6  7  8  9");
   System.out.println();
   
   for(int i = 0; i< 10; i++){
     System.out.print(i + "    ");
     
     for (int j = 0; j < 10; j++){
       System.out.print(board_to_print[j][i] + "  ");
     }
   System.out.println();
   }
 }
 
 
 
  private String getUserString(String message){
   
   Scanner user_input = new Scanner(System.in);
   System.out.print(" " + message);
   
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
