/*
 * SpaceInvaders - JavaFX
 * 
 * author: Galaktika
 * 
 * Date: 2019.11.01 - 2019.11.05.
 * 
 */

package spinvaders;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SpriteObject extends Rectangle {

	//Hajok (a lovedek -bolt- is ez alapjan epul fel) valtozoinak beallitasa	
	String type;
	boolean hited=false; //talalat jelzo
	int life; //elet jelzo
		
	/**
	 * Sprite (urhajok - jatekos es tamadok) konsturktora
	 * @param x - x koordinata
	 * @param y - y koordinata
	 * @param h - height
	 * @param w - width
	 * @param ah - arc height
	 * @param aw - arc width
	 * @param life - elet
	 * @param type - tipus
	 * @param color - szin (alap esetben kepek helyett szineket kaptak, kepek eseten atlatszora allitva)
	 */
	SpriteObject (int x, int y, int h, int w, int ah, int aw, int life, String type, Color color){
		super (w,h,color); //magassag, szelesseg, szin tultoltese
		this.type=type;
		this.life=life;
		this.setTranslateX(x);
		this.setTranslateY(y);
		this.setArcHeight(ah); //jatekos hajojanak alap beallitas: 10 
		this.setArcWidth(aw); //jatekos hajojanak alap beallitas: 20
	}
			
	//mozgas balra
	void moveLeft()
		{setTranslateX(getTranslateX()-5);}
			
	//mozgas jobbra
	void moveRight()
		{setTranslateX(getTranslateX()+5);}
			
	//loves felfele
	void ShootUp()
		{setTranslateY(getTranslateY()-5);}
					
	//loves lefele / ellenseg mozgatasa lefele
	void ShootDown()
		{setTranslateY(getTranslateY()+5);}
}
