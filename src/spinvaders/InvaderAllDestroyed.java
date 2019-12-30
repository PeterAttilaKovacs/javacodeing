package spinvaders;

import javafx.scene.control.Label;

public class InvaderAllDestroyed {

	private Main main;
	
	/**
	 * Invaderek megsemmisitve konstrukora
	 * @param main - Foprogram Main parameter atadasa
	 */
	public InvaderAllDestroyed(Main main){
		this.main = main;
	}
	
	//jatek pont valtozo novelese + --> jatek leallitasa, ha nincs tobb tamado hajo	
	public void InvadersDead(){
		main.InvDead.play(); //tamado megsemmisult hang lejatszasa
		main.playerScore += 1; //talalat eseten eredmeny novelese 1-el
		main.plScore.setText(String.format("Score: %d", main.playerScore));
		if (main.playerScore == 50){ //50 tamado hajo van, ezert 50 pont eleresevel a jatekos nyert TODO tombe alakitani
			main.timer.stop();
			System.out.println("All invaders are dead..."); //!!Debug only!!
			Label gmtxt=new Label("ALL INVADERS DEFEATED!");
				gmtxt.setLayoutX(70);
				gmtxt.setLayoutY(400);
				gmtxt.setId("gotxt"); //css id
				main.PlayMusic.stop();
				main.field.getChildren().add(gmtxt);	
			}
		}
}
