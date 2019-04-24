package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.*;
import javafx.stage.Popup;


public class Main extends Application {

  ObservableList<String> topics = FXCollections.observableArrayList();

  @Override
  public void start(Stage primaryStage) {

    // populate topics with sample values
    for (int i = 0; i < 50; i++) {
      topics.add("Topic" + i);
    }


    try {
      // create main scene using borderpane
      BorderPane root = new BorderPane();
      createMainScene(root, primaryStage, topics);

    } catch (Exception e) {
      // catch any unknown exceptions
    }
  }

  public void subStart(Stage primaryStage) {
    primaryStage.show();
  }

  private void createMainScene(BorderPane root, Stage primaryStage, ObservableList<String> topics) {

    primaryStage.setTitle("Quiz Generator");

    Scene scene = new Scene(root, 1400, 864); // set size of screen
    primaryStage.setMinWidth(1400);
    primaryStage.setMinHeight(864);
    // get CSS
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    primaryStage.setScene(scene); // set primary state and show stage
    primaryStage.show();

    // create layout boxes to hold other elements
    GridPane gridpane = new GridPane();
    gridpane.setHgap(10); // set padding between HBox elements
    HBox hbox = new HBox(10);
    VBox vboxRight = new VBox(10);
    vboxRight.setId("topicsVBox"); // set id to change CSS of VBox

    // create labels
    Label lblWelcome = new Label("Welcome to Quiz Generator");
    lblWelcome.setId("welcome");
    Label lblChooseTopic = new Label("Choose Topic");
    Label lblNumQuestions = new Label("Choose Number of Questions");
    Label listTopicsLabel = new Label("Selected Topics");


    // create buttons
    Button btnStartQuiz = new Button("Start Quiz");
    Button btnLoad = new Button("Load");
    Button btnAddTopic = new Button("Add Topic");
    Button btnRemoveTopic = new Button("Remove Topic");
    Button btnAddQuestions = new Button("Add Questions");

    // create textfield
    TextField txtNumQuestions = new TextField();
    txtNumQuestions.setPrefWidth(100); // set width of textfield

    // create drop down for available topics and listview to hold added topics
    ComboBox<String> dropdownTopics = new ComboBox<String>(topics);
    // alternative way to add sample topics to drop down
    // dropdownTopics.getItems().addAll(topics);
    ListView<String> listTopics = new ListView<String>();
    dropdownTopics.setPrefWidth(300); // set width of dropdown list of topics

    // set the alignment of elements on the page
    BorderPane.setAlignment(lblWelcome, Pos.CENTER);
    BorderPane.setAlignment(gridpane, Pos.CENTER_LEFT);
    BorderPane.setAlignment(hbox, Pos.BOTTOM_CENTER);

    gridpane.setId("gridpane"); // give gridpane an id to apply CSS

    // place elements in gridpane
    gridpane.add(lblChooseTopic, 0, 0);
    gridpane.add(lblNumQuestions, 0, 1);
    gridpane.add(dropdownTopics, 1, 0);
    gridpane.add(txtNumQuestions, 1, 1);
    gridpane.add(btnAddTopic, 2, 0);
    gridpane.add(btnRemoveTopic, 3, 0);

    // create a custom popup that has an "ok" button to close the popup
    CustomPopup popup = new CustomPopup(primaryStage);

    // event handler to start quiz
    btnStartQuiz.setOnAction(e -> {
      // checks if the user typed in the number of questions and if the user selected topics

      try {
        int numQuestions = Integer.parseInt(txtNumQuestions.getText());
        if (!txtNumQuestions.getText().equals("") && !listTopics.getItems().isEmpty()) {
          Question questionScene = new Question();
          questionScene.start(primaryStage);
        } else { // prompt user to fill out fields
          popup.setLabel("Please enter the number of questions and choose at least one topic");
          popup.show();
        }

      } catch (NumberFormatException f) {
        // prompt user to fill out fields correctly
        popup.setLabel("Please enter the number of questions");
        popup.show();
      }


      // pass: txtNumQuestions.getText() into method of questionScene to pass the number of
      // questions
    });

    // add topic to the list of topics for the quiz
    btnAddTopic.setOnAction(e -> {
      String topic = dropdownTopics.getValue(); // get value of topic chosen in drop down

      // check if the topic is actually chosen and it is not already contained in the list
      if (topic != null && !listTopics.getItems().contains(topic))
        listTopics.getItems().add(topic); // add topic to list
      else if (topic != null && !dropdownTopics.getValue().equals("")) {
        // show error message if the topic is already in the list
        popup.setLabel("You have already added this topic to the list");
        popup.show();
      } else if (topic == null) {
        // show error message if no topic is selected from drop down
        popup.setLabel("Please select a topic to add it to the list");
        popup.show();
      }
    });

    // remove a topic if the user selects it from the drop down
    btnRemoveTopic.setOnAction(e -> {
      listTopics.getItems().remove(dropdownTopics.getValue());
    });

    // display load JSON screen
    btnLoad.setOnAction(e -> {
      SceneLoadFile loadFileScene = new SceneLoadFile(this);
      try {
        loadFileScene.start(primaryStage);
      } catch (Exception e1) {

      }
    });

    // display load JSON screen
    btnAddQuestions.setOnAction(e -> {
      SceneAddQuestion addQuestionScene = new SceneAddQuestion();
      try {
        addQuestionScene.start(primaryStage);
      } catch (Exception e1) {

      }
    });

    // set location of elements on the page
    root.setTop(lblWelcome);
    root.setBottom(hbox);
    root.setLeft(gridpane);
    root.setRight(vboxRight);

    // add elements to HBox
    hbox.getChildren().add(btnStartQuiz);
    hbox.getChildren().add(btnLoad);
    hbox.getChildren().add(btnAddQuestions);

    // add elements to topics VBox
    vboxRight.getChildren().add(listTopicsLabel);
    vboxRight.getChildren().add(listTopics);
    listTopics.setMinHeight(600); // set minimum height of topics list

  }

  public static void main(String[] args) {
    launch(args);
  }
}
