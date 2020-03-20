
import java.awt.image.BufferedImage;
import java.io.File;

public abstract class pokemon { 
	
	private File file = new File("default.jpg");
	
	private BufferedImage cart_image;
        
	private boolean is_exist = true;
        
        public boolean getIsExist(){
            return is_exist;
        }
        
        public void setIsExist(boolean is_exist){
            this.is_exist = is_exist;
        }
        
        private int damage_point;
	
	public void set_damage_point(int damage_point)
	{
		this.damage_point = damage_point;
	}
	
	public int get_damage_point()
	{
		return damage_point;
	}
        
        
        
	public void setter_cart_image(BufferedImage cart_image)
	{
		this.cart_image = cart_image;
	}
	
	public BufferedImage getter_cart_image()
	{
		return cart_image;
	}
	
	private String pokemon_name;
	
	public void setter_pokemon_name(String pokemon_name)
	{
		this.pokemon_name = pokemon_name;
	}
	
	public String getter_pokemon_name()
	{
		return pokemon_name;
	}
	
	private String pokemon_kind;
	
	public void setter_pokemon_kind(String pokemon_kind)
	{
		this.pokemon_kind = pokemon_kind;
	}
	
	public String getter_pokemon_kind()
	{
		return pokemon_kind;
	}
	
	private int pokemon_id;
	
	public void setter_pokemon_id(int pokemon_id)
	{
		this.pokemon_id = pokemon_id;
	}
	
	public int getter_pokemon_id()
	{
		return pokemon_id;
	}
	
	private boolean is_used = false;
	
	public void setter_is_used(boolean is_used)
	{
		this.is_used = is_used;
	}
	
	public boolean getter_is_used()
	{
		return is_used;
	}
	
	private boolean is_open = false;
	
	public void setter_is_open(boolean is_open)
	{
		this.is_open = is_open;
	}
	
	public boolean getter_is_open()
	{
		return is_open;
	}
	
	public pokemon(String pokemon_name,int pokemon_id,String pokemon_kind)
	{
		this.pokemon_name = pokemon_name;
		this.pokemon_id = pokemon_id;
		this.pokemon_kind = pokemon_kind;
	}
	
	public pokemon()
	{
		pokemon_name = "Null";
		pokemon_id = -1;
		pokemon_kind = "Null";
	}
        
        
        private boolean is_selected = false;
	
        public boolean getIsSelected(){
            return is_selected;
        }
        public void setIsSelected(boolean is_selected){
            this.is_selected = is_selected;
        }
        
        private boolean is_taken = false;//diğer tarafta ilk altılınınkileri günelle
	
        public boolean getIsTaken(){
            return is_taken;
        }
        public void setIsTaken(boolean is_taken){
            this.is_taken = is_taken;
        }
        
	abstract int showDamagePoint();

	

}
