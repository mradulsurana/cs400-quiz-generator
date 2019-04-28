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


public class Question {
  private String topic;
  private String question;
  private String image;
  private ArrayList<String> correctAns;
  private ArrayList<String> allAns;
  
  public Question(String question) {
    this.question = question;
    this.correctAns = new ArrayList<String>();
    this.allAns = new ArrayList<String>();
  }
  
  public void setCorrectAns(String correctAns) {
    this.correctAns.add(correctAns);
  }
  
  public ArrayList<String> getCorrectAns() {
    return this.correctAns;
  }
  
  public void setAllAns(String allAns) {
    this.allAns.add(allAns);
  }
  
  public ArrayList<String> getAllAns() {
    return this.allAns;
  }
  
  public void setTopic(String topic) {
    this.topic = topic;
  }
  
  public String getTopic() {
    return this.topic;
  }
  
  public void setQuestion(String question) {
    this.question = question;
  }
  
  public void setImage(String image) {
    this.image = image;
  }
  
  public String getQuestion() {
    return this.question;
  }
  
  public String getImage() {
    return this.image;
  }
}
