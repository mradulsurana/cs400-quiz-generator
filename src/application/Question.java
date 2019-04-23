package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Question extends Application {
  static int count;
  public Question() {
    count++;
  }
@Override
public void start(Stage primaryStage) {
  BorderPane root = new BorderPane();
//show question number
primaryStage.setTitle("Test Question "+count);

//label for question text
Label labelQuestion = new Label("What is the answer to question "+count+"?");


Button button= new Button("Next");

RadioButton choice1, choice2, choice3, choice4;
choice1 = new RadioButton("A");
choice2 = new RadioButton("B");
choice3 = new RadioButton("C");
choice4 = new RadioButton("D");

ToggleGroup question1= new ToggleGroup();

choice1.setToggleGroup(question1);
choice2.setToggleGroup(question1);
choice3.setToggleGroup(question1);
choice4.setToggleGroup(question1);

button.setDisable(true);

choice1.setOnAction(e -> button.setDisable(false) );
choice2.setOnAction(e -> button.setDisable(false) );
choice3.setOnAction(e -> button.setDisable(false) );
choice4.setOnAction(e -> button.setDisable(false) );

button.setOnAction(e -> 
{
  if(count <5) {
MCTest next = new MCTest();
next.start(primaryStage);
  }
});

//create image 
Image placeholder = new Image("placeholder.png");
//put image into ImageView object
ImageView i1 = new ImageView();
i1.setImage(placeholder);
//set size of image
i1.setFitWidth(200);
//maintain ratio of image
i1.setPreserveRatio(true);
//keep image quality
i1.setSmooth(true);
i1.setCache(true);
//put image in center pane
root.setCenter(i1);

//holds question and choices and button
VBox layout= new VBox(5);
layout.getChildren().addAll(labelQuestion, choice1, choice2, choice3, choice4, button);

//put question in center, image on top
root.setCenter(layout);
root.setTop(i1);

//align the nodes in root
root.setAlignment(i1, Pos.CENTER);
root.setMargin(layout, new Insets(50));
root.setMargin(i1, new Insets(50));
Scene scene1= new Scene(root, 700, 700);
primaryStage.setScene(scene1);
        
primaryStage.show();
}

  

    
}
