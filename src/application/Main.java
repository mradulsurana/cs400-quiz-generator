package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import javafx.*;


public class Main extends Application {
  @Override
  public void start(Stage primaryStage) {

    Question questionScene = new Question();

    ArrayList<String> topicsList = new ArrayList<String>();

    ObservableList<String> topics = FXCollections.observableArrayList();

    for (int i = 0; i < 50; i++) {
      topics.add("Topic " + i);
    }


    try {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root, 1152, 864);
      scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();


      GridPane gridpane = new GridPane();


      Label lblWelcome = new Label("Welcome to Quiz Generator");
      Label lblChooseTopic = new Label("Choose Topic");
      Label lblNumQuestions = new Label("Choose Number of Questions");

      Button btnStartQuiz = new Button("Start Quiz");
      Button btnAddTopic = new Button("Add Topic");
      Button btnRemoveTopic = new Button("Remove Topic");
      
      TextField txtNumQuestions = new TextField();
      txtNumQuestions.setPrefWidth(80);
      
      ComboBox<String> dropdownTopics = new ComboBox<String>();
      ListView<String> listTopics = new ListView<String>();

      root.setAlignment(lblWelcome, Pos.CENTER);
      root.setAlignment(btnStartQuiz, Pos.CENTER);
      root.setAlignment(gridpane, Pos.CENTER_LEFT);

      gridpane.add(lblChooseTopic, 0, 0);
      gridpane.add(lblNumQuestions, 0, 1);
      gridpane.add(dropdownTopics, 1, 0);
      gridpane.add(txtNumQuestions, 1, 1);
      gridpane.add(btnAddTopic, 2, 0);
      gridpane.add(btnRemoveTopic, 3, 0);
      
      

      dropdownTopics.getItems().addAll("Topic 1", "Topic 2", "Topic 3", "Topic 4", "Topic 5");


      btnStartQuiz.setOnAction(e -> questionScene.start(primaryStage));
      btnAddTopic.setOnAction(e -> listTopics.setItems(topics));
      btnRemoveTopic.setOnAction(e -> questionScene.start(primaryStage));

      root.setTop(lblWelcome);
      root.setBottom(btnStartQuiz);
      root.setLeft(gridpane);
      root.setRight(listTopics);



    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
