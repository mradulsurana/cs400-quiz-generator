package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import static javafx.application.Application.launch;

import java.util.ArrayList;

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


public class Quiz extends Application {
  private static ArrayList<Question> questions;
  private ArrayList<RadioButton> toggles;
  private ArrayList<RadioButton> correctToggles;
  private static int count;
  private static int correct;
  private static int max;
  private static Main sceneMain;
  
  public Quiz(Main sceneMain) {
    if(count == 0) { 
    this.questions = new ArrayList<Question>();
    Question testQuestion = new Question("What is the answer to the question?"); 
    testQuestion.correctAns.add("A");
    testQuestion.incorrectAns.add("B");
    testQuestion.incorrectAns.add("C");
    testQuestion.incorrectAns.add("D");
    this.questions.add(testQuestion);
    this.sceneMain = sceneMain;
    }
    count++;
  }
  
  public void clear() {
    this.count = 1;
    this.correct = 0;
    
  }
  
  public void setNumQuestions(int num) {
    this.max = num;
  }
  private void setToggle() {
    Question testQuestion = this.questions.get(0);
    this.toggles = new ArrayList<RadioButton>();
    this.correctToggles = new ArrayList<RadioButton>();
    for(String c : testQuestion.correctAns) {
      RadioButton button = new RadioButton(c);
      this.toggles.add(button);
      this.correctToggles.add(button);
    }
    for(String i : testQuestion.incorrectAns) {
      this.toggles.add(new RadioButton(i));
    }
  }
  
   private ImageView createImage(String imageFile) {
  //create image 
    Image placeholder = new Image("application/placeholder.png");
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
    return i1;
  }

@Override
public void start(Stage primaryStage) {
  this.setToggle();
  
  BorderPane root = new BorderPane();
  //show question number
  primaryStage.setTitle("Test Question "+count);

  //label for question text
  Label labelQuestion = new Label("What is the answer to question "+count+"?");

  Question testQuestion = this.questions.get(0);

  Button button= new Button("Next");
  button.setDisable(true);

  ToggleGroup question1= new ToggleGroup();

  for(RadioButton r: this.toggles) {
    r.setToggleGroup(question1);
    r.setOnAction(e -> button.setDisable(false));
  }

  button.setOnAction(e -> 
  {
    for(RadioButton r2: this.toggles) {
      if(this.correctToggles.contains(r2)) {
        if(r2.isSelected() ) {
          correct++;
          break;
        }
      }
    }
    if(count < max) {
      Quiz next = new Quiz(sceneMain);
      next.start(primaryStage);
    }
    else {
      Results result = new Results(sceneMain,count,correct);
      result.start(primaryStage);
    };
  });

  ImageView i1 = this.createImage("test");
  //put image in center pane
  root.setCenter(i1);

  //holds question and choices and button
  VBox layout= new VBox(5);
  layout.getChildren().add(labelQuestion);
  for(RadioButton r3: this.toggles) {
    layout.getChildren().add(r3);
  }
  layout.getChildren().add(button);
  //put question in center, image on top
  root.setCenter(layout);
  root.setTop(i1);

  //align the nodes in root
  root.setAlignment(i1, Pos.CENTER);
  root.setMargin(layout, new Insets(50));
  root.setMargin(i1, new Insets(50));
  Scene scene1= new Scene(root, 1400, 864);
  scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  primaryStage.setScene(scene1);
        
  primaryStage.show();
  }

  

    
}
