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
 * @author Allen, Michael, Jordan, Noah, Mradul
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
    this.i1 = this.currentQuestion.getImage();
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
    
    if(this.questions.size() <= max) {
     
      this.max = this.questions.size();
      
    } else {
      
      for(int i=0;i<this.questions.size()-max;i++) {
        this.questions.remove(0);
      }
      //this.questions = (ArrayList<Question>)this.questions.subList(0,max);
      this.max = max;
    }

    
    this.count = 1;
    this.correct = 0;
    //set the current question
    this.currentQuestion = this.questions.get(count-1);
    //set current question count and num correct
    this.i1 = this.currentQuestion.getImage();
    this.sceneMain = sceneMain;
    
    
    
                     
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
    * This method sets the top pane of the root that will be
    * put in scene
    * @param primaryStage is the Stage that quiz will show
    */
   private void buildTop(Stage primaryStage) {
     //check if the image exists
     if(this.currentQuestion.getImage() != null) {
       //create an imageview
       ImageView i1 = new ImageView();
       i1 = this.currentQuestion.getImage();
     //set size of image
       i1.setFitWidth(400);
       //maintain ratio of image
       i1.setPreserveRatio(true);
       //keep image quality
       i1.setSmooth(true);
       i1.setCache(true);
       
       //put image in top pane
       root.setTop(i1);
       
       //center the image and margins
       root.setAlignment(i1, Pos.CENTER);
       root.setMargin(i1, new Insets(50));
     }
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

     //defines what happens when button pressed
    
     button.setOnAction(e -> 
     {
       for(RadioButton r2: this.toggles) {
           if(r2.isSelected() ) {
             //check if button pressed is correct answer
             if (this.correctToggles.contains(r2)) {
               //if correct, increment correct variable
               correct++;
               //tell user they got it correct
               popup.setLabel("Answer was correct!");
               popup.show(primaryStage);
               break;
             } else {
               //tell user if they got answer incorrect
               popup.setLabel("Answer was incorrect!");
               popup.show(primaryStage);
             }
           }
         }
         //check if user has gone through all questions
         if(count < max) {
           //go to next question
           Quiz next = new Quiz(sceneMain);
           next.start(primaryStage);
         }
         else {
           //if user is done, go to results page
           Results result = new Results(this.questions,sceneMain,count,correct);
           result.start(primaryStage);
         };
       });
     
     //holds answer toggles for question
     VBox layout2 = new VBox(20);
     for(RadioButton r3: this.toggles) {
       layout2.getChildren().add(r3);
     }
     //add button to layout2
     layout2.getChildren().add(button);
     //set bottom pane of root
     root.setBottom(layout2);
     root.setMargin(layout2, new Insets(0,0,0,432));
   }
   
   /**
    * This method builds the center layout which holds the question text
    * @param primaryStage is the stage of the program
    */
   private void buildCenter(Stage primaryStage) {
     //label for question text
     Label labelQuestion = new Label(this.currentQuestion.getQuestion());
     //wrap text
     labelQuestion.setWrapText(true);
     //put in Vertical Box
     VBox layout= new VBox(5);
     layout.getChildren().add(labelQuestion);
     //center the layout
     layout.setAlignment(Pos.CENTER);
     root.setCenter(layout);
   }

   /**
    * This method builds the stage and displays it
    */
   @Override
   public void start(Stage primaryStage) {
     //Set title to tell user which question they are on
     primaryStage.setTitle("Question "+count+" out of "+this.max);
  
     //Create button toggles
     this.setToggle();
   
     //Build top, bottom, and center panes of the root
     this.buildTop(primaryStage);
     this.buildBottom(primaryStage);
     this.buildCenter(primaryStage);
  
     root.setPadding(new Insets(30));
 
     //Create the scene
     Scene scene1= new Scene(root, 1400, 864);
     scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
     primaryStage.setScene(scene1);
     primaryStage.show();
   }  
}
