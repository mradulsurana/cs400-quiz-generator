//////////////////////////////// HEAEDER COMMENT //////////////////////////////
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Loads a .json file from the user using a file explorer. (any images referenced from the .json should be in the
 * same directory as the .json itself. Users can load multiple .jsons before returning to the home screen using
 * the back button.
 * 
 * @author Alfred Holmbeck, Mradul Surana, Allen Chang, Michael Lyrek, Jordan Ingbretson
 *
 */
public class SceneLoadFile extends Application {

	Main mainScene; // instance to go back to when the files have been loaded
	Stage primaryStage; // stage for displaying scenes
	BorderPane root; // to display on scenes
	ArrayList<Question> questions; // question which are parsed
	ArrayList<String> topicsStr; // string arr of topics
	ObservableList<String> topics; // topics as observable list

	/**
	 * constructor to set all the initial values of the scene
	 * @param mainScene
	 */
	public SceneLoadFile(Main mainScene) {
		this.mainScene = mainScene;
		primaryStage = null;
		questions = new ArrayList();
		root = new BorderPane();
		topicsStr = new ArrayList<String>();
		topics = FXCollections.observableArrayList();
	}

	/**
	 * runs to show the load window to user
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage; // primary stage to be passed to other classes
		primaryStage.setTitle("Load Quiz Data"); // changes the title of window

		Scene sceneLoad = new Scene(root, 1000, 800); // scene to display on stage
		sceneLoad.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // style of app

		buildMiddle(); // build the middle of the scene
		buildBottom(); // build the bottom (back button) of the scene
		primaryStage.setScene(sceneLoad); // puts scene on stage
		primaryStage.show(); // shows the stage

	}

	/**
	 * gets the questions parsed from the .json files
	 * @return ArrayList<Question> of questions
	 */
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	/**
	 * gets all the different topics of the questions of the loaded .json files
	 * @return ObservableList<String> of topics
	 */
	public ObservableList<String> getTopics() {
		return topics;
	}

	/**
	 * parses a .json file into an ObservableList of Question Objects
	 * @param filePath to the .json to parse
	 * @throws FileNotFoundException if the file was not found
	 * @throws IOException if problem getting the file
	 * @throws ParseException if problem parsing the file
	 */
	private void parseQuestions(String filePath) throws FileNotFoundException, IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader(filePath)); // parses .json to an Object
		JSONObject jo = (JSONObject) obj; //casts to JSONObject
		JSONArray qArr = (JSONArray) jo.get("questionArray"); // gets array of questions
		
		for(int i = 0; i < qArr.size(); ++i) { // iterates through each question
			
			JSONObject jQuestion = (JSONObject) qArr.get(i); // the question as a JSONObject
			Question question = new Question((String)jQuestion.get("questionText")); // adds text of question
			
			String topic = (String)jQuestion.get("topic");
			question.setTopic(topic); // adds the question topic
			
			String imageName = (String)jQuestion.get("image"); // only try and add an image if there is an image to add
			if(!imageName.equals("none")) {
				Path sibling = Paths.get(filePath); // .json as path
				Path childImage = Paths.get(sibling.getParent().toString(), imageName); // gets the path of the sibling (image) of .json
				File imageFile = new File(childImage.toString()); // converts the path of the image to a file
				URL imageURL = imageFile.toURI().toURL(); // converts the image file to a url
				Image qImage = new Image(imageURL.toString()); // converts the image url to an Image in fx
				ImageView image = new ImageView(qImage); // converts image so that it can be displayed in fx
				question.setImage(image); // connects the image to the question
			}
			
			JSONArray choices = (JSONArray) jQuestion.get("choiceArray"); // choices to parse
			for(int j = 0; j < choices.size(); j++) { // iterate through choices
				JSONObject choice = (JSONObject) choices.get(j); // the given choice
				question.getAllAns().add((String) choice.get("choice")); // text of the given choice
				if(((String)choice.get("isCorrect")).equals("T")) { // if correct, adds to correct ans list in question
					question.getCorrectAns().add((String)choice.get("choice"));
				}
			}
			questions.add(question); // add the given question
			if(!topics.contains(topic)) { // add the topic if not already a topic in the list of topics
				topics.add(topic);
			}
			//UNCOMMENT FOR TESTING
//			System.out.println("question: " + question.getQuestion());
//			System.out.println("topic: " + question.getTopic());
//			System.out.println("image: " + question.getImage());
//			System.out.println("all choices: " + question.getAllAns());
//			System.out.println("correct choices: " + question.getCorrectAns());
//			System.out.println();
		}
		//UNCOMMENT FOR TESTING
//		System.out.println(topics);
	}

	/**
	 * builds the middle of the GUI with an open button, and then a load button when a file has been loaded
	 */
	private void buildMiddle() {
		root.setCenter(null); // resets the root (for recursive calls if want to load another file)
		VBox middleBox = new VBox(30); // holds middle contents
		middleBox.setId("VBox"); // css id

		Label prompt = new Label("Please open and load a .json file"); // communicate with user
		
		Label pathName = new Label("    ** No File Openned Yet **    "); // file path of .json loaded
		pathName.setId("labelPathName"); // css id
		// shows when no file is openned yet
		pathName.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		Button btnLoad = new Button("Load File"); // to load file (parsing .json)
		Button btnOpen = new Button("Open File"); // to open a .json
		middleBox.getChildren().addAll(prompt, pathName, btnOpen); // fills middle of screen
		root.setCenter(middleBox);
		
		btnOpen.setOnAction(e -> { // if open button is pressed
			Stage s = new Stage(); // stage for file explorer
			FileChooser fileChooser = new FileChooser(); // to find file
			fileChooser.setTitle("Select a .json file"); // name of window
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json")); // only can select .json files
			File selectedFile = fileChooser.showOpenDialog(s); // show the file explorer
			if(selectedFile != null) { // if file was selected...
				
				pathName.setText(selectedFile.getPath()); //get the path and display for user
				if(!middleBox.getChildren().contains(btnLoad)) { //if load button not on the screen already...
					middleBox.getChildren().addAll(btnLoad); // add it to the screen for parsing
				}
			}
		});

		btnLoad.setOnAction(e2 -> { // if load button pressed
			try {
				if (pathName.getText() != null) { // if a path was selected...
					parseQuestions(pathName.getText()); // parse the file
					CustomPopup pop = new CustomPopup(); // tell of parse success
					pop.setLabel("File loaded successfully and questions were added.");
					pop.show(primaryStage);
				}
				buildMiddle(); // user can load another file if they so choose
				
			} catch (Exception ex) { // parsing the file did not work
				CustomPopup pop = new CustomPopup(); // tell the user and have them try again
				pop.setLabel("Oops. The format of this JSON file was incorrect.\nPlease check over the file and try again.");
				pop.show(primaryStage);
			}
		});

	}

	/**
	 * builds the bottom of the GUI (back button)
	 */
	private void buildBottom() {

		// display the back button to go back to the main home screen
		HBox bottomBox = new HBox();
		Button btnBack = new Button("Back");
		bottomBox.getChildren().addAll(btnBack);

		btnBack.setOnAction(e -> { // if pressed, the user is done loading and can move on with making a quiz
			mainScene.subStart(primaryStage);
		});

		root.setBottom(bottomBox);
	}
}
