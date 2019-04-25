
package application;

import javafx.application.Application;
import javafx.application.Platform;
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
  private Main mainScene;
  
  public Results(Main mainScene,int total, int correct) {
    this.total = total;
    this.correct = correct;
    this.mainScene = mainScene;
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
    Scene scene2 = new Scene(root,1400,86);
    scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    VBox v = new VBox(5);
    HBox h = new HBox(5);
    Label correct = new Label("Questions correct: "+this.getCorrect());
    Label incorrect = new Label("Questions incorrect: "+this.getIncorrect());
    Label percent = new Label("Percent: "+this.getPercent());
    Button makeNew = new Button("Make new quiz");
    makeNew.setOnAction(e -> {
      this.mainScene.subStart(primaryStage);
    });
    Button exit = new Button("Exit");
    exit.setOnAction(e -> Platform.exit());
    v.getChildren().addAll(correct,incorrect,percent);
    h.getChildren().addAll(makeNew,exit);
    root.setCenter(v);
    root.setBottom(h);
    root.setMargin(v, new Insets(100));
    root.setMargin(h, new Insets(100));

    primaryStage.setScene(scene2);
    primaryStage.show();
  }
}
