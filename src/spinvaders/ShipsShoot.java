package spinvaders;

import javafx.scene.paint.Color;

public class ShipsShoot {

	private Main main;
	
	/**
	 * Lovesek (Jatekos es Tamadok) konstruktora
	 * @param main - Foprogram Main parameter atadasa
	 */
	public ShipsShoot(Main main){
		this.main = main;
	}
	
	/**
	 * lovesek letrehozasa	
	 * @param who - ki lo: jatekos vagy tamado --> dontes type (tipus) alapjan
	 */
	void Shoot(SpriteObject who){ //hajo Rectangel hasznalataval
		SpriteObject b = new SpriteObject(	(int) who.getTranslateX() +10, 
											(int) who.getTranslateY(), 10, 4, 1, 1, 1, who.type + "bolt", Color.RED); //lovedek letrehozasa
		main.root.getChildren().add(b); //hozzadas root-hoz
	}
}
