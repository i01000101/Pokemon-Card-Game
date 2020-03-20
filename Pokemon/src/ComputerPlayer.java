import java.util.Random;


public class ComputerPlayer extends Player{
    
    
    public ComputerPlayer(){
        super();
    }
    
    public ComputerPlayer(String player_name, int player_id, int skor){
        super(player_name, player_id, skor);
    }
    
    
    @Override
    public int select_cart(int x, int y) {
        Random r = new Random();
        int selection = r.nextInt(getCartNumber()) + 1;//getCartNumber() + 1
        try {
        Thread.sleep(2500);
        }catch (InterruptedException ex) {
            System.out.println("Uyuyamadım");
        }
        return selection;
    }
    
    
}
