package application;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import static javafx.application.Application.launch;

import java.util.ArrayList;

import javafx.scene.Node;
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
  private BorderPane root = new BorderPane();
  private ImageView i1;
  private Question currentQuestion;
  
  private Quiz(Main sceneMain) {
    /**
    if(count == 0) { 
    this.questions = new ArrayList<Question>();
    Question testQuestion = new Question("What is the answer to the question?"); 
    testQuestion.correctAns.add("A");
    testQuestion.allAns.add("A");
    testQuestion.allAns.add("B");
    testQuestion.allAns.add("C");
    testQuestion.allAns.add("D");
    this.questions.add(testQuestion);
    this.sceneMain = sceneMain;
    }
    **/
    count++;
    this.currentQuestion = this.questions.get(count-1);
  }
  
  public Quiz(Main sceneMain, ArrayList<Question> questions, int max, ObservableList<String> topics) {
    this.questions = questions;
    this.sceneMain = sceneMain;
    this.currentQuestion = this.questions.get(count-1);
    this.count = 1;
    this.correct = 0;
    this.max = max;
  }
  
  
  
  private void setToggle() {
    Question testQuestion = this.questions.get(count-1);
    this.toggles = new ArrayList<RadioButton>();
    this.correctToggles = new ArrayList<RadioButton>();
    for(String c : testQuestion.getAllAns()) {
      RadioButton button = new RadioButton(c);
      this.toggles.add(button);
      if(testQuestion.getCorrectAns().contains(c)) {
        this.correctToggles.add(button);
      }
    }
  }
  
   private void createImage(Image imageFile) {
  //create image 
    Image placeholder = imageFile;
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
    this.i1 = i1;
  }
   
   private void builtTop(Stage primaryStage) {
     root.setTop(this.i1);
     root.setAlignment(this.i1, Pos.CENTER);
     root.setMargin(this.i1, new Insets(50));
   }
   
   private void buildBottom(Stage primaryStage) {
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
     VBox layout2 = new VBox(20);
     for(RadioButton r3: this.toggles) {
       layout2.getChildren().add(r3);
     }
     layout2.getChildren().add(button);
     root.setBottom(layout2);
     root.setMargin(layout2, new Insets(0,0,0,432));
   }
   
   private void buildCenter(Stage primaryStage) {
   //label for question text
     Label labelQuestion = new Label(this.currentQuestion.getQuestion());
     VBox layout= new VBox(5);
     layout.getChildren().add(labelQuestion);
     
     layout.setAlignment(Pos.CENTER);
     
     
     //put question in center, image on top
     root.setCenter(layout);
   }

@Override
public void start(Stage primaryStage) {
  primaryStage.setTitle("Test Question "+count);
  
  this.setToggle();
  this.createImage(null);
  this.builtTop(primaryStage);
  this.buildBottom(primaryStage);
  this.buildCenter(primaryStage);
  
  
  Scene scene1= new Scene(root, 1400, 864);
  scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  primaryStage.setScene(scene1);
        
  primaryStage.show();
  }

  

    
}
