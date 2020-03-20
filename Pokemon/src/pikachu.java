
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class pikachu extends pokemon {
	
	private int damage_point;
	
	public void set_damage_point(int damage_point)
	{
		this.damage_point = damage_point;
	}
	
	public int get_damage_point()
	{
		return damage_point;
	}
	
	public pikachu(String pokemon_name,int pokemon_id,String pokemon_kind) 
	{
		super(pokemon_name,pokemon_id,pokemon_kind);
		
		try {
			setter_cart_image(ImageIO.read(new File("Pikachu.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Pikachu resmi yok !");
		}
	}
	
	public pikachu()
	{
		super();

		try {
			setter_cart_image(ImageIO.read(new File("Pikachu.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Pikachu resmi yok !");
		}
	}
	
	@Override
	public int showDamagePoint() {
		return damage_point;
		
	}


}
