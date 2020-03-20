
public abstract class Player {
    private String player_name;
    private int player_id, skor;
    private int current_cart_number;
    
    
    public Player(){
        player_name = "Unkown";
        player_id = -1;
        skor = 0;
    }
    
    public Player(String player_name, int player_id, int skor){
        this.player_name = player_name;
        this.player_id = player_id;
        this.skor = skor;
    }
    
    private int cart_list[] = new int[3];
    
    public int getCartList(int sira){
        return cart_list[sira];    
    } 
    
    public void setCartList(int cart_list[]){
        this.cart_list = cart_list;
    }
    
    public void setCartList(int sira, int id){
        this.cart_list[sira] = id;
    }
    
    public int getSkor(){
        return skor;
    }
    public void setSkor(int skor){
        this.skor = skor;
    }
    
    private int cart_number;
    
    public int getCartNumber(){
        return cart_number;
    }
    public void setCartNumber(int cart_number){
        this.cart_number = cart_number;
    }
    
    public int getter_current_cart_number()
    {
        return current_cart_number;
    }
    
    public void setter_current_cart_number(int current_cart_number)
    {
        this.current_cart_number=current_cart_number;
    }
    //yoket
    private int cart_positions[][][] = new int[3][2][2];
    
    public int[][][] getCartPositions(){
        return cart_positions;
    }
    public void setCartPositions(int cart_positions[][][]){
        this.cart_positions = cart_positions;
    }
    //yoket
    
    public String getPlayerName(){
        return player_name;
    }
    
    public void setPlayerNamme(String player_name){
        this.player_name = player_name;
    }
    
    public abstract int select_cart(int x, int y);
    
    
}
