package application;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneAddQuestion extends Application {
	Main mainClass;
	Scene scene1;

	// needs to take in parameter for topic list
	public SceneAddQuestion(Main main) {
		mainClass = main;
	}
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		
		primaryStage.setTitle("Add Question");
		
		Label text = new Label("Text");
		Label topic = new Label("Topic");
		Label image = new Label("Image");
		Label choice = new Label("Options");
		Label correct = new Label("Correct");
		
		RadioButton choice1, choice2, choice3, choice4, choice5;
		choice1 = new RadioButton("A");
		choice2 = new RadioButton("B");
		choice3 = new RadioButton("C");
		choice4 = new RadioButton("D");
		choice5 = new RadioButton("E");
	
		
		TextField textField = new TextField();
		TextField imageFile = new TextField();
		TextField choiceOne, choiceTwo, choiceThree, choiceFour, choiceFive;
		choiceOne = new TextField();
		choiceTwo = new TextField();
		choiceThree = new TextField();
		choiceFour = new TextField();
		choiceFive = new TextField();
		choiceOne.setPrefWidth(800);
		
		textField.setPromptText("Enter Question Here");
		imageFile.setPromptText("Enter Image File Here");
		choiceOne.setPromptText("Option One");
		choiceTwo.setPromptText("Option Two");
		choiceThree.setPromptText("Option Three");
		choiceFour.setPromptText("Option Four");
		choiceFive.setPromptText("Option Five");
		
		Button add = new Button("Add");
		Button back = new Button("Back");
		
		add.setDisable(true);

		choice1.setOnAction(e -> add.setDisable(false) );
		choice2.setOnAction(e -> add.setDisable(false) );
		choice3.setOnAction(e -> add.setDisable(false) );
		choice4.setOnAction(e -> add.setDisable(false) );
		choice5.setOnAction(e -> add.setDisable(false) );
		
		ComboBox topics = new ComboBox();
		

		GridPane grid = new GridPane();
		grid.add(text, 0, 0, 1, 1);
		grid.add(topic, 0, 1, 1, 1);
		grid.add(image, 0, 2, 1, 1);
		grid.add(textField, 1, 0, 1, 1);
		grid.add(topics, 1, 1, 1, 1);
		grid.add(imageFile, 1, 2, 1, 1);
		
		GridPane grid2 = new GridPane();

		grid2.add(choice, 0, 1, 1, 1);
		grid2.add(choiceOne, 0, 2, 1, 1);
		grid2.add(choiceTwo, 0, 3, 1, 1);
		grid2.add(choiceThree, 0, 4, 1, 1);
		grid2.add(choiceFour, 0, 5, 1, 1);
		grid2.add(choiceFive, 0,  6, 1, 1);

		grid2.add(correct, 1, 1, 1, 1);
		grid2.add(choice1, 1, 2, 1, 1);
		grid2.add(choice2, 1, 3, 1, 1);
		grid2.add(choice3, 1, 4, 1, 1);
		grid2.add(choice4, 1, 5, 1, 1);
		grid2.add(choice5, 1, 6, 1, 1);
		
		HBox bottomButtons = new HBox(2);
		bottomButtons.getChildren().addAll(back, add);
		bottomButtons.setAlignment(Pos.CENTER);
		bottomButtons.setSpacing(200);
		grid.setVgap(30);
		grid.setHgap(200);
		grid.setAlignment(Pos.CENTER);
		grid2.setVgap(30);
		grid2.setHgap(200);
		grid2.setAlignment(Pos.CENTER);
		root.setCenter(grid2);
		root.setTop(grid);
		root.setBottom(bottomButtons);
		
		
		
		add.setOnAction(e -> {
			if (!choice1.isSelected() && !choice2.isSelected() && !choice3.isSelected() && 
					!choice4.isSelected() && !choice5.isSelected())
				this.subStart(primaryStage);
		});
		back.setOnAction(e ->  mainClass.subStart(primaryStage));
		
		
		scene1= new Scene(root, 1400, 864);
		primaryStage.setScene(scene1);
		        
		scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.show();
		
		
		
		
		
		
		
		
		
	}
	
	  public void subStart(Stage primaryStage) {
		    primaryStage.setScene(scene1);
		    primaryStage.show();

		  }
	
	
}
