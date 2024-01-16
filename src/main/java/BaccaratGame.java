import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;


public class BaccaratGame extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	private ArrayList<Card> playerHand = new ArrayList<Card>();
	
	private ArrayList<Card> bankerHand = new ArrayList <Card>();
	
	private static BaccaratDealer theDealer = new BaccaratDealer();
	private BaccaratGameLogic gameLogic = new BaccaratGameLogic();
	
	private double currentBet = 0;
	private double totalWinnings = 0;
	
	private double wallet = 1000; // INITIAL STARTING BALANCE
	private String whoToBetOn = "No_one";
	
	public void setWhoBet(String person) {
		whoToBetOn = person;
	}
	
	public double evaluateWinnings() {
		
		if (gameLogic.whoWon(playerHand, bankerHand) == "Player" && whoToBetOn == "Player") {
//			System.out.println("playerOutcome: " + currentBet * 2);
			return currentBet * 2;
		}
		
		else if (gameLogic.whoWon(playerHand, bankerHand) == "Banker" && whoToBetOn == "Banker") {
			
//			System.out.println("bankerOutcome: " + ((currentBet * 2) - (currentBet * .05)));
			return (currentBet * 2) - (currentBet * .05);
		}
		
		else if (gameLogic.whoWon(playerHand, bankerHand) == "Draw" && whoToBetOn == "Draw") {
			return currentBet * 2;
		}
		
		return 0;
	}
	
	public void updateWinnings() {
		
		this.totalWinnings += evaluateWinnings();
		
		
		
	}
	
    private TranslateTransition translateTransition;
    private FadeTransition fadeTransition;
    private Text hint = new Text("");
	
    private void setupTransitions() {
        translateTransition = new TranslateTransition(Duration.seconds(5), hint);
        translateTransition.setByY(-400 );

        fadeTransition = new FadeTransition(Duration.seconds(4), hint);
        fadeTransition.setToValue(0);
        hint.setVisible(true);
        
        translateTransition.setOnFinished(event -> {
//          hint.setLayoutX(0);
//          hint.setLayoutY(0);
      	
      	hint.setVisible(false);
          hint.setTranslateY(0); // Reset the translation
        });

    }
	
    
    private void startTransitions() {
        translateTransition.stop(); // Stop previous transition, if running
        hint.setTranslateY(0);
        fadeTransition.stop();      // Stop previous transition, if running
        setupTransitions();         // Reset the transitions
        translateTransition.play();
        fadeTransition.play();
    }
    
    private ScaleTransition scaleTransition;
    private ScaleTransition descaleTransition;
    ImageView theBagImage = new ImageView();

    
    private void scaleSetUp() {
    	
    scaleTransition = new ScaleTransition(Duration.millis(100), theBagImage);
    scaleTransition.setFromX(1.0); // Initial scale factor in the X direction
    scaleTransition.setFromY(1.0); // Initial scale factor in the Y direction
    scaleTransition.setToX(1.1);   // Target scale factor in the X direction
    scaleTransition.setToY(1.1);   // Target scale factor in the Y direction
//  
    descaleTransition = new ScaleTransition(Duration.millis(100), theBagImage);
    descaleTransition.setFromX(1.1); // Initial scale factor in the X direction
    descaleTransition.setFromY(1.1); // Initial scale factor in the Y direction
    descaleTransition.setToX(1.0);   // Target scale factor in the X direction
    descaleTransition.setToY(1.0);   // Target scale factor in the Y direction
    }
    
    private void startBagScale() {
    	
    	scaleTransition.play();
    	
    	scaleTransition.setOnFinished(scaleEvent -> {
    		
    		descaleTransition.play();
    	});
    }
    
    private void cardHoverScale(ImageView image) {
        ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), image);
        scaleTransition1.setFromX(1.0); // Initial scale factor in the X direction
        scaleTransition1.setFromY(1.0); // Initial scale factor in the Y direction
        scaleTransition1.setToX(1.1);   // Target scale factor in the X direction
        scaleTransition1.setToY(1.1);   // Target scale factor in the Y direction
        
        scaleTransition1.play();
        
        
    }
    
    private void cardHoverDescale(ImageView image) {
        ScaleTransition descaleTransition1 = new ScaleTransition(Duration.millis(100), image);
        descaleTransition1.setFromX(1.1); // Initial scale factor in the X direction
        descaleTransition1.setFromY(1.1); // Initial scale factor in the Y direction
        descaleTransition1.setToX(1.0);   // Target scale factor in the X direction
        descaleTransition1.setToY(1.0);   // Target scale factor in the Y direction
        
        descaleTransition1.play();
        
       
    }
    
    private boolean isNumeric(String s) {
        // Check if the string can be parsed as a number
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Baccarat");
		
	    
	    Button playBtn = new Button("PLAY");
	    
		
	    BorderPane root = new BorderPane();
	    
	    ImageView logoNode = new ImageView(new Image(getClass().getResource("BaccaratLogo.png").toExternalForm()));

	    
	    VBox logoVBox = new VBox();

        MenuBar menuBar = new MenuBar();

        menuBar.setStyle("-fx-background-color: #325937;"); // You can set your desired color code

        Menu options = new Menu("Options");
        
        options.setStyle("-fx-text-fill: #336699;");

        MenuItem restartOption = new MenuItem("Fresh Start");
        MenuItem exitOption = new MenuItem("Exit");
	    
        menuBar.setUseSystemMenuBar(true);
        
        options.getItems().addAll(restartOption, exitOption);
        menuBar.getMenus().add(options);
        
        
 
        
	    logoVBox.getChildren().add(logoNode);
	    logoVBox.setAlignment(Pos.BOTTOM_CENTER);
	    
        logoNode.setFitWidth(500); // Set the width
        logoNode.setFitHeight(500); // Set the height
        
        playBtn.setStyle("-fx-font-size: 50px; -fx-font-family: 'Georgia'; -fx-background-color: #22803d;  -fx-text-fill: white;");
      
        // make it easier to see if on playButton
        playBtn.setOnMouseEntered(e -> playBtn.setStyle("-fx-font-size: 50px; -fx-font-family: 'Georgia'; -fx-background-color: #145928;  -fx-text-fill: white;"));
        playBtn.setOnMouseExited(e -> playBtn.setStyle("-fx-font-size: 50px; -fx-font-family: 'Georgia'; -fx-background-color: #22803d;  -fx-text-fill: white;"));

        playBtn.setPrefWidth(300); 
        playBtn.setPrefHeight(100); 
	    
        

        
  


        
	    root.setBottom(logoVBox);
	    root.setCenter(playBtn);

	    // StartScreen Gradient/backgroundfill
	    LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#3dc462")), new Stop(.7, Color.web("#1d6630")));	    
	    
        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);

//	     BorderPane root = new BorderPane();
        root.setBackground(background);
        

        
	    
	    Scene startScene = new Scene(root, 1500,700); 
	    
	    BorderPane root2 = new BorderPane();
	    
	    
	    // GameScreen Gradient
	    LinearGradient gameGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
               new Stop(0, Color.web("#2c9e4b")), new Stop(.7, Color.web("#175727")));	    
	    
        BackgroundFill gameBGFill = new BackgroundFill(gameGradient, CornerRadii.EMPTY, Insets.EMPTY);

        Background gameBG = new Background(gameBGFill);

        
        root2.setBackground(gameBG);
        
	    Scene gameScene = new Scene(root2, startScene.getWidth(), startScene.getHeight());
	    
	    
	    FadeTransition fadeOut = new FadeTransition(Duration.millis(500), startScene.getRoot());
	    fadeOut.setFromValue(1.0);
	    fadeOut.setToValue(0.0);
//        primaryStage.setFullScreen(true);

//	    root.getChildren().add(menuBar);


		// SetOnAction simply changes scene from startScene to gameScene with fade effect.
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
        	
            public void handle(ActionEvent event) {
            	
//            	System.out.println("Player pressed play!");
      

                

 
                
                fadeOut.setOnFinished(e -> {
                	
                	startScene.getRoot().setVisible(false);
                    primaryStage.setScene(gameScene); // Set gameScene as the current scene
                    
                    
//                    primaryStage.setFullScreenExitHint(null);
//                    primaryStage.setFullScreen(true);
                    gameScene.getRoot().setOpacity(0.0); // Start with gameScene fully transparent
                    startScene.getRoot().setVisible(false);
                    gameScene.getRoot().setVisible(true);
                    
                    
                    FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), gameScene.getRoot());
                    fadeIn.setFromValue(0.0);
                    fadeIn.setToValue(1.0);
                    
                    fadeIn.play();
                    
                    fadeIn.setOnFinished(fadeInEvent ->{
                    	
                      	startTransitions();
                    });
                });
                
                fadeOut.play();
            }
            
            
        	
        });
        
 
        //Banker cards to go on right
        
        BorderPane bottom = new BorderPane();
        

        Insets padding = new Insets(10);

        
        // Wallet Functionality
        
        HBox balanceHolder = new HBox();
        
        Image theBag = new Image(getClass().getResource("money_icon.png").toExternalForm());
        
        
        theBagImage = new ImageView(theBag);
        
        theBagImage.setScaleX(.8);
        theBagImage.setScaleY(.8);
        
        Text balanceAmount = new Text("$" + String.format("%.2f", wallet));
        
        balanceAmount.setStyle("-fx-font: bold 20px 'Arial'; -fx-fill: white;");
        balanceAmount.setTextAlignment(TextAlignment.CENTER);
        balanceHolder.setPadding(padding);
        balanceHolder.setAlignment(Pos.BOTTOM_CENTER);
        
        
        VBox container = new VBox(theBagImage, balanceAmount);
        container.setAlignment(Pos.CENTER); // Center both the image and text horizontally

        balanceHolder.getChildren().add(container);
//        balanceHolder.getChildren().addAll(theBagImage, balanceAmount);
        
        bottom.setLeft(balanceHolder);
        
        
        
        // Input Bet
        
        VBox inputContainer = new VBox();
        TextField betAmount = new TextField();
        
        
        betAmount.setPrefWidth(100);
        betAmount.setPrefHeight(25);
        betAmount.setStyle("-fx-background-color: #58825b; -fx-text-fill: white;");
        betAmount.setPromptText("Enter Bet $");
    	
        betAmount.setEditable(true);
        betAmount.clear();
        // ONLY NUMBERS AS BET
        betAmount.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!isNumeric(event.getCharacter())) {
                event.consume(); // Consume the event to prevent non-numeric input
            }
        });
        
        gameScene.setOnMouseClicked(e -> {
            if (!betAmount.getBoundsInParent().contains(e.getX(), e.getY())) {
                betAmount.setEditable(false);
                betAmount.getParent().requestFocus(); // Remove focus
            }
        });
        
        betAmount.setOnMouseClicked(eventMouseClick -> {
        	betAmount.setEditable(true);
            betAmount.requestFocus(); // Set focus to the TextField

        });
        inputContainer.getChildren().add(betAmount);
        
        betAmount.setAlignment(Pos.BOTTOM_CENTER);
        inputContainer.setAlignment(Pos.BOTTOM_RIGHT);
        inputContainer.setPadding(padding);
        
        bottom.setRight(inputContainer);
        
        
        
        
        // deal button in center

        
        Button dealBtn = new Button("DEAL");
        Button continueBtn = new Button("CONTINUE");

        dealBtn.setAlignment(Pos.CENTER);
        dealBtn.setStyle("-fx-font-size: 25px; -fx-font-family: 'Arial'; -fx-background-color: #22803d;  -fx-text-fill: white;");
        dealBtn.setOnMouseEntered(e -> dealBtn.setStyle("-fx-font-size: 25px; -fx-font-family: 'Arial'; -fx-background-color: #67b57e;  -fx-text-fill: white;"));
        dealBtn.setOnMouseExited(e -> dealBtn.setStyle("-fx-font-size: 25px; -fx-font-family: 'Arial'; -fx-background-color: #22803d;  -fx-text-fill: white;"));
        
        
        continueBtn.setAlignment(Pos.CENTER);
        continueBtn.setStyle("-fx-font-size: 15px; -fx-font-family: 'Arial'; -fx-background-color: #22803d;  -fx-text-fill: white;");
        continueBtn.setOnMouseEntered(e -> continueBtn.setStyle("-fx-font-size: 15px; -fx-font-family: 'Arial'; -fx-background-color: #67b57e;  -fx-text-fill: white;"));
        continueBtn.setOnMouseExited(e -> continueBtn.setStyle("-fx-font-size: 15px; -fx-font-family: 'Arial'; -fx-background-color: #22803d;  -fx-text-fill: white;"));
        
        

        

        
        dealBtn.setPrefSize(150, 50);
        continueBtn.setPrefSize(150, 50);

        
        StackPane btnStack = new StackPane();
        
        continueBtn.setVisible(false);
        
        btnStack.getChildren().addAll(dealBtn, continueBtn);
        btnStack.setAlignment(Pos.CENTER);
        
        bottom.setCenter(btnStack);
        
        bottom.setPadding(padding);
        // playerCards to go on left 
        
        VBox leftSide = new VBox();
        
        VBox rightSide = new VBox();
        
        
        VBox betAndWinnings = new VBox();
        
        root2.setPadding(new javafx.geometry.Insets(0));
        
        betAndWinnings.getChildren().add(menuBar);
        betAndWinnings.setSpacing(5);
        
        Text winningsTotal = new Text("Total Winnings: $0.00");
        
        Text currentBetText = new Text("Current Bet: $0.00");
        
        winningsTotal.setStyle("-fx-font: bold 25px 'Arial'; -fx-fill: white;");
        currentBetText.setStyle("-fx-font: bold 25px 'Arial'; -fx-fill: white;");
        betAndWinnings.setAlignment(Pos.CENTER);
        
        winningsTotal.setTextAlignment(TextAlignment.CENTER);
        currentBetText.setTextAlignment(TextAlignment.CENTER);
        
        betAndWinnings.getChildren().addAll(winningsTotal, currentBetText);
        
        
        Text playerName = new Text("PLAYER");
        Text playerScore = new Text("0");
        
        
        
        Text bankerName = new Text("BANKER");
        Text bankerScore = new Text("0");
        
        hint.setText("Click on \n 'PLAYER' or 'BANKER' \nto choose \nwho to bet on.");
        
        hint.setTextAlignment(TextAlignment.CENTER);
        //REMOVE HINT after 10 SECONDS:
        
//        Pane centerPane = new Pane();

        
//        centerPane.getChildren().add(hint);
        root2.setCenter(hint);

        
        hint.setStyle("-fx-font: bold 20px 'Arial'; -fx-fill: white;");

        hint.setVisible(true);

        
        setupTransitions();

  
        
//        transition.play();
        
        playerScore.setStyle("-fx-font: bold 60px 'Arial'; -fx-fill: white;");
        bankerScore.setStyle("-fx-font: bold 60px 'Arial'; -fx-fill: white;");
        bankerScore.setTextAlignment(TextAlignment.CENTER);
        playerScore.setTextAlignment(TextAlignment.CENTER);
        
        
        
        bankerName.setTextAlignment(TextAlignment.RIGHT);

        bankerName.setStyle("-fx-font: bold 60px 'Arial'; -fx-fill: white;");

        playerName.setTextAlignment(TextAlignment.LEFT);
        
        playerName.setStyle("-fx-font: bold 60px 'Arial'; -fx-fill: white;");
        
        
        leftSide.getChildren().add(playerName);
        rightSide.getChildren().add(bankerName);
        
        BorderPane top = new BorderPane();
        
     
        
        top.setPadding(new javafx.geometry.Insets(0));
        
        top.setTop(betAndWinnings);
//        BorderPane.setMargin(playerScore,  new javafx.geometry.Insets(0, 20, 0, 0));
//        BorderPane.setMargin(bankerScore,  new javafx.geometry.Insets(0, 0, 0, 20));
        
        VBox playerScoreBox = new VBox();
        VBox bankerScoreBox = new VBox();
        playerScoreBox.getChildren().add(playerScore);
        playerScoreBox.setAlignment(Pos.BASELINE_RIGHT);
        playerScoreBox.setPadding(new javafx.geometry.Insets(5));
        bankerScoreBox.getChildren().add(bankerScore);
        bankerScoreBox.setPadding(new javafx.geometry.Insets(5));
        bankerScoreBox.setAlignment(Pos.BASELINE_LEFT);
        top.setLeft(playerScoreBox);
        top.setRight(bankerScoreBox);
        

        HBox leftCards = new HBox();
        HBox leftCardsUnder = new HBox();

        HBox rightCards = new HBox();
        HBox rightCardsUnder = new HBox();
        
        
        // choosing who to bet on functionality:
        
        AtomicBoolean playerSelected = new AtomicBoolean(false);
        AtomicBoolean bankerSelected = new AtomicBoolean(false);

        
        
        playerName.setOnMouseClicked(event -> {
        	
        	if (playerSelected.get() == false) {
//        	System.out.println("playerName clicked");
        	playerName.setStroke(Color.BLACK);
        	playerName.setStrokeWidth(2);
        	playerSelected.set(true);
        	
            if (playerSelected.get() && bankerSelected.get()) {
            	
            	whoToBetOn = "Draw";
            	hint.setText("You bet on a draw.");
            	hint.setVisible(true);
            	hint.setOpacity(1);
            	startTransitions();

            }
            else {
            	if (playerSelected.get()) {
            		
            		whoToBetOn = "Player";
                	hint.setText("You bet on yourself.");
                	hint.setVisible(true);
                	hint.setOpacity(1);
                	startTransitions();
            		
            	} 
            	else {
            		whoToBetOn = "Banker";
                	hint.setText("You bet on the banker.");
                	hint.setVisible(true);
                	hint.setOpacity(1);
                	startTransitions();
                	
            	}
            	
            }
            
        	}
        	else {
            	playerName.setStrokeWidth(0);
            	playerSelected.set(false);
                
            	if (bankerSelected.get() == false) {
                	whoToBetOn = "No_one";
                	
            	}
            	if (bankerSelected.get()) {
            		whoToBetOn = "Banker";
            	}
        	}
        });
        
        
        bankerName.setOnMouseClicked(event -> {
        	
        	if (bankerSelected.get() == false) {
//        	System.out.println("playerName clicked");
        		bankerName.setStroke(Color.BLACK);
        		bankerName.setStrokeWidth(2);
        		bankerSelected.set(true);
        		
                if (playerSelected.get() && bankerSelected.get()) {
                	
                	whoToBetOn = "Draw";
                	hint.setText("You bet on a draw.");
                	hint.setVisible(true);
                	hint.setOpacity(1);
                	startTransitions();
 
                	
                }
                
                
                else {
                	if (playerSelected.get()) {
                		
                		whoToBetOn = "Player";
                    	hint.setText("You bet on yourself.");
                    	hint.setVisible(true);
                    	hint.setOpacity(1);
                    	startTransitions();


                	} 
                	else {
                		whoToBetOn = "Banker";
                    	hint.setText("You bet on the banker.");
                    	hint.setVisible(true);
                    	hint.setOpacity(1);
                    	startTransitions();

                	}
                	
                }
                
        	}
        	else {
        		bankerName.setStrokeWidth(0);
            	bankerSelected.set(false);
                
            	if (playerSelected.get() == false) {
                	whoToBetOn = "No_one";
            	}
            	if (playerSelected.get()) {
            		whoToBetOn = "Player";
            	}
                
        	}
        });
        

            
        	
        
        
        //leftcards positioning
        
        leftCardsUnder.setSpacing(10);
        leftCardsUnder.setPadding(padding);
        
        leftCards.setSpacing(10);
        leftCards.setPadding(padding);
        
        rightCardsUnder.setSpacing(10);
        rightCardsUnder.setPadding(padding);
        
        rightCards.setSpacing(10);
        rightCards.setPadding(padding);
        
        leftSide.setAlignment(Pos.CENTER);
        rightSide.setAlignment(Pos.CENTER);
        

        
        
        
        // The backdrop for the cards, to know where they are supposed to be placed.
        Image darkGreenImage = new Image(getClass().getResource("/back-card-imprint.png").toExternalForm());
        
        Image cardBackImage = new Image(getClass().getResource("/PNG-cards-1.3/back-card.png").toExternalForm());

        // PLAYER CARDS (Back of Card)
        ImageView card1 = new ImageView(cardBackImage); 
        ImageView card2 = new ImageView(cardBackImage);
        ImageView card3 = new ImageView(cardBackImage);
        
        
        // BANKER CARDS (Back of card)
        ImageView card4 = new ImageView(cardBackImage); 
        ImageView card5 = new ImageView(cardBackImage);
        ImageView card6 = new ImageView(cardBackImage);
        
        
        // Create Layered Effect on cards:
        
        StackPane leftStack = new StackPane();

        StackPane leftStackBG = new StackPane();
        
        
        StackPane cardsLayeredLeft = new StackPane();
        
        StackPane rightStack = new StackPane();
        
        StackPane rightStackBG = new StackPane();
        
        StackPane cardsLayeredRight = new StackPane();

        
        ImageView card1BG = new ImageView(darkGreenImage); 
        ImageView card2BG = new ImageView(darkGreenImage);
        ImageView card3BG = new ImageView(darkGreenImage);
        
        ImageView card4BG = new ImageView(darkGreenImage); 
        ImageView card5BG = new ImageView(darkGreenImage);
        ImageView card6BG = new ImageView(darkGreenImage);
        
        // FADE TRANSITIONS -----------------------------------------------------
        FadeTransition card1FadeIn = new FadeTransition(Duration.millis(500), card1);
        card1FadeIn.setFromValue(0.0);
        card1FadeIn.setToValue(1.0);
        
        
        FadeTransition card2FadeIn = new FadeTransition(Duration.millis(500), card2);
        card2FadeIn.setFromValue(0.0);
        card2FadeIn.setToValue(1.0);
        card2FadeIn.setNode(card2);
        
        FadeTransition card6FadeIn = new FadeTransition(Duration.millis(500), card6);
        card6FadeIn.setFromValue(0.0);
        card6FadeIn.setToValue(1.0);
        card6FadeIn.setNode(card6);
        
        FadeTransition card5FadeIn = new FadeTransition(Duration.millis(500), card5);
        card5FadeIn.setFromValue(0.0);
        card5FadeIn.setToValue(1.0);
        card5FadeIn.setNode(card5);
        
        
        // THIRD CARD FADES IN WHEN YOU PRESS DRAW BUTTON. (Draw button shows only if you have ability to draw.)
        FadeTransition card3FadeIn = new FadeTransition(Duration.millis(500), card3);
        card3FadeIn.setFromValue(0.0);
        card3FadeIn.setToValue(1.0);
        
        FadeTransition card4FadeIn = new FadeTransition(Duration.millis(500), card4);
        card4FadeIn.setFromValue(0.0);
        card4FadeIn.setToValue(1.0);
        
        
        

        
    
        // FADE TRANSITIONS -----------------------------------------------------
        
        
        continueBtn.setOnAction(e -> {
        	continueBtn.setVisible(false);
        	cardsLayeredLeft.getChildren().clear();
        	cardsLayeredRight.getChildren().clear();
        	dealBtn.setVisible(true);
			betAmount.setDisable(false);
        	currentBetText.setText("Current Bet: $0.00");
			bankerScore.setText("0");
			playerScore.setText("0");
			playerName.setMouseTransparent(false); // Disable clickability, bets are final
            bankerName.setMouseTransparent(false); // Disable clickability, bets are final
        	
        });

        

    	
    
    	//FIRST WE GENERATE DECK -------------------
    	theDealer.generateDeck();
		theDealer.shuffleDeck();

    	
    	

    	
		// ADD MENUBAR
		   		
	       restartOption.setOnAction(event ->{
	        	cardsLayeredLeft.getChildren().clear();
	        	cardsLayeredRight.getChildren().clear();
	        	
	        	// DISABLE CONTINUE, YOU CAN BET OFF RIP
	        	continueBtn.setVisible(false);
	        	dealBtn.setVisible(true);
	        	// RESET THE MONEY AMOUNTS
	        	totalWinnings = 0;
	        	wallet = 1000;
	        	currentBet = 0;
	        	whoToBetOn = "No_one";
	        	// RESET TEXTS
	        	winningsTotal.setText("Total Winnings: $" + String.format("%.2f", totalWinnings));
	        	currentBetText.setText("Current Bet: $0.00");

				bankerScore.setText("0");
				playerScore.setText("0");

	        	balanceAmount.setText("$" + String.format("%.2f", wallet));

	        	betAmount.clear();

	        	
	        	// ENABLE CLICKABLITY AGAIN IF ON CONTINUE
				playerName.setMouseTransparent(false); 
	            bankerName.setMouseTransparent(false); 
	        	betAmount.setEditable(true);
				betAmount.setDisable(false);

				

				// DE-SELECT WHO YOU BET ON
	        	playerName.setStrokeWidth(0);
	        	bankerName.setStrokeWidth(0);
	        	playerSelected.set(false);
	        	bankerSelected.set(false);
	        	
	        	
	        });
	        
	        exitOption.setOnAction(event -> {
	            primaryStage.close(); // Close the application
	        });

    	
	    // DEAL BUTTON FUNCTIONALITY:
        dealBtn.setOnAction(e -> {
        	
        	// IF USER DOESNT CHOOSE BET
            if (whoToBetOn.equals("No_one")) {
            	hint.setText("Please select who to bet on first.");
            	hint.setVisible(true);
            	hint.setOpacity(1);
            	startTransitions();
            	
                return; // Exit the event handler
            }
            
            String betText = betAmount.getText().trim();
            
            // IF USER DOESNT INPUT BET AMOUNT
            if (betText.isEmpty()) {
            	
                hint.setText("Please enter a bet amount.");
                hint.setVisible(true);
                hint.setOpacity(1);
                startTransitions();
                return;
            }
            
            currentBet = Double.parseDouble(betAmount.getText());
            

            //IF BET AMOUNT IS NEGATIVE
            
            if (currentBet <= 0) {
            	
            	hint.setText("You cannot play for free..\n You must input >= $1");
            	hint.setVisible(true);
            	hint.setOpacity(1);
            	startTransitions();
            	return;
            }
            
            // IF BET AMOUNT IS TOO MUCH
            if (wallet < currentBet) {
            	
            	hint.setText("You cannot afford to play.\n I'm sorry...");
            	hint.setVisible(true);
            	hint.setOpacity(1);
            	startTransitions();
            	return;
            }
        	
        		dealBtn.setVisible(false);
//				theDealer.generateDeck();	
//				System.out.println(theDealer.deckSize());
				if (theDealer.deckSize() <= 6) {
					theDealer.generateDeck();
					theDealer.shuffleDeck();
				}
			
			betAmount.setDisable(true);
			wallet = wallet - currentBet;
	         
	        balanceAmount.setText("$" + String.format("%.2f", wallet));
	        

        	currentBetText.setText("Current Bet: $" + String.format("%.2f", currentBet));

        	
			hint.setVisible(false);

        	// if deck is empty, regenerate Deck

			playerName.setMouseTransparent(true); // Disable clickability, bets are final
            bankerName.setMouseTransparent(true); // Disable clickability, bets are final
				
			bankerScore.setText("0");
			playerScore.setText("0");
        	cardsLayeredLeft.getChildren().clear();
        	cardsLayeredRight.getChildren().clear();
        	
            

//        	dealBtn.setVisible(false);
        	card1.setVisible(true);
        	card2.setVisible(true);
        	card5.setVisible(true);
        	card6.setVisible(true);
        	

        	
//        	theDealer.shuffleDeck(); // UNCOMMENT THIS WHEN YOU ARE DONE PRACTICING
        	playerHand.clear();
        	bankerHand.clear();
        	
        	playerHand = theDealer.dealHand();
        	bankerHand = theDealer.dealHand();
        	
        	String card1ImagePath = "";
        	String card2ImagePath = "";
        	String card3ImagePath = "";
        	
        	
        	String card4ImagePath = "";
        	String card5ImagePath = "";
        	String card6ImagePath = "";
        	
    		Card playerDraw = null;
    		Card bankerDraw = null;
    		
            AtomicBoolean didPlayerDraw = new AtomicBoolean(false);
            AtomicBoolean didBankerDraw = new AtomicBoolean(false);



        	if (playerHand.get(0).getFace().equals("nil")) {
        		card1ImagePath = "/PNG-cards-1.3/" + playerHand.get(0).getValue() + "_of_" + playerHand.get(0).getSuit() + ".png";

        	}else {
        		card1ImagePath = "/PNG-cards-1.3/" + playerHand.get(0).getValue()+ playerHand.get(0).getFace().charAt(0) + "_of_" + playerHand.get(0).getSuit() + ".png";
        		
        	}
        	
        	
        	if (playerHand.get(1).getFace().equals("nil")) {
        		card2ImagePath = "/PNG-cards-1.3/" + playerHand.get(1).getValue() + "_of_" + playerHand.get(1).getSuit() + ".png";
        		

        	}else {
        		card2ImagePath = "/PNG-cards-1.3/" + playerHand.get(1).getValue()+ playerHand.get(1).getFace().charAt(0) + "_of_" + playerHand.get(0).getSuit() + ".png";
        		
        	}
        	if (gameLogic.evaluatePlayerDraw(playerHand)) {
        		playerDraw = theDealer.drawOne();
        		playerHand.add(playerDraw);
    			didPlayerDraw.set(true);
        		if (playerDraw.getFace() == "nil") {
        			card3ImagePath = "/PNG-cards-1.3/" + playerDraw.getValue() + "_of_" + playerDraw.getSuit() + ".png";
        		}else {
        			card3ImagePath = "/PNG-cards-1.3/" + playerDraw.getValue()+ playerDraw.getFace().charAt(0) + "_of_" + playerDraw.getSuit() + ".png";
        		
        		}
        	}
//        	
     
        	if (gameLogic.evaluateBankerDraw(bankerHand, playerDraw)) {
        		bankerDraw = theDealer.drawOne();
        		bankerHand.add(bankerDraw);
        		didBankerDraw.set(true);
       
          	if (bankerDraw.getFace() == "nil") {
        		card4ImagePath = "/PNG-cards-1.3/" + bankerDraw.getValue() + "_of_" + bankerDraw.getSuit() + ".png";

        	}else {
        		card4ImagePath = "/PNG-cards-1.3/" + bankerDraw.getValue()+ bankerDraw.getFace().charAt(0) + "_of_" + bankerDraw.getSuit() + ".png";
        		
        	}
               
        	  	}
        
      
        	
        	if (bankerHand.get(1).getFace() == "nil") {
        		card5ImagePath = "/PNG-cards-1.3/" + bankerHand.get(1).getValue() + "_of_" + bankerHand.get(1).getSuit() + ".png";

        	}else {
        		card5ImagePath = "/PNG-cards-1.3/" + bankerHand.get(1).getValue()+bankerHand.get(1).getFace().charAt(0) + "_of_" + bankerHand.get(1).getSuit() + ".png";
        		
        	}

          	if (bankerHand.get(0).getFace() == "nil") {
        		card6ImagePath = "/PNG-cards-1.3/" + bankerHand.get(0).getValue() + "_of_" + bankerHand.get(0).getSuit() + ".png";

        	}else {
        		card6ImagePath = "/PNG-cards-1.3/" + bankerHand.get(0).getValue()+ bankerHand.get(0).getFace().charAt(0) + "_of_" + bankerHand.get(0).getSuit() + ".png";
        		
        	}
   
        	
        	

        	
//        	System.out.println(card1ImagePath);
//        	System.out.println(card2ImagePath);
//        	System.out.println(card3ImagePath);
//        	
//        	System.out.println(card4ImagePath);
//        	System.out.println(card5ImagePath);
//        	System.out.println(card6ImagePath);
        	
        	HBox playerCards = new HBox();
            HBox bankerCards = new HBox();
            
            
            playerCards.getChildren().clear();
            bankerCards.getChildren().clear();
        	
            
        	Image card1ImageP = new Image(getClass().getResource(card1ImagePath).toExternalForm());
        	Image card2ImageP = new Image(getClass().getResource(card2ImagePath).toExternalForm());
        	Image card3ImageP = new Image(getClass().getResource(card3ImagePath).toExternalForm());
        		
        	
            ImageView card1Image = new ImageView(card1ImageP); 
            ImageView card2Image = new ImageView(card2ImageP);
            ImageView card3Image = new ImageView(card3ImageP);
            
//            card1Image.setVisible(false);
//            card2Image.setVisible(false);
            card3Image.setVisible(false); // temporarily make it false until we draw, then we change image

            
    
        	
        	Image card4ImageP= new Image(getClass().getResource(card4ImagePath).toExternalForm());
        	Image card5ImageP = new Image(getClass().getResource(card5ImagePath).toExternalForm());
        	Image card6ImageP= new Image(getClass().getResource(card6ImagePath).toExternalForm());
        	
        	
        	
        	
        	ImageView card4Image = new ImageView(card4ImageP); // temporarily make it false until we draw
            ImageView card5Image = new ImageView(card5ImageP); 
            ImageView card6Image = new ImageView(card6ImageP);
            
            
            
          	
//        	card4Image.setVisible(false);
//        	card5Image.setVisible(false);
//        	card6Image.setVisible(false);
            
            card1Image.setFitWidth(150); 
            card1Image.setFitHeight(225);
    
            card2Image.setFitWidth(150); 
            card2Image.setFitHeight(225);
            
            
            card3Image.setFitWidth(150); 
            card3Image.setFitHeight(225);
            
            
            card4Image.setFitWidth(150); 
            card4Image.setFitHeight(225);
            
            
            card5Image.setFitWidth(150); 
            card5Image.setFitHeight(225);
            
            card6Image.setFitWidth(150); 
            card6Image.setFitHeight(225);
        	
            playerCards.getChildren().addAll(card1Image, card2Image, card3Image);
            
            bankerCards.getChildren().addAll(card4Image, card5Image, card6Image);
            
            playerCards.setSpacing(10);
            playerCards.setPadding(padding);
            
            bankerCards.setSpacing(10);
            bankerCards.setPadding(padding);
            
            cardsLayeredLeft.getChildren().add(playerCards);
            cardsLayeredRight.getChildren().add(bankerCards);
            
            card1Image.setVisible(true);	
            card2Image.setVisible(true);	
            card3Image.setVisible(true);
            card4Image.setVisible(true);
            card5Image.setVisible(true);	
            card6Image.setVisible(true);	
            
            card1Image.setOpacity(0.0);
            card2Image.setOpacity(0.0);
            card3Image.setOpacity(0.0);
            card4Image.setOpacity(0.0);
            card5Image.setOpacity(0.0);
            card6Image.setOpacity(0.0);
        	
        	// get the second card
        	
        	// concatonate the value + suit to a string to resemble the image.
        	
        	
        	

            
 
            
            	
                PauseTransition pause1 = new PauseTransition(Duration.seconds(0.2));
                PauseTransition pause2 = new PauseTransition(Duration.seconds(0.2));
                PauseTransition pause3 = new PauseTransition(Duration.seconds(0.2));

                
                
                SequentialTransition sequentialTransition2 = new SequentialTransition();

                FadeTransition card1FadeIn_Image = new FadeTransition(Duration.millis(500), card1Image);
                card1FadeIn_Image.setFromValue(0.0);
                card1FadeIn_Image.setToValue(1.0);
                card1FadeIn_Image.setNode(card1Image);

                
                FadeTransition card2FadeIn_Image = new FadeTransition(Duration.millis(500), card2Image);
                card2FadeIn_Image.setFromValue(0.0);
                card2FadeIn_Image.setToValue(1.0);
                card2FadeIn_Image.setNode(card2Image);
                
                FadeTransition card5FadeIn_Image = new FadeTransition(Duration.millis(500), card5Image);
                card5FadeIn_Image.setFromValue(0.0);
                card5FadeIn_Image.setToValue(1.0);
                card5FadeIn_Image.setNode(card5Image);
                
                FadeTransition card6FadeIn_Image = new FadeTransition(Duration.millis(500), card6Image);
                card6FadeIn_Image.setFromValue(0.0);
                card6FadeIn_Image.setToValue(1.0);
                card6FadeIn_Image.setNode(card6Image);
                

            	

            	 // THIRD CARD FADES IN WHEN YOU PRESS DRAW BUTTON. (Draw button shows only if you have ability to draw.)
                FadeTransition card3FadeIn_Image = new FadeTransition(Duration.millis(500), card3Image);
                card3FadeIn_Image.setFromValue(0.0);
                card3FadeIn_Image.setToValue(1.0);
                
                FadeTransition card4FadeIn_Image = new FadeTransition(Duration.millis(500), card4Image);
                card4FadeIn_Image.setFromValue(0.0);
                card4FadeIn_Image.setToValue(1.0);
                
                // Add the card1FadeIn animation with a 0.5-second duration
                card1FadeIn_Image.setDuration(Duration.millis(500));
                sequentialTransition2.getChildren().add(card1FadeIn_Image);

                // Add a pause before card2FadeIn
                sequentialTransition2.getChildren().add(pause1);
                
                // Add the card2FadeIn animation with a 0.5-second duration
                card2FadeIn_Image.setDuration(Duration.millis(500));
                sequentialTransition2.getChildren().add(card2FadeIn_Image);
                
                // Add a pause before card6FadeIn
                sequentialTransition2.getChildren().add(pause2);
                
                // Add the card6FadeIn animation with a 0.5-second duration
                card6FadeIn_Image.setDuration(Duration.millis(500));
                sequentialTransition2.getChildren().add(card6FadeIn_Image);
                
                // Add a pause before the last card
                sequentialTransition2.getChildren().add(pause3);

                // Add the last cardFadeIn animation
                card5FadeIn_Image.setDuration(Duration.millis(500));
                sequentialTransition2.getChildren().add(card5FadeIn_Image);

                // Play the sequential animations
                sequentialTransition2.play();
                
                
                // HOVER OVER CARDS
                

                  

                  
                  
                  
                  card1Image.setOnMouseEntered(event -> {
          
             
                          cardHoverScale(card1Image);// play the scaling animation when mouse enters
             
                          
                  	});
                  
                  
                  card1Image.setOnMouseExited(event -> {
          
                  		
                  
                      cardHoverDescale(card1Image); // play the scaling animation when mouse exits
                      
                  });
          
                  
                  card2Image.setOnMouseEntered(event -> {
          
                  	   
                      cardHoverScale(card2Image); // play the scaling animation when mouse enters
          
                      
              	});
                  
          
              
                  card2Image.setOnMouseExited(event -> {
          
              		
              
                      cardHoverDescale(card2Image); // play the scaling animation when mouse exits
                  
              });
                  
                  card3Image.setOnMouseEntered(event -> {
                      
                 	   
                      cardHoverScale(card3Image); // play the scaling animation when mouse enters
          
                      
              	});
                  
          
              
                  card3Image.setOnMouseExited(event -> {
          
              		
              
                      cardHoverDescale(card3Image); // play the scaling animation when mouse exits
                  
              });
                  
                  card4Image.setOnMouseEntered(event -> {
                      
                	   
                      cardHoverScale(card4Image); // play the scaling animation when mouse enters
          
                      
              	});
                  
          
              
                  card4Image.setOnMouseExited(event -> {
          
              		
              
                      cardHoverDescale(card4Image); // play the scaling animation when mouse exits
                  
              });
                  
                  card5Image.setOnMouseEntered(event -> {
                      
                	   
                      cardHoverScale(card5Image); // play the scaling animation when mouse enters
          
                      
              	});
                  
          
              
                  card5Image.setOnMouseExited(event -> {
          
              		
              
                      cardHoverDescale(card5Image); // play the scaling animation when mouse exits
                  
              });
                  
                  card6Image.setOnMouseEntered(event -> {
                      
                	   
                      cardHoverScale(card6Image); // play the scaling animation when mouse enters
          
                      
              	});
                  
          
              
                  card6Image.setOnMouseExited(event -> {
          
              		
              
                      cardHoverDescale(card6Image); // play the scaling animation when mouse exits
                  
              });
                

                sequentialTransition2.setOnFinished(eventFin -> {
                	

                    
                 
                 PauseTransition pauseDraw = new PauseTransition(Duration.seconds(0.2));

                 SequentialTransition sequentialTransition3 = new SequentialTransition();
                 
//                 System.out.print(didPlayerDraw.get());
                 
           
                	 card3FadeIn_Image.setDuration(Duration.millis(500));
                	 sequentialTransition3.getChildren().add(pauseDraw);
                	 sequentialTransition3.getChildren().add(card3FadeIn_Image);


                          
                	card4FadeIn_Image.setDuration(Duration.millis(500));
                	sequentialTransition3.getChildren().add(card4FadeIn_Image);
            
//                    System.out.println("Before wallet: "  + wallet);

                sequentialTransition3.setOnFinished(event3 -> {
                    playerScore.setText(Integer.toString(gameLogic.handTotal(playerHand)));
                    bankerScore.setText(Integer.toString(gameLogic.handTotal(bankerHand)));
                    continueBtn.setVisible(true);
                    
                    scaleSetUp();
                    
                    
                    wallet += evaluateWinnings();
                    
//                    System.out.println("wallet: "  + wallet);
 
                    if (whoToBetOn == gameLogic.whoWon(playerHand, bankerHand) && gameLogic.whoWon(playerHand, bankerHand) != "Draw") {
                    	hint.setText(gameLogic.whoWon(playerHand, bankerHand) + " has won!\n You won $" + String.format("%.2f", evaluateWinnings()));
                        startBagScale();
                    	balanceAmount.setText("$" + String.format("%.2f", wallet));

                        totalWinnings += evaluateWinnings() - currentBet;

                    }
                    
                    if(whoToBetOn != gameLogic.whoWon(playerHand, bankerHand) && gameLogic.whoWon(playerHand, bankerHand) != "Draw") {
                    	hint.setText(gameLogic.whoWon(playerHand, bankerHand) + " has won!\n You lost $" + String.format("%.2f", (currentBet)));
                    	totalWinnings -= currentBet;
                    	
                    }
                    
                    if (whoToBetOn == gameLogic.whoWon(playerHand, bankerHand) && gameLogic.whoWon(playerHand, bankerHand) == "Draw") {
                    	hint.setText("The game was a draw!\n You won $" + String.format("%.2f", evaluateWinnings()));
                    	balanceAmount.setText("$" + String.format("%.2f", wallet));
                        startBagScale();
                    	totalWinnings += evaluateWinnings() - currentBet;
                    }
                    
                    if ((whoToBetOn != gameLogic.whoWon(playerHand, bankerHand) && gameLogic.whoWon(playerHand, bankerHand) == "Draw")){
                    	hint.setText("The game was a draw!\n You lost $" + String.format("%.2f", currentBet));
                    	totalWinnings -= currentBet;
                    }
//                    System.out.println("total: "  + totalWinnings);
                    if (totalWinnings >= 0) {
                    winningsTotal.setText("Total Winnings: $" + String.format("%.2f", totalWinnings));
                    }
                    else {
                    	winningsTotal.setText("Total Winnings: -$" + String.format("%.2f", (totalWinnings * -1)));
                    	
                    }
                    
                    
                    
                    
                    hint.setVisible(true);
                    hint.setOpacity(1);
                    startTransitions();
                    


                    
                });
                
                sequentialTransition3.play();

                    	
                });
                




            	
            });
            
            
//            playerCards.getChildren().clear();
//            bankerCards.getChildren().clear();
                      
        

        
        // SCALE TRANSITIONS -----------------------------------------------------
        
        

        // SCALE TRANSITIONS -----------------------------------------------------
        
    	// get the first card,
    	
    	// PLAYER CARD CLICK

        // SETTING UP ACTUAL CARD IMAGRES
        
        card1.setOpacity(0.0);
        card1.setVisible(false);
        
        card2.setOpacity(0.0);
        card2.setVisible(false); // ensure the user doesn't accidentally click
        
        card3.setOpacity(0.0);
        card3.setVisible(false);
        
        
        card4.setOpacity(0.0);
        card4.setVisible(false);
        
        card5.setOpacity(0.0);
        card5.setVisible(false);// ensure the user doesn't accidentally click
        
        card6.setOpacity(0.0);
        card6.setVisible(false);
        
        
        card1.setFitWidth(150); 
        card1.setFitHeight(225); 
        
        card2.setFitWidth(150); 
        card2.setFitHeight(225); 
        
        card3.setFitWidth(150); 
        card3.setFitHeight(225); 
        
        
        
        card4.setFitWidth(150); 
        card4.setFitHeight(225); 
        
        card5.setFitWidth(150); 
        card5.setFitHeight(225); 
        
        card6.setFitWidth(150); 
        card6.setFitHeight(225); 

        
        card1BG.setFitWidth(150); 
        card1BG.setFitHeight(225); 
        
        card2BG.setFitWidth(150); 
        card2BG.setFitHeight(225); 
        
        card3BG.setFitWidth(150); 
        card3BG.setFitHeight(225); 
        
        card4BG.setFitWidth(150); 
        card4BG.setFitHeight(225); 
        
        card5BG.setFitWidth(150); 
        card5BG.setFitHeight(225); 
        
        card6BG.setFitWidth(150); 
        card6BG.setFitHeight(225);

        
        
        leftCards.getChildren().addAll(card1, card2, card3);
        leftCardsUnder.getChildren().addAll(card1BG, card2BG, card3BG);
        
        rightCards.getChildren().addAll(card4, card5, card6);
        rightCardsUnder.getChildren().addAll(card4BG, card5BG, card6BG);
        
        cardsLayeredLeft.getChildren().addAll(leftCards);
        
        cardsLayeredRight.getChildren().addAll(rightCards);
//        leftCards.getChildren().add(card1);


        leftStackBG.getChildren().addAll(leftCardsUnder, leftCards);
        
        rightStackBG.getChildren().addAll(rightCardsUnder, rightCards);
        
        leftStack.getChildren().addAll(leftStackBG, cardsLayeredLeft);
        
        rightStack.getChildren().addAll(rightStackBG, cardsLayeredRight);
        
        leftSide.getChildren().add(leftStack);
        
        rightSide.getChildren().add(rightStack);
        
        // SET UP GAME SCREEN
        root2.setTop(top);
        root2.setLeft(leftSide);
        root2.setRight(rightSide);
        root2.setBottom(bottom);        
        
		primaryStage.setScene(startScene); // change this to startScene once done testing.
		primaryStage.show();
		
		
				
		
	}

}
