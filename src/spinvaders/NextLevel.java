package spinvaders;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class NextLevel {

	private Main main;
	
	/**
	 * Palyatoltes (NextLevel) konstruktora
	 * @param main - fo jatekter
	 */
	public NextLevel(Main main){
		this.main = main;
		
		nextLevel();
	}
	
	String imgUrl="resources/invader.jpg";
	String imgUrl2="resources/invader2.jpg";
	String imgUrl3="resources/invader3.jpg";
	
	//ellensegek feltoltese jatekterre
	private void nextLevel(){

		//3 eletes ellensegek 
		for (int i=0; i<10 ;i++){ //10 tamado hajo 1 sorban
			for (int o=0; o<1; o++){ //1 sornyi tamado hajo
				SpriteObject enemy=new SpriteObject(50+i*50, 150+o*50, 25, 30, 30, 20, 3, "Invaders", Color.TRANSPARENT); //ellensegek elhelyezese
					Image img=new Image(imgUrl3);
					enemy.setFill(new ImagePattern(img,0,0,30,24,false)); //tamadok kepeinek behivasa
				main.root.getChildren().add(enemy); //hozzadas root-hoz
					}
				}
		
		//2 eletes ellensegek
		for (int i=0; i<10 ;i++){ //10 tamado hajo 1 sorban
			for (int o=0; o<2; o++){ //2 sornyi tamado hajo
				SpriteObject enemy=new SpriteObject(50+i*50, 200+o*50, 25, 30, 30, 20, 2, "Invaders", Color.TRANSPARENT); //ellensegek elhelyezese
					Image img=new Image(imgUrl2);
					enemy.setFill(new ImagePattern(img,0,0,30,24,false)); //tamadok kepeinek behivasa
				main.root.getChildren().add(enemy); //hozzadas root-hoz
			}
		}
		
		//1 eletes ellensegek
		for (int i=0; i<10 ;i++){ //10 tamado hajo 1 sorban
			for (int o=0; o<2; o++){ //2 sornyi tamado hajo
				SpriteObject enemy=new SpriteObject(50+i*50, 300+o*50, 25, 30, 30, 20, 1, "Invaders", Color.TRANSPARENT); //ellensegek elhelyezese
					Image img=new Image(imgUrl);
					enemy.setFill(new ImagePattern(img,0,0,30,24,false)); //tamadok kepeinek behivasa
				main.root.getChildren().add(enemy); //hozzadas root-hoz
			}
		}
	}
}
