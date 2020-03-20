
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Core extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
    
    BufferedImage pokeball, go_button, corner, back;
    
    static JFrame window;
    private static int cart_positions[][][] = new int[6][2][1];
    private static int sub_cart_positions[][][] = new int[3][2][1];
    private static int carts_middle_positions[][] = new int[4][2];
    private static int carts_middle_id[] = new int[4];
    
    JLabel arka, banner1name, banner2name, banner1skor, banner2skor, skynetphoto, firstplayerpp, gameower,
            brokeeffect1, brokeeffect2, brokeeffect3, brokeeffect4, distructions;
    
    private int go_X = 630, go_Y = 460, go_width = 100, go_height = 50;
    static int X,Y;
    boolean did_go_bigger = false;
    static boolean is_pc, is_god;
    static Player players[] = new Player[2];
    
    Toolkit tk = Toolkit.getDefaultToolkit();
    public static int xsize, ysize;
    
    static pokemon[] pokemons = new pokemon[10];
    
    Timer wait_timer = new Timer(5000,this);
    boolean is_waiting = false;
    
    Timer cart_going_animation = new Timer(1,this);
    static boolean does_cart1_go = false;
    static boolean does_cart2_go = false;
    static boolean cart1_step1 = false, cart1_step2 = false;
    static boolean cart2_step1 = false, cart2_step2 = false;
    static boolean go_is_visible = false;
    static boolean is_go_clicked;
    static boolean is_fnish = false;
    static boolean is_machine_selected = false;
    static boolean is_cart_bigger[] = {false,false,false};
    static boolean is_middle_cart_bigger[] = {false,false,false,false};
    boolean is_selected = false, is_selecting = false, is_taked = false, is_taking = false;
    static int selected_cart1 = 1, selected_cart2 = 1;
    
    int selection_middle_cart, selection_cart_position;
    
    Font font;
    static Random r = new Random();
    
    public Core(boolean is_pc){
        
        this.setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);
        try {
            pokeball = ImageIO.read(new File("pokeball.png"));
            go_button = ImageIO.read(new File("go.png"));
            corner = ImageIO.read(new File("corner.png"));
            back = ImageIO.read(new File("back.png"));
        } catch (IOException ex) {
            System.err.println("Arka plan ya da bişiler yüklenemedi");
        }
        
        this.is_pc = is_pc;
        
       
        xsize = (int) tk.getScreenSize().getWidth();
        ysize = (int) tk.getScreenSize().getHeight();
                
            for(int i = 0, j = 0; i < 6; i++){
                if(i < 3){
                    cart_positions[i][0][0] = 50;
                    cart_positions[i][1][0] = 50 + i*235;
                }else{
                    cart_positions[i][0][0] = 1150;
                    cart_positions[i][1][0] = 50 + j*235;
                    j++;
                }
            }
            
            for(int i = 0; i < 3; i++){//bu çirkinliği yok et!
                sub_cart_positions[i][0][0] = cart_positions[i][0][0];
                sub_cart_positions[i][1][0] = cart_positions[i][1][0];
            }
            
            for(int i = 0, m = 0; i < 2; i++){
                for(int j = 0; j < 2; j++){
                    carts_middle_positions[m][0] = 630 + j*60;
                    carts_middle_positions[m][1] = 15 + i*65;
                    m++;
                }
            }
            
        pokemons[0] = new pikachu("Pikachu",1,"Electric");pokemons[0].set_damage_point(40);pokemons[0].setter_is_open(is_god);
        pokemons[1] = new Bulbasaur("Bulbasaur",2,"Grass");pokemons[1].set_damage_point(50);pokemons[1].setter_is_open(is_god);
        pokemons[2] = new Charmander("Charmander",3,"Fire");pokemons[2].set_damage_point(60);pokemons[2].setter_is_open(is_god);
        pokemons[3] = new Squirtle("Squirtle",4,"Water");pokemons[3].set_damage_point(30);pokemons[3].setter_is_open(is_god);
        pokemons[4] = new Zubat("Zubat",5,"Air");pokemons[4].set_damage_point(50);pokemons[4].setter_is_open(is_god);
        pokemons[5] = new Psyduck("Psyduck",6,"Water");pokemons[5].set_damage_point(20);pokemons[5].setter_is_open(is_god);
        pokemons[6] = new Snorlax("Snorlax",7,"Normal");pokemons[6].set_damage_point(30);pokemons[6].setter_is_open(is_god);
        pokemons[7] = new Butterfree("Butterfree",8,"Air");pokemons[7].set_damage_point(10);pokemons[7].setter_is_open(is_god);
        pokemons[8] = new Jigglypuff("Jigglypuff",9,"Sound");pokemons[8].set_damage_point(70);pokemons[8].setter_is_open(is_god);
        pokemons[9] = new Meowth("Meowth",10,"Normal");pokemons[9].set_damage_point(40);pokemons[9].setter_is_open(is_god);
            
        
        if(is_pc){
            players[0] = new ComputerPlayer("THOMAS ANDERSON", 1, 0);
        }else{
            players[0] = new HumanPlayer("ASH", 1, 0);
            players[0].setCartPositions(sub_cart_positions);
            
        }
        
        players[1] = new ComputerPlayer("SKYNET", 2, 0);
        
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("Pokemon Solid.ttf"));
            font = font.deriveFont(15f);
        } catch (FontFormatException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        arka = new JLabel();
        arka.setBounds(0, 0, xsize, ysize);
        arka.setIcon(new ImageIcon(new ImageIcon("background.png").getImage().getScaledInstance(xsize, ysize, Image.SCALE_DEFAULT)));
        
        skynetphoto = new JLabel();
        skynetphoto.setBounds(730, 285, 100, 60);
        skynetphoto.setIcon(new ImageIcon(new ImageIcon("skynetphoto.png").getImage().getScaledInstance(100, 60, Image.SCALE_DEFAULT)));
        
        firstplayerpp = new JLabel();
        firstplayerpp.setBounds(is_pc?545:525, 285, is_pc?80:100, is_pc?80:60);
        firstplayerpp.setIcon(new ImageIcon(new ImageIcon(is_pc?"neopp.jpeg":"normalash.png").getImage().getScaledInstance(is_pc?80:100, is_pc?80:60, Image.SCALE_DEFAULT)));
        
        
        brokeeffect1 = new JLabel();
        brokeeffect1.setBounds(490, 220, 110, 100);
        brokeeffect1.setIcon(new ImageIcon(new ImageIcon("brokeeffect1.png").getImage().getScaledInstance(110, 100, Image.SCALE_DEFAULT)));
        brokeeffect1.setVisible(false);
        
        brokeeffect2 = new JLabel();
        brokeeffect2.setBounds(530, 282, 100, 85);
        brokeeffect2.setIcon(new ImageIcon(new ImageIcon("brokeeffect1.png").getImage().getScaledInstance(100, 85, Image.SCALE_DEFAULT)));
        brokeeffect2.setVisible(false);
        
        brokeeffect3 = new JLabel();
        brokeeffect3.setBounds(710, 265, 120, 100);
        brokeeffect3.setIcon(new ImageIcon(new ImageIcon("brokeeffect1.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        brokeeffect3.setVisible(false);
        
        brokeeffect4 = new JLabel();
        brokeeffect4.setBounds(740, 230, 150, 120);
        brokeeffect4.setIcon(new ImageIcon(new ImageIcon("brokeeffect1.png").getImage().getScaledInstance(150, 120, Image.SCALE_DEFAULT)));
        brokeeffect4.setVisible(false);
        
        
        banner1name = new JLabel(players[0].getPlayerName());
        banner1name.setBounds(is_pc ? 517:567, 225, 200, 40);
        banner1name.setFont(font);
        banner1name.setForeground(new Color(255, 203, 5));
        
        banner2name = new JLabel(players[1].getPlayerName());
        banner2name.setBounds(755, 225, 200, 40);
        banner2name.setFont(font);
        banner2name.setForeground(new Color(255, 203, 5));
        
        banner1skor = new JLabel(Integer.toString(players[0].getSkor()));
        banner1skor.setBounds(577, 250, 200, 40);
        banner1skor.setFont(font);
        banner1skor.setForeground(new Color(255, 203, 5));
        
        banner2skor = new JLabel(Integer.toString(players[1].getSkor()));
        banner2skor.setBounds(776, 250, 200, 40);
        banner2skor.setFont(font);
        banner2skor.setForeground(new Color(255, 203, 5));
        
        
        gameower = new JLabel();
        gameower.setBounds(430, 85, 500, 500);
        gameower.setVisible(false);
        
        font = font.deriveFont(55f);
        
        distructions = new JLabel();
        distructions.setFont(font);
        distructions.setForeground(Color.RED);
        distructions.setVisible(false);
        
        add(distructions);
        add(gameower);
        add(brokeeffect1);
        add(brokeeffect2);
        add(brokeeffect3);
        add(brokeeffect4);
        add(banner1name);
        add(banner2name);
        add(banner1skor);
        add(banner2skor);
        add(firstplayerpp);
        add(skynetphoto);
        add(arka);
        
        
        cart_going_animation.start();
        
        
        
        
        
    }
       
        
    public static void main(String[] args) {
        
        window = new JFrame();
        
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(150, 85, 1000, 600);
        window.setUndecorated(true);
        window.setAutoRequestFocus(true);
        //window.setAlwaysOnTop(true);
        window.setVisible(true);
        
        StartupScreen startPage = new StartupScreen();
        startPage.setBounds(0,0,1000,600);
        window.add(startPage);
        
        while(startPage.getVisible()){//düzelt
            System.err.print(""); 
        }
        is_god = startPage.isGod();
        
        Core mainScreen = new Core(startPage.getOyuncu1_makine_mi());
        window.setBounds(0,0,xsize,ysize);
        mainScreen.setBounds(0,0,xsize,ysize);
        
        window.add(mainScreen);
        mainScreen.setVisible(true);
        
        int given_number = 0;
       
        int random_number = r.nextInt(10);
        int temporal_cart_list[] = new int[3];
        
        while(given_number < 3){
            
            if(pokemons[random_number].getter_is_used() == false){
                pokemons[random_number].setter_is_used(true);
                temporal_cart_list[given_number] = pokemons[random_number].getter_pokemon_id();
                random_number = r.nextInt(10);
                given_number++;
            }else if(pokemons[random_number].getter_is_used() == true){
                random_number = r.nextInt(10);
                continue;
            }
        }
        mainScreen.players[0].setCartNumber(3);
        players[0].setCartList(temporal_cart_list);
        if(players[0].getClass() == new HumanPlayer().getClass()){
            for(int i = 0; i < 3; i++){
                pokemons[players[0].getCartList(i) - 1].setter_is_open(true);
            }
        }
        given_number=0;
        random_number = r.nextInt(10);
        int temporal_cart_list2[] = new int[3];
        
        while(given_number < 3)
        {
            if(pokemons[random_number].getter_is_used() == false){
                
                pokemons[random_number].setter_is_used(true);
                temporal_cart_list2[given_number]=pokemons[random_number].getter_pokemon_id();
                random_number = r.nextInt(10);
                given_number++;
                mainScreen.players[1].setter_current_cart_number(given_number);
            }else if(pokemons[random_number].getter_is_used()==true){
                random_number = r.nextInt(10);
                continue;
            }
            
            
        }
        mainScreen.players[1].setCartNumber(3);
        mainScreen.players[1].setCartList(temporal_cart_list2);
        
        
        for(int i = 0, j = 0; i < 4; i++){
            random_number = r.nextInt(10);
            if(!pokemons[random_number].getter_is_used()){
                pokemons[random_number].setter_is_used(true);
                carts_middle_id[j] = random_number + 1; 
                j++;
            }else{
                i--;
            }
        
        }
      
       
        selecter(mainScreen);
        
        System.out.println(mainScreen.players[0].getPlayerName() +
                "'nın skoru: " + mainScreen.players[0].getSkor());
        System.out.println(mainScreen.players[1].getPlayerName() +
                "'nın skoru: " + mainScreen.players[1].getSkor());
      
        
    }
    
    public static void selecter(Core mainScreen){
        int number_of_cart = 10;
        
        
        while(number_of_cart != 0){
            
            cart1_step1 = false; cart1_step2 = false;
            cart2_step1 = false; cart2_step2 = false;
            is_go_clicked = false;
            
            
            
            int select_number2;
                do{
                select_number2 = mainScreen.players[1].select_cart(0,0);
                }while(pokemons[mainScreen.players[1].
                    getCartList(select_number2 - 1) -1].getIsSelected());
                selected_cart2 = select_number2;
                pokemons[mainScreen.players[1].
                    getCartList(select_number2 - 1) -1].setIsSelected(true);
                is_machine_selected = true;
                mainScreen.repaint();
            
            if(mainScreen.players[0].getClass() == new HumanPlayer().getClass()){
                mainScreen.distructions.setText("SELECT A CARD");
                mainScreen.distructions.setBounds(480, 600, 500, 100);
                mainScreen.distructions.setVisible(true);
            }    
                
            int select_number1; 
                if(players[0].getClass() == new ComputerPlayer().getClass()){
                    do{    
                      select_number1 = mainScreen.players[0].select_cart(0,0);
                    }while(pokemons[mainScreen.players[0].
                        getCartList(select_number1 - 1) -1].getIsSelected());
                }else{
                    mainScreen.is_selecting = true;
                    while(!mainScreen.is_selected){
                        //System.out.print("");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    select_number1 = mainScreen.players[0].select_cart(X,Y);
                    mainScreen.is_selected = false;
                }
                selected_cart1 = select_number1;
                pokemons[mainScreen.players[0].
                getCartList(select_number1 - 1) -1].setIsSelected(true);mainScreen.distructions.setVisible(false);
                is_machine_selected = false;
                //nabura
            
            
            
            int selection_firts_player = pokemons[mainScreen.players[0].
                    getCartList(select_number1 - 1) -1].showDamagePoint();
            int selection_second_player = pokemons[mainScreen.players[1].
                    getCartList(select_number2 - 1) -1].showDamagePoint();
            
            does_cart1_go = true;
            does_cart2_go = true;
            /*
            try {
            Thread.sleep(30000);
            }catch (InterruptedException ex) {
            System.out.println("Uyuyamadım");
            }
            */
            while(!is_go_clicked){//kaldır
                try {
            Thread.sleep(500);
            }catch (InterruptedException ex) {
            System.out.println("Uyuyamadım");
            }
                
                //System.out.println("");
            }
            
            if(selection_firts_player > selection_second_player){
                System.err.println("Birinci oyuncu kazandı");
                mainScreen.players[0].setSkor(mainScreen.players[0].getSkor() + 5);
                mainScreen.banner1skor.setText(Integer.toString(players[0].getSkor()));
            }
        
            if(selection_firts_player < selection_second_player){
                System.err.println("İkinci oyuncu kazandı");
                mainScreen.players[1].setSkor(mainScreen.players[1].getSkor() + 5);
                mainScreen.banner2skor.setText(Integer.toString(players[1].getSkor()));
            }
            
            if(selection_firts_player == selection_second_player){
                System.err.println("Berabere");
            }
            if(!is_god){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    System.out.println("Uyku problemlerim var");
                }
            }
            pokemons[players[0].getCartList(selected_cart1 - 1) - 1].setIsExist(false);
            pokemons[players[1].getCartList(selected_cart2 - 1) - 1].setIsExist(false);
            
        mainScreen.repaint();    
            
        
        if(players[1].getSkor() >= 5){mainScreen.brokeeffect1.setVisible(true);}
        if(players[1].getSkor() >= 10){mainScreen.brokeeffect2.setVisible(true);}
            
            
        if(players[0].getSkor() >= 5){mainScreen.brokeeffect3.setVisible(true);}
        if(players[0].getSkor() >= 10){mainScreen.brokeeffect4.setVisible(true);;}
        
        if(players[0].getSkor() > players[1].getSkor() && !is_pc){
            mainScreen.firstplayerpp.setBounds(545, 285,75,100);
            mainScreen.firstplayerpp.setIcon(new ImageIcon(new ImageIcon("happyash.png").getImage().getScaledInstance(75,100, Image.SCALE_DEFAULT)));
        }
        if(players[0].getSkor() < players[1].getSkor() && !is_pc){
            mainScreen.firstplayerpp.setBounds(535, 290,90,100);
            mainScreen.firstplayerpp.setIcon(new ImageIcon(new ImageIcon("sadash.png").getImage().getScaledInstance(90,100, Image.SCALE_DEFAULT)));
        
        }
        if(players[0].getSkor() == players[1].getSkor() && !is_pc){
            mainScreen.firstplayerpp.setBounds(525, 285,100,60);
            mainScreen.firstplayerpp.setIcon(new ImageIcon(new ImageIcon("normalash.png").getImage().getScaledInstance(100,60, Image.SCALE_DEFAULT)));
        }
        
        
        if(number_of_cart > 6){
            takeCart(players[0], mainScreen);
            takeCart(players[1], mainScreen);
            }mainScreen.repaint();
            
            number_of_cart -= 2;
        }
        
        is_fnish = true;
        if(players[1].getSkor() > players[0].getSkor()){
            mainScreen.gameower.setIcon(new ImageIcon(new ImageIcon("skynetwon.jpeg").getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT)));
            mainScreen.gameower.setVisible(true);
        }
        if(players[1].getSkor() < players[0].getSkor()){
            mainScreen.gameower.setIcon(new ImageIcon(new ImageIcon(is_pc?"neowon.jpg":"humanitywon.jpg").getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT)));
            mainScreen.gameower.setVisible(true);
        }
        if(players[1].getSkor() == players[0].getSkor()){
            mainScreen.gameower.setBounds(170, 100, 1000, 500);
            mainScreen.gameower.setIcon(new ImageIcon(new ImageIcon("peace.jpg").getImage().getScaledInstance(1000, 500, Image.SCALE_DEFAULT)));
            mainScreen.gameower.setVisible(true);
        }
    }
    
    public static void takeCart(Player player, Core mainScreen){
        
        
        if(player.getClass() == new ComputerPlayer().getClass()){
            do{
                mainScreen.selection_middle_cart = r.nextInt(4);
            }while(pokemons[carts_middle_id[mainScreen.selection_middle_cart] - 1].getIsTaken());
            
            pokemons[carts_middle_id[mainScreen.selection_middle_cart] - 1].setIsTaken(true);
            
            do{
                mainScreen.selection_cart_position = r.nextInt(3);
            }while(pokemons[player.getCartList(mainScreen.selection_cart_position) - 1].getIsExist());
            player.setCartList(mainScreen.selection_cart_position, carts_middle_id[mainScreen.selection_middle_cart]);
        
        }else{
            mainScreen.is_taking = true; mainScreen.is_taked = false;
            
            mainScreen.distructions.setText("TAKE A POKEBALL");
            mainScreen.distructions.setBounds(450, 600, 500, 100);
            mainScreen.distructions.setVisible(true);
            
            while(!mainScreen.is_taked){
                try {//tikkat
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        pokemons[carts_middle_id[mainScreen.selection_middle_cart] - 1].setIsTaken(true);
        pokemons[carts_middle_id[mainScreen.selection_middle_cart] - 1].setter_is_open(true);
        do{
               mainScreen.selection_cart_position = r.nextInt(3);
           }while(pokemons[player.getCartList(mainScreen.selection_cart_position) - 1].getIsExist());
           player.setCartList(mainScreen.selection_cart_position, carts_middle_id[mainScreen.selection_middle_cart]);
        
        }
        
        
        
            for(int i = 0, j = 0; i < 6; i++){
                if(i < 3){
                    cart_positions[i][0][0] = 50;
                    cart_positions[i][1][0] = 50 + i*235;
                }else{
                    cart_positions[i][0][0] = 1150;
                    cart_positions[i][1][0] = 50 + j*235;
                    j++;
                }
            }
            
            for(int i = 0; i < 3; i++){//bu çirkinliği yok et!
                sub_cart_positions[i][0][0] = cart_positions[i][0][0];
                sub_cart_positions[i][1][0] = cart_positions[i][1][0];
            }
            if(player.getClass() == new HumanPlayer().getClass()){
                player.setCartPositions(sub_cart_positions);
            }
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
       
        for(int i = 0, j = 0, m = 0; i < 6; i++, m++){
            if(i == 3){j = 1; m = 0;}
            if(pokemons[players[j].getCartList(m) - 1].getIsExist()){  
                 g.drawImage(pokemons[players[j].getCartList(m) - 1].getter_is_open()?
                         pokemons[players[j].getCartList(m) - 1].getter_cart_image():back,
                   cart_positions[i][0][0] - ((i < 3 && is_cart_bigger[i])?4:0),
                   cart_positions[i][1][0] - ((i < 3 && is_cart_bigger[i])?4:0),
                   127 + ((i < 3 && is_cart_bigger[i])?8:0), 175 + ((i < 3 && is_cart_bigger[i])?8:0),this);
            }
        }
        
        for(int i = 0; i < 4; i++){
      
            if(!pokemons[carts_middle_id[i] - 1].getIsTaken() && is_god){//god mod
                g.drawImage(pokemons[carts_middle_id[i] - 1].getter_cart_image(),
                 carts_middle_positions[i][0] - (is_middle_cart_bigger[i]?2:0),
                 carts_middle_positions[i][1] - (is_middle_cart_bigger[i]?2:0), 
                 40 + (is_middle_cart_bigger[i]?4:0), 60 + (is_middle_cart_bigger[i]?4:0), this);
            }
            
            if(!pokemons[carts_middle_id[i] - 1].getIsTaken() && !is_god){
                g.drawImage(pokeball,
                        carts_middle_positions[i][0] - (is_middle_cart_bigger[i]?4:0)
                        , carts_middle_positions[i][1] - (is_middle_cart_bigger[i]?4:0), 
                        40 + (is_middle_cart_bigger[i]?8:0), 40 + (is_middle_cart_bigger[i]?8:0), this);
            }
        }
        
        if(is_machine_selected && !is_pc && is_god){
            g.setColor(Color.green);
            g.drawRect(cart_positions[selected_cart2 + 2][0][0] - 3, cart_positions[selected_cart2 + 2][1][0] - 3, 131, 180);
        }
        
            if(go_is_visible){   
                g.drawImage(go_button, go_X, go_Y, go_width, go_height, this);
            }
            
            if(!is_fnish){
                g.drawImage(corner, 340, 407, 130, 31, this);
                g.drawImage(corner, 896, 406, 131, 31, this);
            }
           
            
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if((e.getSource() == cart_going_animation) && (does_cart1_go || does_cart2_go)){
            
            if(cart_positions[selected_cart1 - 1][1][0] < 520 && does_cart1_go && !cart1_step2){
                cart_positions[selected_cart1 - 1][1][0]++;
                repaint();
            }
            
            if(cart_positions[selected_cart1 - 1][1][0] == 520){
                cart1_step1 = true;
            }
            
            if(cart_positions[selected_cart1 - 1][0][0] < 342 && does_cart1_go && cart1_step1){
                cart_positions[selected_cart1 - 1][0][0]++;
                repaint();
            }
            
            
            if(cart_positions[selected_cart2 + 2][1][0] < 520 && does_cart2_go && !cart2_step2){
                cart_positions[selected_cart2 + 2][1][0]++;
                repaint();
            }
            
            if(cart_positions[selected_cart2 + 2][1][0] == 520){
                cart2_step1 = true;
            }
            
            if(cart_positions[selected_cart2 + 2][0][0] > 899 && does_cart2_go && cart2_step1){
                cart_positions[selected_cart2 + 2][0][0]--;
                repaint();
            }
            
            
            
            if(cart_positions[selected_cart2 + 2][0][0] == 899 && 
                cart_positions[selected_cart2 + 2][1][0] == 520){
                cart2_step1 = false;    
                cart2_step2 = true;
                }
            
            if(cart_positions[selected_cart1 - 1][0][0] == 342 && 
                cart_positions[selected_cart1 - 1][1][0] == 520){
                cart1_step1 = false;
                cart1_step2 = true;
                }
            
            
            
            if(cart_positions[selected_cart1 - 1][1][0] > 231 && does_cart1_go && cart1_step2){
                cart_positions[selected_cart1 - 1][1][0]--;
                repaint();
            }
            
            
            if(cart_positions[selected_cart2 + 2][1][0] > 231 && does_cart2_go && cart2_step2){//231
                cart_positions[selected_cart2 + 2][1][0]--;
                repaint();
            }
            
            
            if(cart_positions[selected_cart2 + 2][0][0] == 899 && 
                    cart_positions[selected_cart2 + 2][1][0] == 231 &&
                    cart_positions[selected_cart1 - 1][0][0] == 342 && 
                    cart_positions[selected_cart1 - 1][1][0] == 231){
                go_is_visible = true;
                repaint();
            }
            
            
            if(cart_positions[selected_cart2 + 2][0][0] == 899 && 
                    cart_positions[selected_cart2 + 2][1][0] == 231 && cart2_step2){
                    does_cart2_go = false;
                    cart2_step1 = false;
                    cart2_step2 = false;
                }
            
            if(cart_positions[selected_cart1 - 1][0][0] == 342 && 
                    cart_positions[selected_cart1 - 1][1][0] == 231 && cart1_step2){
                    does_cart1_go = false;
                    cart1_step1 = false;
                    cart1_step2 = false;
                }
            
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if(go_X <= e.getX() && e.getX() <= go_X + go_width && go_Y <= e.getY() && e.getY() <= go_Y + go_height
                && go_is_visible){//go butonuna basıldı mı kontrolü
            
            pokemons[players[0].getCartList(selected_cart1 - 1) - 1].setter_is_open(true);
            pokemons[players[1].getCartList(selected_cart2 - 1) - 1].setter_is_open(true);
            
            is_go_clicked = true;
            go_is_visible = false;
            did_go_bigger = false;
            go_width -= 8;
            go_height -= 4;
            go_X += 4;
            go_Y += 2;
            
            repaint();
            
            
        }
        
        if(is_selecting){
            
            
            if(cart_positions[0][0][0] <= e.getX() && e.getX() <= cart_positions[0][0][0] + 127 && 
                cart_positions[0][1][0] <= e.getY() &&  e.getY() <= cart_positions[0][1][0] + 173){
            is_selecting = false; is_selected = true; is_cart_bigger[0] = false; repaint();
            X = e.getX(); Y = e.getY();
        }
        
        if(cart_positions[1][0][0] <= e.getX() && e.getX() <= cart_positions[1][1][0] + 127 && 
                cart_positions[1][1][0] <= e.getY() &&  e.getY() <= cart_positions[1][1][0] + 173){
            is_selecting = false; is_selected = true;  is_cart_bigger[1] = false; repaint();
            X = e.getX(); Y = e.getY();
        }
        
        if(cart_positions[2][0][0] <= e.getX() && e.getX() <= cart_positions[2][0][0] + 127 && 
                cart_positions[2][1][0] <= e.getY() &&  e.getY() <= cart_positions[2][1][0] + 173 ){
            is_selecting = false; is_selected = true;  is_cart_bigger[2] = false; repaint();
            X = e.getX(); Y = e.getY();
        }
            
            
            
        }
        
        if(is_taking){
            for(int i = 0; i < 4; i++){
                if(!is_god){
                    if(Math.sqrt( Math.pow(carts_middle_positions[i][0] + 20 - e.getX(),2) +
                          Math.pow(carts_middle_positions[i][1] + 20 - e.getY(),2)) <= 20){
                        is_taking = false; is_taked = true; selection_middle_cart = i;
                        is_middle_cart_bigger[i] = false; distructions.setVisible(false);
                    }

                }else{
                    if(carts_middle_positions[i][0] <= e.getX() && e.getX() <= carts_middle_positions[i][0] + 40 && 
                    carts_middle_positions[i][1] <= e.getY() &&  e.getY() <= carts_middle_positions[i][1] + 60){
                        is_taking = false; is_taked = true; selection_middle_cart = i;
                        is_middle_cart_bigger[i] = false; distructions.setVisible(false);
                    }

                }
            }
        }
        
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
     }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(go_X <= e.getX() && e.getX() <= go_X + go_width && go_Y <= e.getY() && e.getY() <= go_Y + go_height &&
                go_is_visible && !did_go_bigger){
                    did_go_bigger = true;
                    go_width += 8;
                    go_height += 4;
                    go_X -= 4;
                    go_Y -= 2;
                    repaint();
        }
        
        if((go_X > e.getX() || e.getX() > go_X + go_width || go_Y > e.getY() || e.getY() > go_Y + go_height) &&
                go_is_visible && did_go_bigger){
                    did_go_bigger = false;
                    go_width -= 8;
                    go_height -= 4;
                    go_X += 4;
                    go_Y += 2;
                    repaint();
        }
        
        
        if(players[0].getClass() == new HumanPlayer().getClass() && is_selecting){
        
            for(int i = 0; i < 3; i++){
                if(cart_positions[i][0][0] <= e.getX() && e.getX() <= cart_positions[i][0][0] + 127 &&
                        cart_positions[i][1][0] <= e.getY() && e.getY() <= cart_positions[i][1][0] + 175 
                        && !is_cart_bigger[i]){
                    is_cart_bigger[i] = true;
                    repaint();
                }
                
                 if((cart_positions[i][0][0] > e.getX() || e.getX() > cart_positions[i][0][0] + 127 ||
                        cart_positions[i][1][0] > e.getY() || e.getY() > cart_positions[i][1][0] + 175) 
                        && is_cart_bigger[i]){
                    is_cart_bigger[i] = false;
                    repaint();
                }
                
                
            }
        
        }
        
         if(players[0].getClass() == new HumanPlayer().getClass() && is_taking){
        
             for(int i = 0; i < 4; i++){
                if(!is_god){
                    if(Math.sqrt( Math.pow(carts_middle_positions[i][0] + 20 - e.getX(),2) +
                          Math.pow(carts_middle_positions[i][1] + 20 - e.getY(),2)) <= 20 && !is_middle_cart_bigger[i]){
                        is_middle_cart_bigger[i] = true;
                        repaint();
                    }

                }else{
                    if(carts_middle_positions[i][0] <= e.getX() && e.getX() <= carts_middle_positions[i][0] + 40 && 
                    carts_middle_positions[i][1] <= e.getY() &&  e.getY() <= carts_middle_positions[i][1] + 60 && !is_middle_cart_bigger[i]){
                        is_middle_cart_bigger[i] = true;
                        repaint();
                    }

                }
                
                if(!is_god){
                    if(Math.sqrt( Math.pow(carts_middle_positions[i][0] + 20 - e.getX(),2) +
                          Math.pow(carts_middle_positions[i][1] + 20 - e.getY(),2)) > 20 && is_middle_cart_bigger[i]){
                        is_middle_cart_bigger[i] = false;
                        repaint();
                    }

                }else{
                    if((carts_middle_positions[i][0] > e.getX() || e.getX() > carts_middle_positions[i][0] + 40 || 
                    carts_middle_positions[i][1] > e.getY() ||  e.getY() > carts_middle_positions[i][1] + 60) && is_middle_cart_bigger[i]){
                        is_middle_cart_bigger[i] = false;
                        repaint();
                    }

                }
                
                
            }
        
        }
        
        
    }
    
}
