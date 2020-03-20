import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;


public class HumanPlayer extends Player{

    private int selection;
    
    public HumanPlayer(){
        super();
    }
    
    public HumanPlayer(String player_name, int player_id, int skor){
        super(player_name, player_id, skor);
    }
    
    private int cart_positions[][][] = new int[3][2][2];
    
    public int[][][] getCartPositions(){
        return cart_positions;
    }
    public void setCartPositions(int cart_positions[][][]){
        this.cart_positions = cart_positions;
    }
    
    
    @Override
    public int select_cart(int x, int y) {
        
       if(cart_positions[0][0][0] <= x && x <= cart_positions[0][0][0] + 127 && 
                cart_positions[0][1][0] <= y &&  y <= cart_positions[0][1][0] + 175){
            selection = 1;
        }
        
        if(cart_positions[1][0][0] <= x && x <= cart_positions[1][1][0] + 127 && 
                cart_positions[1][1][0] <= y &&  y <= cart_positions[1][1][0] + 175){
            selection = 2;
        }
        
        if(cart_positions[2][0][0] <= x && x <= cart_positions[2][0][0] + 127 && 
                cart_positions[2][1][0] <= y &&  y <= cart_positions[2][1][0] + 175 ){
            selection = 3;
        }
        
       System.out.println(selection);
       return selection;
    }
    
    

    

   

   
}
