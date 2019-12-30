package spinvaders;

import javafx.scene.control.Label;

public class PlayerGameOver {

	private Main main;
	
	/**
	 * GameOver konstruktora
	 * @param main - Foprogram Main parameter atadasa
	 */
	public PlayerGameOver(Main main){
		this.main = main;
	}
	
	//Jatekos megsemmisult - game over
	public void PlayerDead (boolean GameRun){
		if (GameRun == false){
			main.timer.stop();
			Label gmtxt=new Label("GAME OVER");
				main.PlayerDied.play();
				gmtxt.setLayoutX(200);
				gmtxt.setLayoutY(400);
				gmtxt.setId("gotxt"); //css id
				main.PlayMusic.stop();
			main.field.getChildren().add(gmtxt);
		};
	}
}
