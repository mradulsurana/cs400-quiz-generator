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
import java.util.Collections;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * This class is the main driver application for the entire program and holds the GUI elements for
 * the main page. The application allows users to load questions from a JSON file, create new
 * questions from the applications itself, take a quiz based on loaded or created questions (user
 * can select topics and number of questions), and save
 * 
 * @author Alfred Holmbeck, Mradul Surana, Allen Chang, Michael Lyrek, Jordan Ingbretson
 *
 */
public class Main extends Application {

  private ObservableList<String> topics = FXCollections.observableArrayList();
  private ArrayList<Question> questions = new ArrayList<Question>();
  private Scene scene;
  private BorderPane root = new BorderPane();
  private SceneLoadFile loadFileScene;
  private SceneAddQuestion addQuestionScene;

  // create buttons
  private Button btnStartQuiz = new Button("Start Quiz");
  private Button btnLoad = new Button("Load");
  private Button btnAddQuestions = new Button("Add Questions");
  private Button btnAddTopic = new Button("Add Topic");
  private Button btnRemoveTopic = new Button("Remove Topic");

  // create TextField
  private TextField txtNumQuestions = new TextField();

  // create ListView to hold added topics
  private ListView<String> listTopics = new ListView<String>();

  // create drop down for available topics
  private ComboBox<String> dropdownTopics = new ComboBox<String>(topics);

  // create a custom popup that has an "ok" button to close the popup
  private CustomPopup popup = new CustomPopup();

  // create labels
  private Label lblWelcome = new Label("Welcome to Quiz Generator");
  private Label listTopicsLabel = new Label("Selected Topics");
  private Label lblTotalQuestions = new Label("Number of Questions in Question Database: 0");

  /**
   * This is the main start method. It creates the GUI.
   */
  @Override
  public void start(Stage primaryStage) {

    try {
      // create main scene using BorderPane
      createMainScene(primaryStage, topics);

    } catch (Exception e) {
      // catch any unknown exceptions
    }
  }

  public void subStart(Stage primaryStage) {
    // questions = loadfileScene.getQuestions(); // arraylist of type Questions
    // topics = .getTopics // observable list of type String

    getQuestionsLoadFile(); // get new questions and topics
    loadFileScene = null; // set this to null so it cannot be called again
    addQuestionScene = null; // set this to null so it cannot be called again

    Collections.sort(topics); // sort topics alphabetically since new topics may have been added

    lblTotalQuestions.setText("Number of Questions in Question Database: " + questions.size());

    primaryStage.setScene(scene);
    primaryStage.show();

  }

  private void createMainScene(Stage primaryStage, ObservableList<String> topics) {

    primaryStage.setTitle("Quiz Generator"); // set Title of Application

    scene = new Scene(root, 1400, 864); // set size of screen

    // set minimum screen size, so user cannot size below the requirements before
    primaryStage.setMinWidth(1400);
    primaryStage.setMinHeight(864);

    // get CSS
    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    primaryStage.setScene(scene); // set primary state and show stage
    primaryStage.show();

    // set all elements of BorderPane layout
    buildTop();
    buildLeft();
    buildRight();
    buildBottom();
    handleEvents(primaryStage);



    /*
     * // set the alignment of elements on the page BorderPane.setAlignment(gridpane,
     * Pos.CENTER_LEFT); BorderPane.setAlignment(lblWelcome, Pos.CENTER);
     * BorderPane.setAlignment(hbox, Pos.BOTTOM_CENTER);
     */



  }

  public static void main(String[] args) {
    launch(args);
  }

  public void buildTop() {
    // set location of elements on the page
    root.setTop(lblWelcome);
    lblWelcome.setId("welcome");
  }

  public void buildLeft() {

    // create layout box to hold other elements
    VBox vbox = new VBox(80);
    GridPane gridpane = new GridPane();
    gridpane.setHgap(10); // set padding between HBox elements
    vbox.setId("leftVBox"); // give gridpane an id to apply CSS
    gridpane.setId("gridpane"); // give gridpane an id to apply CSS

    root.setLeft(vbox); // add gridpane to borderpane

    // create labels
    Label lblChooseTopic = new Label("Choose Topic");
    Label lblNumQuestions = new Label("Choose Number of Questions");


    // alternative way to add sample topics to drop down
    // dropdownTopics.getItems().addAll(topics);
    dropdownTopics.setPrefWidth(300); // set width of dropdown list of topics



    txtNumQuestions.setPrefWidth(100); // set width of textfield

    // place elements in gridpane
    gridpane.add(lblChooseTopic, 0, 0);
    gridpane.add(lblNumQuestions, 0, 1);
    gridpane.add(dropdownTopics, 1, 0);
    gridpane.add(txtNumQuestions, 1, 1);
    gridpane.add(btnAddTopic, 2, 0);
    gridpane.add(btnRemoveTopic, 3, 0);

    vbox.getChildren().add(gridpane);
    vbox.getChildren().add(lblTotalQuestions);

  }

  public void buildRight() {

    VBox vboxRight = new VBox(10); // create new VBox to store elements horizontally
    vboxRight.setId("VBox"); // set id to change CSS of VBox
    root.setRight(vboxRight); // add HBox to BorderPane left


    // add elements to topics VBox
    vboxRight.getChildren().add(listTopicsLabel);
    vboxRight.getChildren().add(listTopics);
    listTopics.setMinHeight(600); // set minimum height of topics list

  }

  public void buildBottom() {


    HBox hbox = new HBox(10); // create new HBox to store elements horizontally
    root.setBottom(hbox); // add HBox to BorderPane bottom

    // add elements to HBox
    hbox.getChildren().add(btnStartQuiz);
    hbox.getChildren().add(btnLoad);
    hbox.getChildren().add(btnAddQuestions);
  }

  public void handleEvents(Stage primaryStage) {
    // event handler to start quiz
    btnStartQuiz.setOnAction(e -> {
      // checks if the user typed in the number of questions and if the user selected topics

      try {
        int numQuestions = Integer.parseInt(txtNumQuestions.getText());
        if (!txtNumQuestions.getText().equals("") && !listTopics.getItems().isEmpty()
            && (numQuestions > 0)) {

          ObservableList<String> selectedTopics = FXCollections.observableArrayList();
          selectedTopics.addAll(listTopics.getItems());

          // clear fields so the next time the user creates a quiz the fields are reset
          txtNumQuestions.clear();
          listTopics.getItems().clear();

          // create and start Quiz scene
          Quiz quizScene = new Quiz(this, questions, numQuestions, selectedTopics);
          quizScene.start(primaryStage);


        } else { // prompt user to fill out fields
          popup.setLabel(
              "Please enter a positive number of questions and choose at least one topic");
          popup.show(primaryStage);
        }

      } catch (NumberFormatException f) {
        // prompt user to fill out fields correctly
        popup.setLabel("Please enter the number of questions");
        popup.show(primaryStage);
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
        popup.show(primaryStage);
      } else if (topic == null) {
        // show error message if no topic is selected from drop down
        popup.setLabel("Please select a topic to add it to the list");
        popup.show(primaryStage);
      }
    });

    // remove a topic if the user selects it from the drop down
    btnRemoveTopic.setOnAction(e -> {
      listTopics.getItems().remove(dropdownTopics.getValue());
    });

    // display load JSON screen
    btnLoad.setOnAction(e -> {
      loadFileScene = new SceneLoadFile(this);
      try {
        loadFileScene.start(primaryStage);
      } catch (Exception e1) {

      }
    });

    // display load JSON screen
    btnAddQuestions.setOnAction(e -> {
      addQuestionScene = new SceneAddQuestion(this, topics);
      try {
        addQuestionScene.start(primaryStage);
      } catch (Exception e1) {

      }
    });

    dropdownTopics.showingProperty().addListener((obs, wasShowing, isNowShowing) -> {
      if (isNowShowing && topics.size() == 0) {
        popup.setLabel("Please load or add questions to select a topic");
        popup.show(primaryStage);
      } else {
        // choice box popup is now hidden
      }
    });


    // TODO: add code that gives popup when no topics are there

  }

  private void getQuestionsLoadFile() {
    // get the questions loaded from the JSON file
    if (loadFileScene != null) {
      ObservableList<String> newTopics = loadFileScene.getTopics();
      ArrayList<Question> newQuestions = loadFileScene.getQuestions();

      if (newTopics != null && newQuestions != null) {
        for (int i = 0; i < newQuestions.size(); i++) {
          questions.add(newQuestions.get(i)); // duplicate questions can be added to questions list
        }

        for (int i = 0; i < newTopics.size(); i++) {

          // add a topic to list if it is not in the list
          if (!topics.contains(newTopics.get(i))) {
            topics.add(newTopics.get(i));
          }
        }
      }



    }

    if (addQuestionScene != null) {
      ObservableList<String> newTopics = addQuestionScene.getTopics();
      ArrayList<Question> newQuestions = addQuestionScene.getQuestions();

      if (newTopics != null && newQuestions != null) {
        for (int i = 0; i < newQuestions.size(); i++) {
          questions.add(newQuestions.get(i)); // duplicate questions can be added to questions list
        }

        for (int i = 0; i < newTopics.size(); i++) {
          // add a topic to list if it is not in the list
          if (!topics.contains(newTopics.get(i))) {
            topics.add(newTopics.get(i));
          }
        }
      }



    }

  }


}
