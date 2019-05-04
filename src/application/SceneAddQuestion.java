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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This creates a new question and a new topic if that is required.  Adds each element created into 
 * a question, including an image if an image file is included.  Users can create a new topic or 
 * add the question into an existing topic
 * 
 * @author Alfred Holmbeck, Mradul Surana, Allen Chang, Michael Lyrek, Jordan Ingbretson
 *
 */
public class SceneAddQuestion extends Application implements Builder {
	//Fields that connect this class to its parent
	private Main mainClass;
	private Scene scene1;
	private ObservableList<String> topics;
	private ArrayList<Question> allQuestions = new ArrayList<>();
	private ObservableList<String> newTopics = FXCollections.observableArrayList();
	ArrayList<String> correctAnswers = new ArrayList<String>();

	// fields to be added to the scene of the page
	private BorderPane root = new BorderPane();
	Label text = new Label("Text"); // labels
	Label topic = new Label("Topic");
	Label addTopic = new Label("If question is being added to new topic");
	Label image = new Label("Image");
	Label choice = new Label("Options");
	Label correct = new Label("Correct");

	RadioButton choice1 = new RadioButton("A"); // radio buttons
	RadioButton choice2 = new RadioButton("B");
	RadioButton choice3 = new RadioButton("C");
	RadioButton choice4 = new RadioButton("D");
	RadioButton choice5 = new RadioButton("E");

	TextField textField = new TextField(); // text fields
	TextField imageFile = new TextField();
	TextField topicField = new TextField();
	TextField choiceOne = new TextField();
	TextField choiceTwo = new TextField();
	TextField choiceThree = new TextField();
	TextField choiceFour = new TextField();
	TextField choiceFive = new TextField();

	Button add = new Button("Add"); // buttons
	Button back = new Button("Back");
	Button loadImageFile = new Button("Load Image");
	Button resetBox = new Button("Clear Topics Drop Down Box");

	ComboBox<String> topicComboBox;

	GridPane grid = new GridPane(); // to be put on the root border pane
	GridPane grid2 = new GridPane();

	HBox bottomButtons = new HBox(3);

	// needs to take in parameter for topic list
	public SceneAddQuestion(Main main, ObservableList<String> allTopics) {
		mainClass = main;
		topics = allTopics; // gets topic list from parent
	}

	/**
	 * (non-Javadoc)
	 * driver method
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Add Question"); 
		
		// combo box to hold list of topics
		topicComboBox = new ComboBox<String>(topics);
		
		// calls setup method to create each element
		setup();

		// each method builds part of the border pane
		buildCenter();
		buildTop();
		buildBottom();
		
		// event handler for load image button
		loadImageFile.setOnAction(ee -> {
			loadImage(); // calls a method to load the image file into the image text field
		});
		
		// reset combo box to null
		resetBox.setOnAction(eeee -> topicComboBox.setValue(null));

		// event handler for add button
		add.setOnAction(eee -> {
			try {
				CustomPopup popup = new CustomPopup(); // popup for empty fields

				
				File file = new File(imageFile.getText().toString()); // converts the path of the image to a file
				URL imageURL = file.toURI().toURL();
				Image qImage = new Image(imageURL.toString()); // converts the image url to an Image in fx
				ImageView imageView = new ImageView(qImage); // converts image so that it can be displayed in fx

				String topicChosen;
				boolean isNewTopic = false;
				if (topicField.getText().equals("")) // checks if topic field is empty
					topicChosen = topicComboBox.getValue(); // gets value chosen from combo box
				else {
					topicChosen = topicField.getText(); // gets topic from text field
					isNewTopic = true; // boolean value true if there is a new topic
				}
				
				// string to hold question
				String questionText = textField.getText();

				// adds each answer to an array list
				ArrayList<String> answers = new ArrayList<String>();
				if (!choiceOne.getText().equals(""))
					answers.add(choiceOne.getText());
				if (!choiceTwo.getText().equals(""))
					answers.add(choiceTwo.getText());
				if (!choiceThree.getText().equals(""))
					answers.add(choiceThree.getText());
				if (!choiceFour.getText().equals(""))
					answers.add(choiceFour.getText());
				if (!choiceFive.getText().equals(""))
					answers.add(choiceFive.getText());

				// checks how many answer fields arent null
				boolean answersEmpty = true;
				int j = 0;
				for (int i = 0; i < answers.size(); ++i) 
					if (!answers.get(i).equals("")) 
						j++;
					
				if (j >= 2) // checks there are at least two non null answers
					answersEmpty = false;

				boolean bad = false;
				// checks each radio button and if it is checked the corresponding answer is added 
				// to an array list
				// if corresponding answer is null, a boolean is set to true to make a popup
				if (choice1.isSelected())
					if (!choiceOne.getText().equals(""))
						correctAnswers.add(choiceOne.getText());
					else {
						bad = true;
					}
				if (choice2.isSelected())
					if (!choiceTwo.getText().equals(""))
						correctAnswers.add(choiceTwo.getText());
					else {
						bad = true;
					}
				if (choice3.isSelected())
					if (!choiceThree.getText().equals(""))
						correctAnswers.add(choiceThree.getText());
					else {
						bad = true;
					}
				if (choice4.isSelected())
					if (!choiceFour.getText().equals(""))
						correctAnswers.add(choiceFour.getText());
					else {
						bad = true;
					}
				if (choice5.isSelected())
					if (!choiceFive.getText().equals(""))
						correctAnswers.add(choiceFive.getText());
					else {
						bad = true;
					}

				// checks if there is at least one correct answer and that each 
				//correct answer has a non null answer
				if (correctAnswers.isEmpty() || bad) {
					popup.setLabel("Please select correct answers that have an answer given");
					popup.show(primaryStage);
				} else if (!isNewTopic && (String) topicComboBox.getValue() == null) {
					// checks if there is a topic or new topic
					// creates popup if there is none
					popup.setLabel("Please pick a topic or enter new topic");
					popup.show(primaryStage);
				} else if ((String) topicComboBox.getValue() != null && isNewTopic) {
					// checks that only one topic is chosen between the text field and combo box
					// creates popup if so
				    
				    topicComboBox.setValue(null);
					popup.setLabel("Please only pick a topic OR enter new topic");
					popup.show(primaryStage);
				} else if (questionText.equals("")) {
					// checks that there is a question in the text box
					// creates popup if there is none
					popup.setLabel("Please enter text for a question");
					popup.show(primaryStage);
				} else if (answersEmpty) {
					// checks that there are at least two answers non null 
					// creates popup if not
					popup.setLabel("Please enter at least two answers");
					popup.show(primaryStage);
				} else if (topics.contains(topicChosen) && isNewTopic) {
					// checks that the new topic being inout doesnt exsist
					// creates popup if so
					popup.setLabel("Topic already exsists, please select it from the drop down box");
					popup.show(primaryStage);
				} else {
					// if all fields are filled out properly new topic is added to topic lists
					if (isNewTopic) {
						newTopics.add(topicChosen);
						topics.add(topicChosen);
						Collections.sort(topics);
						Collections.sort(newTopics);
					}

					// question is created
					Question newQuestion = new Question(questionText);
					
					for (int k = 0; k < correctAnswers.size(); ++k)
						newQuestion.setCorrectAns(correctAnswers.get(k));
					
					for (int l = 0; l < answers.size(); ++l)
						newQuestion.setAllAns(answers.get(l));
					
					newQuestion.setTopic(topicChosen);
					newQuestion.setMetaData("unused");
					
					if (!imageFile.getText().equals("")) {
						newQuestion.setImage(imageView);
						newQuestion.setImageName(imageFile.getText());
					}

					allQuestions.add(newQuestion);
					
					// if question was proplerly added to list, a success popup is displayed
					if (allQuestions.contains(newQuestion)) {
						popup.setLabel("Question was added, press back to return home, or add a new question");
						popup.show(primaryStage);
					}

					// clears all text fields after question is created
					clearFields();

				}

			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

			// returns to same page
			subStart(primaryStage);

		});

		// event handler for back button, returns to main screen
		back.setOnAction(e -> mainClass.subStart(primaryStage));

		// shows the whole stage
		scene1 = new Scene(root, 1400, 864);
		primaryStage.setScene(scene1);

		scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.show();

	}

	/**
	 * method to clear all text fields and buttons
	 */
	private void clearFields() {
		textField.setText("");
		imageFile.setText("");
		topicField.setText("");
		choiceOne.setText("");
		choiceTwo.setText("");
		choiceThree.setText("");
		choiceFour.setText("");
		choiceFive.setText("");
		topicComboBox.setValue(null);
		choice1.setSelected(false);
		choice2.setSelected(false);
		choice3.setSelected(false);
		choice4.setSelected(false);
		choice5.setSelected(false);
	}

	/**
	 * (non-Javadoc)
	 * method to build the top portion of the border pane
	 * @see application.Builder#buildTop()
	 */
	@Override
	public void buildTop() {
		// textfields and labels are added to grid pane
		grid.add(text, 0, 0, 1, 1);
		grid.add(topic, 0, 1, 1, 1);
		grid.add(image, 0, 2, 1, 1);

		grid.add(textField, 1, 0, 1, 1);
		grid.add(topicComboBox, 1, 1, 1, 1);
		grid.add(imageFile, 1, 2, 1, 1);

		grid.add(addTopic, 2, 0, 1, 1);
		grid.add(topicField, 2, 1, 1, 1);
		grid.add(loadImageFile, 2, 2, 1, 1);

		// dimensions are set
		grid.setVgap(30);
		grid.setHgap(200);
		grid.setAlignment(Pos.CENTER);

		// grid pane is added to border pane
		root.setTop(grid);

	}

	/**
	 * (non-Javadoc)
	 * method to build center of border pane
	 * @see application.Builder#buildCenter()
	 */
	@Override
	public void buildCenter() {
		// text fields and radio buttons are added to grid pane
		grid2.add(choice, 0, 1, 1, 1);
		grid2.add(choiceOne, 0, 2, 1, 1);
		grid2.add(choiceTwo, 0, 3, 1, 1);
		grid2.add(choiceThree, 0, 4, 1, 1);
		grid2.add(choiceFour, 0, 5, 1, 1);
		grid2.add(choiceFive, 0, 6, 1, 1);

		grid2.add(correct, 1, 1, 1, 1);
		grid2.add(choice1, 1, 2, 1, 1);
		grid2.add(choice2, 1, 3, 1, 1);
		grid2.add(choice3, 1, 4, 1, 1);
		grid2.add(choice4, 1, 5, 1, 1);
		grid2.add(choice5, 1, 6, 1, 1);

		// dimesnions are set
		grid2.setVgap(30);
		grid2.setHgap(200);
		grid2.setAlignment(Pos.CENTER);

		// grid pane is added to center
		root.setCenter(grid2);

	}

	/**
	 * (non-Javadoc)
	 * build the bottom of the border pane
	 * @see application.Builder#buildBottom()
	 */
	@Override
	public void buildBottom() {
		// buttons are added to an Hbox
		bottomButtons.getChildren().addAll(back, add, resetBox);
		bottomButtons.setAlignment(Pos.CENTER);
		bottomButtons.setSpacing(200);

		// hbox is added to bottom 
		root.setBottom(bottomButtons);

	}

	/**
	 * (non-Javadoc)
	 * builds left of border pane
	 * @see application.Builder#buildLeft()
	 */
	@Override
	public void buildLeft() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * (non-Javadoc)
	 * builds right of border pane
	 * @see application.Builder#buildRight()
	 */
	@Override
	public void buildRight() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * method sets up each elemnt of scene
	 */
	public void setup() {
		// sets a width for each text field
		choiceOne.setPrefWidth(800);

		// sets prompt texts for each text field
		textField.setPromptText("Enter Question Here");
		imageFile.setPromptText("Enter Image File Here");
		topicField.setPromptText("Enter new topic here");
		choiceOne.setPromptText("Option One");
		choiceTwo.setPromptText("Option Two");
		choiceThree.setPromptText("Option Three");
		choiceFour.setPromptText("Option Four");
		choiceFive.setPromptText("Option Five");

		// disables add button at start
		add.setDisable(true);

		// each radio button enables add button
		choice1.setOnAction(e -> add.setDisable(false));
		choice2.setOnAction(e -> add.setDisable(false));
		choice3.setOnAction(e -> add.setDisable(false));
		choice4.setOnAction(e -> add.setDisable(false));
		choice5.setOnAction(e -> add.setDisable(false));
	}

	/**
	 * method to lead image file
	 */
	private void loadImage() {
		Stage s = new Stage(); // stage for file explorer
		FileChooser fileChooser = new FileChooser(); // to find file
		fileChooser.setTitle("Select a .json file"); // name of window
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PNG File", "*.png"),
				new ExtensionFilter("JPEG File", "*.jpeg")); // only can select .json
																// files
		File selectedFile = fileChooser.showOpenDialog(s); // show the file explorer
		if (selectedFile != null)
			imageFile.setText(selectedFile.getPath()); // get the path and display for user
	}

	/**
	 * getter for new topics created
	 * @return newTopics is a list of all new topics
	 */
	public ObservableList<String> getTopics() {
		return newTopics;
	}

	/**
	 * getter for all questions made
	 * @return allQuestions is a list of the questions made
	 */
	public ArrayList<Question> getQuestions() {
		return allQuestions;
	}

	/**
	 * starts scene from within the scene
	 * @param primaryStage is the stage showing the GUI
	 */
	public void subStart(Stage primaryStage) {
		primaryStage.setScene(scene1);
		primaryStage.show();

	}

}
