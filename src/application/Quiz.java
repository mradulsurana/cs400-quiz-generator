package application;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import static javafx.application.Application.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

/**
 * This class starts displays a Quiz of a certain
 * amount of Questions and filtered by topic and 
 * allows the user to answer them. It keeps track
 * of how many questions were answered correctly
 * @author allen
 *
 */
public class Quiz extends Application {
  //the arraylist of Questions that will be asked to the user
  private static ArrayList<Question> questions;
  //the buttons that the user can select to answer
  private ArrayList<RadioButton> toggles;
  //the buttons that are the correct answer
  private ArrayList<RadioButton> correctToggles;
  //how many questions have been asked
  private static int count;
  //how many questions have been answered correctly
  private static int correct;
  //how many questions there are in total
  private static int max;
  //the main page that linked to this page
  private static Main sceneMain;
  //the object that will be in the scene
  private BorderPane root = new BorderPane();
  //image for the question
  private ImageView i1;
  //the current question
  private Question currentQuestion;
  
  /**
   * Private constructor that will only be called within this class
   * to move on to the next question
   * @param sceneMain
   */
  private Quiz(Main sceneMain) {
    //iterate count since this is a new question in the quiz
    count++;
    //set the current question
    this.currentQuestion = this.questions.get(count-1);
  }
  
  /**
   * Public constructor called by the main page to start a new quiz
   * @param sceneMain is the scene that linked to the quiz
   * @param questions is a list of all questions that can be asked
   * @param max is the amount of questions for the quiz
   * @param topics is the list of topics for the quiz
   */
  public Quiz(Main sceneMain, ArrayList<Question> questions, int max, ObservableList<String> topics) {
    //initially set the list of questions to be all the questions
    this.questions = new ArrayList<Question>();
    
    for(Question q: questions) {
      if(topics.contains(q.getTopic())) {
        this.questions.add(q);
      }
    }
    //randomize the order of questions
    Collections.shuffle(this.questions);
    
    if(this.questions.size() < max) {
      this.max = this.questions.size();
      
    } else {
      this.questions = (ArrayList<Question>)this.questions.subList(0,max);
      this.max = max;
    }

    
    this.count = 1;
    this.correct = 0;
    //set the current question
    this.currentQuestion = this.questions.get(count-1);
    //set current question count and num correct
    
    for(String s: topics) {
      System.out.println(s);
    }
    
    for(Question q: this.questions) {
      System.out.println(q.getQuestion());
      
    }
    
                     
  }
  
  /**
   * This method creates all the toggle buttons for the multiple choice
   * question
   */
  private void setToggle() {
    //initialize the toggle list
    this.toggles = new ArrayList<RadioButton>();
    this.correctToggles = new ArrayList<RadioButton>();
    
    //iterate through the answers and create a button
    //for each answer, and save the correct answers
    for(String c : this.currentQuestion.getAllAns()) {
      RadioButton button = new RadioButton(c);
      this.toggles.add(button);
      if(this.currentQuestion.getCorrectAns().contains(c)) {
        this.correctToggles.add(button);
      }
    }
  }
  
  /**
   * This method formats the question image
   * @param imageFile is the image to be formated
   */
   private void createImage(ImageView imageFile) {
  //create image 
  
    //put image into ImageView object
    ImageView i1 = imageFile;
    //set size of image
    i1.setFitWidth(400);
    //maintain ratio of image
    i1.setPreserveRatio(true);
    //keep image quality
    i1.setSmooth(true);
    i1.setCache(true);
    this.i1 = i1;
  }
   
   /**
    * This method sets the top pane of the root that will be
    * put in scene
    * @param primaryStage is the Stage that quiz will show
    */
   private void buildTop(Stage primaryStage) {
     root.setTop(this.i1);
     root.setAlignment(this.i1, Pos.CENTER);
     root.setMargin(this.i1, new Insets(50));
   }
   
   /**
    * This method sets the bottom pane of the root that will 
    * be put in scene
    * @param primaryStage is stage of the Quiz
    */
   private void buildBottom(Stage primaryStage) {
     //button for going to next question
     Button button= new Button("Next");
     button.setDisable(true);
     CustomPopup popup = new CustomPopup();
     boolean isCorrect = false;

     //toggle group so that only one button can be selected
     ToggleGroup question1= new ToggleGroup();

     // add buttons to the toggle group and allow next button
     // to be pressed once an answer is selected
     for(RadioButton r: this.toggles) {
       r.setToggleGroup(question1);
       r.setOnAction(e -> button.setDisable(false));
     }

     //defines what happens when 
    
     button.setOnAction(e -> 
     {
       for(RadioButton r2: this.toggles) {
           if(r2.isSelected() ) {
          if (this.correctToggles.contains(r2)) {
             correct++;
             popup.setLabel("Answer was correct!");
             popup.show(primaryStage);
             break;
           } else {
             popup.setLabel("Answer was incorrect!");
             popup.show(primaryStage);
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
  this.createImage(this.currentQuestion.getImage());
  
  this.buildTop(primaryStage);
  this.buildBottom(primaryStage);
  this.buildCenter(primaryStage);
  
  
  Scene scene1= new Scene(root, 1400, 864);
  scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  primaryStage.setScene(scene1);
        
  primaryStage.show();
  }

  

    
}
