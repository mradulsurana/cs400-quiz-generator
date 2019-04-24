
package application;

import javafx.application.Application;
import javafx.geometry.Insets;
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
  
  public Results(int total, int correct) {
    this.total = total;
    this.correct = correct;
  }
  
  public Results() {
    this.total = 0;
    this.correct = 0;
  }
  
  public double getPercent() {
    return (double)correct/total;
  }
  
  public int getCorrect() {
    return correct;
  }
  
  public int getIncorrect() {
    return total-correct;
  }
  
  @Override
  public void start(Stage primaryStage) {
    BorderPane root = new BorderPane();
    VBox v = new VBox(5);
    HBox h = new HBox(5);
    Label correct = new Label("Questions correct: "+this.getCorrect());
    Label incorrect = new Label("Questions incorrect: "+this.getIncorrect());
    Label percent = new Label("Percent: "+this.getPercent());
    Button makeNew = new Button("Make new quiz");
    Button exit = new Button("Exit");
    v.getChildren().addAll(correct,incorrect,percent);
    h.getChildren().addAll(makeNew,exit);
    root.setCenter(v);
    root.setBottom(h);
    root.setMargin(v, new Insets(100));
    root.setMargin(h, new Insets(100));
    Scene scene2 = new Scene(root,700,700);
    primaryStage.setScene(scene2);
    primaryStage.show();
  }
}
