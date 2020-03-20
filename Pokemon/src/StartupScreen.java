import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StartupScreen extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
   
    BufferedImage wallpaper, wallpaperCropped, pokeball, little_pokeball, title, signature, pc_or_human, freeman;
    
    private boolean oyuncu1_makine_mi = false, isThatVisible = true, is_god = false; //visible için değişkene ne gerek var ahmak
    private boolean pokeball_did_bigger = false;
    private boolean morgan_did_bigger = false;
    
    private int pokeball_X = 700, pokeball_Y = 315, pokeball_radius = 25, crop_val = 150;
    private int morgan_X = 705, morgan_Y = 385, morgan_weight = 40, morgan_height = 60;
    private int title_x = 595, title_y = 90, title_height = 92, title_weight = 250;
    private int signature_x = 675, signature_y = 190, signature_height = 28, signature_weight = 100;
    private int little_pokeball_x = 695;
    
    private Timer time1 = new Timer(42,this);
    private Timer select_animation_timer = new Timer(5,this);
    //private Timer pulse_time = new Timer(2000,this);
    //private Timer pulsing_timer = new Timer(40,this);
    
    private boolean is_to_right = true;
    private boolean is_to_upper = true;
    private boolean is_to_signature_upper = true;
    
    public StartupScreen(){
        super();
        
        try {
            wallpaper = ImageIO.read(new File("wallpaper.png"));
            pokeball = ImageIO.read(new File("pokeball.png"));
            little_pokeball = ImageIO.read(new File("pokeball.png"));
            title = ImageIO.read(new File("title.png"));
            signature = ImageIO.read(new File("signature.png"));
            pc_or_human = ImageIO.read(new File("pcorhuman.png"));
            freeman = ImageIO.read(new File("freeman.png"));
        } catch (IOException ex) {
            System.err.println("Yok ki!"); 
        }
        
        this.setLayout(null);
        
        
        
        addMouseListener(this);
        addMouseMotionListener(this);
        time1.start();
        //pulse_time.start();
        //pulsing_timer.start();
        
    }
    
    public boolean getVisible(){
        return this.isThatVisible;
    }
    
    public boolean getOyuncu1_makine_mi(){
        return this.oyuncu1_makine_mi;
    }
    
    public void setOyuncu1_makine_mi(boolean oyuncu1_makine_mi){
        this.oyuncu1_makine_mi = oyuncu1_makine_mi;
    }
    
    public boolean isGod(){
        return is_god;
    }
    
    public void paint(Graphics g){
        super.paint(g);
        wallpaperCropped = wallpaper.getSubimage(crop_val, 150, 1000, 600);
        g.drawImage(wallpaperCropped, 0, 0, 1000, 600, this);
        g.drawImage(pokeball, pokeball_X, pokeball_Y, 2*pokeball_radius, 2*pokeball_radius, this);
        g.drawImage(title, title_x, title_y, title_weight, title_height, this);
        g.drawImage(signature, signature_x, signature_y, signature_weight, signature_height, this);
        g.drawImage(pc_or_human, 635, 230, 198, 79, this);
        g.drawImage(little_pokeball, little_pokeball_x, 260, 25, 25, this);
        g.drawImage(freeman, morgan_X, morgan_Y, morgan_weight, morgan_height, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == time1){
            if(crop_val == 920 || crop_val == 149){
                is_to_right = !is_to_right;
            }
            if(is_to_right == true){
                crop_val += 1;
                repaint();
            }
            if(is_to_right == false){
                crop_val -= 1;
                repaint();
            }
        
        }
        
        if(e.getSource() == select_animation_timer){
            if(oyuncu1_makine_mi == true && little_pokeball_x <= 730){
                little_pokeball_x++;
                if(little_pokeball_x == 730){select_animation_timer.stop();}
            }
            if(oyuncu1_makine_mi == false && 695 <= little_pokeball_x){
                little_pokeball_x--;
                if(little_pokeball_x == 730){select_animation_timer.stop();}
            }
            
        }
        
     
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
      if(Math.sqrt( Math.pow(pokeball_X + pokeball_radius - e.getX(),2) +
              Math.pow(pokeball_Y + pokeball_radius - e.getY(),2)) <= pokeball_radius){
          //Başlangıç için büyük pokeballa tıklanmış mı kontrol eden hede
          
            this.setVisible(false);
            isThatVisible = false;
            
        }
      
      if(690 <= e.getX() && e.getX() <= 757 && 258 <= e.getY() && e.getY() <= 286){
          select_animation_timer.start();
          oyuncu1_makine_mi = !oyuncu1_makine_mi;
      }
      
      
      if(e.getX() > morgan_X && e.getX() < morgan_X + morgan_weight && e.getY() > morgan_Y && e.getY() 
                < morgan_Y + morgan_height){
            this.setVisible(false);
            isThatVisible = false;
            is_god = true;
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
        
        if(Math.sqrt( Math.pow(pokeball_X + pokeball_radius - e.getX(),2) +
              Math.pow(pokeball_Y + pokeball_radius - e.getY(),2)) <= pokeball_radius && !pokeball_did_bigger){
            pokeball_did_bigger = true;
            pokeball_radius += 2;
            pokeball_X -= 2;
            pokeball_Y -= 2;
            repaint();
        }
        
        if(Math.sqrt( Math.pow(pokeball_X + pokeball_radius - e.getX(),2) +
              Math.pow(pokeball_Y + pokeball_radius - e.getY(),2)) > pokeball_radius && pokeball_did_bigger){
            pokeball_did_bigger = false;
            pokeball_radius -= 2;
            pokeball_X += 2;
            pokeball_Y += 2;
            repaint();
        }
        
        if(e.getX() > morgan_X && e.getX() < morgan_X + morgan_weight && e.getY() > morgan_Y && e.getY() 
                < morgan_Y + morgan_height && !morgan_did_bigger){
            
            morgan_did_bigger = true;
            morgan_X -= 2;
            morgan_Y -= 2;
            morgan_weight += 4;
            morgan_height += 4;
            repaint();
        }
        
        if((!(e.getX() > morgan_X) || !(e.getX() < morgan_X + morgan_weight) || !(e.getY() > morgan_Y) 
                || !(e.getY() < morgan_Y + morgan_height)) && morgan_did_bigger){
            morgan_did_bigger = false;
            morgan_X += 2;
            morgan_Y += 2;
            morgan_weight -= 4;
            morgan_height -= 4;
            repaint();
        }
        
    }
}
