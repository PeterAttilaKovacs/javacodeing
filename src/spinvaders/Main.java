/*
 * SpaceInvaders - JavaFX
 * 
 * author: Galaktika
 * 
 * Date: 2019.11.01 - 2019.11.05.
 * 
 * Music: SpaceBalls movie 1987 - All Rights Reserved for Mel Brooks :)
 * (Found mp3 file on i-net, in a public-domain!)
 * 
 */

package spinvaders;
	
import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Main extends Application {
	
	/**
	 * Ablakelemek letrehozasa, idovaltozo letrehozasa	
	 */
	public Pane root;
	public Group field;
	public double ActionTime;
	public int playerScore;
	public HBox topbox;
	public Label plScore;
	private Label plLife;
	private Label pltxt;

	/**
	 * Hangok es kepek behivasa
	 * 
	 * Direktbe kodolva (beegetve), mert bontott eljarasnal a MediaPlayer nem mukodott megfeleloen...
	 * TODO szetbontani!
	 */
	public String imgUrl 			= "resources/spinlogo.jpg"; //logo kepenek betoltese
	public Image img 				= new Image(imgUrl);
	public String imgUrl2 			= "resources/ship.jpg"; //Jatekos hajo-kepenek betoltese
	public Image img2 				= new Image(imgUrl2);
	public AudioClip ShipShoot 		= new AudioClip(getClass().getResource("/resources/shoot.wav").toString()); //wav fileok lejatszasahoz
	public AudioClip PlayerDied 	= new AudioClip(getClass().getResource("/resources/explosion.wav").toString());
	public AudioClip InvMove 		= new AudioClip(getClass().getResource("/resources/fastinvader1.wav").toString());
	public AudioClip InvDead 		= new AudioClip(getClass().getResource("/resources/invaderkilled.wav").toString());
	public Media Music 				= new Media(getClass().getResource("/resources/spaceballsjm.mp3").toExternalForm()); //mp3, mpeg fileok lejatszasahoz -spaceballs-
	public MediaPlayer PlayMusic 	= new MediaPlayer(Music);
	
	/**
	 * Jatekos letrehozasa	
	 */
	private SpriteObject player;
	private PlayerGameOver PlayerDead;
	
	/**
	 * Tamadok valtozoi
	 */
	private InvaderAllDestroyed IAD;
	
	/**
	 * Kozos valtozok (ShipsShoot)
	 */
	private ShipsShoot shipShoot;
	
	/**
	 * Root-on animation timer mukodtetese 2-es megoldas
	 * eredeti Timeline megoldas nem mukodott megfeleloen	
	 */
	AnimationTimer timer = new AnimationTimer(){
		
		@Override
		public void handle(long arg0) { 
			update();
		}
	};

	/**
	 * Elemek (Ships) gyujtese listaba (List tomb)	
	 * @return List<Ships> - osszegyujtott elemek
	 */
	public List<SpriteObject> Ships() {
		return root.getChildren().stream().map(n->(SpriteObject)n).collect(Collectors.toList()); //lambdakifejezeses gyujtes
	} 
	
	/**
	 * Inicializalas
	 */
	public void init(){
		root = new Pane();
		field = new Group();
		
		ActionTime = 0;
		playerScore = 0;
		
		topbox = new HBox();
		plScore = new Label();
		plLife = new Label();
		pltxt = new Label();
		PlayerDead = new PlayerGameOver(this);
		IAD = new InvaderAllDestroyed(this);
		shipShoot = new ShipsShoot(this);
		
						//x:250, y:650, height: 30, width:30, ArcHeight:10, ArcWidth:20, Life:10 
		player = new SpriteObject(250, 650, 30, 30, 10, 20, 10, "Defender", Color.TRANSPARENT); 
	}
	
	/**
	 * Hajok, lovesek frissitese ---> Jatek logikaja	
	 */
	private void update(){
		ActionTime += 0.04; //idovaltozo novelese x,xx millisecundummal (alap beallitas: 0.016)
		
		Ships().forEach(ship ->{
			switch (ship.type) {
			
			//tamado -cpu-
			case "Invadersbolt": 
				ship.ShootDown(); //ellenseg lovese lefele
				if (ship.getBoundsInParent().intersects(player.getBoundsInParent())){ //loves vizsgalat <-eltalata-e a jatekost?
					ship.hited=true; //lovedek levetele (talalat) a kepernyorol <-- ez fontos!!
					player.life = player.life -1; //jatekos hajoja talalatot kapott, elet csokkentese
					plLife.setText(String.format("Life: %d", player.life));
						
					if (player.life <= 0){ //ha elfogytak az eletek, jatekos-hajo megsemmisitve
						player.hited = true;
						PlayerDead.PlayerDead(false);
					};
				}
			break;
			
			//jatekos -player-
			case "Defenderbolt":	
				ship.ShootUp(); //jatekos lovese felfele
				Ships().stream().filter(e->e.type.equals("Invaders")).forEach(enemy->{ //type-nak annak kell lennie, ami az enemy(ships) neve(type)!
					if (ship.getBoundsInParent().intersects(enemy.getBoundsInParent())){
						enemy.life = enemy.life -1;
						ship.hited=true; //lovedek levetele (talalat) a kepernyorol <-- ez fontos!!
							if (enemy.life <= 0){ //ha elfogytak az eletek, ellenseges hajo megsemmisitve
								enemy.hited = true;
								IAD.InvadersDead();
							}	
					}
				});
			break;
			
			//tamado -cpu- lepesei/lovesei
			case "Invaders":
				if (ActionTime > 2){ //ha idonoveles nagyobb mint 2 masodperc, akkor:
					Ships().stream().filter(e -> e.type.equals("Invaders")).forEach(enemy->{ //jatekos es ellenseges hajo utkozes vizsgalata
						if(enemy.getBoundsInParent().intersects(player.getBoundsInParent())){
							player.hited = true;
							enemy.hited = true; 
							PlayerDead.PlayerDead(false); //utkozes eseten azonnal vege a jateknak
						}
					});
					
					if (Math.random() > 0.9){ //random loves generalasa (alap beallitas: 0.3)
						ShipShoot.play(); //tamado loveshang lejatszasa
						shipShoot.Shoot(ship); //tamado lovese
					}
					InvMove.play();
					ship.ShootDown(); //ellenseges hajok egyutt lefele mozognak
				}
			break;
			}
		});
		
		root.getChildren().removeIf(n ->{ //ha hajo(k) eltalava, akkor torles a jatekterrol
			SpriteObject s=(SpriteObject) n;
			return s.hited;
		});
		
		//idovaltozo visszallitasa 0-ra
		if (ActionTime > 2){
			ActionTime = 0;
		}
	}
	
	/**
	 * Start metodus felulirasa	
	 */
	@Override
	public void start(Stage primaryStage) {

		init();
		
		//jatekos, topbox, root, field beallitasai, ellensegek feltoltese, timer inditasa
		pltxt.setText("Player move ship with ARROW-KEYS, Shoot with SPACE, ENTER start Music, Quit game with ESC.");
			pltxt.setLayoutX(20);
			pltxt.setLayoutY(80);
			pltxt.setId("pltxt"); //css id
		
		player.setFill(new ImagePattern(img2,0,0,30,24,false));	
			plLife.setId("plLife"); //css id
			plLife.setLayoutX(100);
			plLife.setLayoutY(100);
			plLife.setText("Life: 10"); //elet alapbeallitasa mutatasa (ha modositod, itt is ird at!)
			plScore.setId("plScore"); //css id
			plScore.setLayoutX(400);
			plScore.setLayoutY(100);
			plScore.setText("Score: 0"); //pontszam alapbeallitas mutatasa
		
		topbox.setPadding(new Insets(30,60,45,540)); //topbox meretenek beallitasa
			topbox.setId("topbox"); //css id

		root.getChildren().add(player); //jatekos hozzadasa roothoz
			root.setPrefSize(600, 700); //root meretenek beallitasa
			root.setId("root"); //css id
		
		field.getChildren().add(root);
		field.getChildren().add(topbox);
		field.getChildren().add(plScore);
		field.getChildren().add(plLife);
		field.getChildren().add(pltxt);
		field.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); //CSS stiluslap behivasa

		new NextLevel(this); //ellensegek feltoltes fuggvenyenek meghivasa
		
		timer.start(); // AnimationTimer inditasa	

		//scene beallitasa, field hozzadasa		
		Scene play = new Scene(field, 590, 690);
		
		//jatek inditasa		
		try {
					
			//jatekos hajojanak mozgatasa es loves
			play.setOnKeyPressed(new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent event) { //jatekos jobbra mozgas
					if (event.getCode() == KeyCode.LEFT){
						player.moveLeft();
					}

					if (event.getCode() == KeyCode.RIGHT){ // jatekos balra mozgas
						player.moveRight();
					}
					
					if (event.getCode() == KeyCode.SPACE){ // jatekos loves
						ShipShoot.play(); //jatekos loveshang lejatszasa
						shipShoot.Shoot(player);
					}
					
					if (event.getCode() == KeyCode.ESCAPE){ //jatek bezarasa
						primaryStage.close();
					}
					
					if (event.getCode()==KeyCode.ENTER){ //zene inditasa <-- nem all le, elindulas utan
						Status status = PlayMusic.getStatus();
						
						if (status != Status.STOPPED){ //<-- igy nem jo! nem mukodik, elindulas utan nem all le ujabb enterre...
							PlayMusic.setVolume(0.5); //hangero beallitasa 0.0 es 1.0 kozott (alapbeallitas: 0.5)
							PlayMusic.play();	
						}
						
						if (status != Status.PLAYING){
							PlayMusic.stop();
						}
					}
				}
			});
			
			/**
			 * Fojelenet beallitasa es inditasa			
			 */
			primaryStage.setTitle("SpaceInvaders 1.9");
			primaryStage.setResizable(false);
			primaryStage.setScene(play); //fojelenet inditasa
			primaryStage.show(); //fojelenet mutatasa
		} 
		
		catch(Exception e) { //kivetelkezeles kiiratasa konzolra
			e.printStackTrace();
			System.err.println("StackTrace: " + e); //stacktrace kiirasa konzolra
		}
	}

	/**
	 * Foprogram inditas	
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}
}
