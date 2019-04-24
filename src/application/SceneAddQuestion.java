package application;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneAddQuestion extends Application {

	
	public SceneAddQuestion() {
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		
		primaryStage.setTitle("Add Question");
		
		Label text = new Label("Text");
		Label topic = new Label("Topic");
		Label image = new Label("Image");
		Label choice = new Label("Choice");
		Label correct = new Label("Correct");
		
		RadioButton choice1, choice2, choice3, choice4;
		choice1 = new RadioButton("A");
		choice2 = new RadioButton("B");
		choice3 = new RadioButton("C");
		choice4 = new RadioButton("D");
	
		ToggleGroup question = new ToggleGroup();

		choice1.setToggleGroup(question);
		choice2.setToggleGroup(question);
		choice3.setToggleGroup(question);
		choice4.setToggleGroup(question);
		
		TextField textField = new TextField("Enter Text Here");
		TextField imageFile = new TextField("Enter Image File Name Here");
		TextField choiceOne, choiceTwo, choiceThree, choiceFour;
		choiceOne = new TextField("Choice One");
		choiceTwo = new TextField("Choice Two");
		choiceThree = new TextField("Choice Three");
		choiceFour = new TextField("Choice Four");
		
		Button add = new Button("Add");
		
		add.setDisable(true);

		choice1.setOnAction(e -> add.setDisable(false) );
		choice2.setOnAction(e -> add.setDisable(false) );
		choice3.setOnAction(e -> add.setDisable(false) );
		choice4.setOnAction(e -> add.setDisable(false) );
		
		ComboBox topics = new ComboBox();
		
		VBox textInputs = new VBox(4);
		textInputs.getChildren().addAll(text, topic, image, choice);
		GridPane grid = new GridPane();
		grid.add(text, 0, 0, 1, 1);
		grid.add(topic, 0, 1, 1, 1);
		grid.add(image, 0, 2, 1, 1);
		grid.add(choice, 0, 3, 1, 1);
		grid.add(new Label("  "), 0, 4, 1, 1);
		grid.add(choiceOne, 0, 5, 1, 1);
		grid.add(choiceTwo, 0, 6, 1, 1);
		grid.add(choiceThree, 0, 7, 1, 1);
		grid.add(choiceFour, 0, 8, 1, 1);
		
		grid.add(textField, 1, 0, 1, 1);
		grid.add(topics, 1, 1, 1, 1);
		grid.add(imageFile, 1, 2, 1, 1);
		grid.add(correct, 1, 3, 1, 1);
		grid.add(new Label("  "), 1, 4, 1, 1);
		grid.add(choice1, 1, 5, 1, 1);
		grid.add(choice2, 1, 6, 1, 1);
		grid.add(choice3, 1, 7, 1, 1);
		grid.add(choice4, 1, 8, 1, 1);
		
		
		root.setCenter(grid);
		root.setBottom(add);
		
		Scene scene1= new Scene(root, 700, 700);
		primaryStage.setScene(scene1);
		        
		primaryStage.show();
		
		
		
		
		
		
		
	}
	
	
}
