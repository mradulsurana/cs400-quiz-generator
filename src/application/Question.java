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

/**
 * A class that stores information about a question 
 * to be used in the quiz generator program, that includes
 * the topic, choices, image, and question text
 * @author allen
 *
 */
public class Question {
  //topic of question
  private String topic;
  //question text
  private String question;
  //question image
  private Image image;
  //correct answers to the question
  private ArrayList<String> correctAns;
  //all answers to the question
  private ArrayList<String> allAns;
  
  /**
   * This constructor takes in the question text
   * @param question
   */
  public Question(String question) {
    this.question = question;
    //initializes array lists for correct and all answers
    this.correctAns = new ArrayList<String>();
    this.allAns = new ArrayList<String>();
  }
  
  /**
   * This method adds a correct answer to the question
   * @param correctAns is a correct answer
   */
  public void setCorrectAns(String correctAns) {
    this.correctAns.add(correctAns);
  }
  
  /**
   * This method gets an array list containing all the correct answers
   * @return a list of all correct answers
   */
  public ArrayList<String> getCorrectAns() {
    return this.correctAns;
  }
  
  /**
   * This method adds an answer to the question, could be correct or incorrect
   * @param allAns is the string answer to add
   */
  public void setAllAns(String allAns) {
    this.allAns.add(allAns);
  }
  
  /**
   * This method gets the array list containing all answers to the question
   * @return a list of all answers
   */
  public ArrayList<String> getAllAns() {
    return this.allAns;
  }
  
  /**
   * This method sets the topic for the question
   * @param topic is the topic of the question
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }
  
  /**
   * This method gets the topic for the question
   * @return the topic
   */
  public String getTopic() {
    return this.topic;
  }
  
  /**
   * This method sets the question string 
   * @param question is the text of the question
   */
  public void setQuestion(String question) {
    this.question = question;
  }
  
  /**
   * This method gets the question text
   * @return the question text
   */
  public String getQuestion() {
    return this.question;
  }
  
  /**
   * This method sets the image for the question
   * @param image is the image for the question
   */
  public void setImage(Image image) {
    this.image = image;
  }
  
  /**
   * This method gets the image for the question
   * @return image of the question
   */
  public Image getImage() {
    return this.image;
  }
}
