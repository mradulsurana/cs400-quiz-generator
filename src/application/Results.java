//////////////////////////////// HEADER COMMENT //////////////////////////////
//
// Title: cs400-quiz-generator
// Course: (CS 400 section 001, Spring, 2019)
// Due: May 2nd by 10:00pm
//
// Author: (Alfred Holmbeck, Mradul Surana, Allen Chang, Michael Lyrek, Jordan Ingbretson)
// Lecturer's Name: (Prof. KUEMMEL)
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Persons: (none)
// Online Sources: (none)
//
////////////////////////// COMMENTS and|or KNOWN BUGS /////////////////////////
//
// (none)
//
///////////////////////////////////////////////////////////////////////////////

package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Results extends Application{
  private int total;
  private int correct;
  private Main mainScene;
  private ArrayList<Question> questions;
  
  public Results(ArrayList<Question> questions,Main mainScene,int total, int correct) {
    this.questions = questions;
    this.total = total;
    this.correct = correct;
    this.mainScene = mainScene;
  }
  
  public Results() {
    this.total = 0;
    this.correct = 0;
  }
  
  public double getPercent() {
	  return (((double)correct)*100) / (((double)total)*100) * 100;
  }
  
  public int getCorrect() {
    return correct;
  }
  
  public int getIncorrect() {
    return total-correct;
  }
  
  @Override
  public void start(Stage primaryStage) {
    //setting the title
    primaryStage.setTitle("Results");
    
    //create root and scene
    BorderPane root = new BorderPane();
    Scene scene2 = new Scene(root,1400,86);
    scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    
    //Vertical box will hold buttons for save and exit
    VBox v = new VBox(5);
    
    //Horizontal box will hold scoring information
    HBox h = new HBox(5);
    
    //Labels for how well the user did on quiz
    Label correct = new Label("Questions correct: "+this.getCorrect());
    Label incorrect = new Label("Questions incorrect: "+this.getIncorrect());
    Label percent = new Label("Percent: "+(int)(this.getPercent())+"%");
    
    correct.setAlignment(Pos.CENTER);
    incorrect.setAlignment(Pos.CENTER);
    percent.setAlignment(Pos.CENTER);
    
    //Button for making new quiz
    Button makeNew = new Button("Make new quiz");
    makeNew.setOnAction(e -> {
      this.mainScene.subStart(primaryStage);
    });
    
    //Button for exiting
    Button exit = new Button("Exit");
    exit.setOnAction(e -> {
    	ExitPopup pop = new ExitPopup(this.questions);
    	pop.start(primaryStage);
    	pop.show(primaryStage);
    });
    
    //putting labels in the vertical box
    v.getChildren().addAll(correct,incorrect,percent);
    
    //putting buttons into horizontal box
    h.getChildren().addAll(makeNew,exit);
    
    //setting the center and bottom panes of root
    root.setCenter(v);
    root.setBottom(h);
    
    //margins for vertical and horizontal box
    root.setMargin(v, new Insets(100));
    root.setMargin(h, new Insets(100));

    primaryStage.setScene(scene2);
    primaryStage.show();
  }
}
