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
  private String question;
  private Image image;
  public ArrayList<String> correctAns;
  public ArrayList<String> incorrectAns;
  
  public Question(String question) {
    this.question = question;
    this.correctAns = new ArrayList<String>();
    this.incorrectAns = new ArrayList<String>();
  }
  
  public void setQuestion(String question) {
    this.question = question;
  }
  
  public void setImage(Image image) {
    this.image = image;
  }
  
  public String getQuestion() {
    return this.question;
  }
  
  public Image getImage() {
    return this.image;
  }
}
