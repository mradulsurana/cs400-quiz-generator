package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

public class SceneLoadFile extends Application {

	Main mainScene;
	Stage primaryStage;
	BorderPane root;
	ArrayList<Question> questions;
	ArrayList<String> topicsStr;
	ObservableList<String> topics;

	public SceneLoadFile(Main mainScene) {
		this.mainScene = mainScene;
		primaryStage = null;
		questions = new ArrayList();
		root = new BorderPane();
		topicsStr = new ArrayList<String>();
		topics = FXCollections.observableArrayList();
	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException, IOException, ParseException {

		this.primaryStage = primaryStage;
		primaryStage.setTitle("Load Quiz Data");

		Scene sceneLoad = new Scene(root, 1000, 800);
		sceneLoad.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		buildMiddle();
		buildBottom();
		primaryStage.setScene(sceneLoad);
		primaryStage.show();

	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public ObservableList<String> getTopics() {
		return topics;
	}

	private void parseQuestions(String filePath) throws Exception {
		Object obj = new JSONParser().parse(new FileReader(filePath)); // parses .json to an Object
		JSONObject jo = (JSONObject) obj; //casts to JSONObject
		JSONArray qArr = (JSONArray) jo.get("questionArray"); // gets array of questions
		
		for(int i = 0; i < qArr.size(); ++i) { // iterates through each question
			
			JSONObject jQuestion = (JSONObject) qArr.get(i); // the question as a JSONObject
			Question question = new Question((String)jQuestion.get("questionText")); // adds text of question
			
			question.setTopic((String)jQuestion.get("topic")); // adds the question topic
			
			File childJson = new File(filePath); // .json as file object
			String parentDir = childJson.getParent(); // parent dir which image should be in
			Image qImage = new Image(parentDir + (String)jQuestion.get("image")); // image should be in same directory
			question.setImage(qImage); // connects the image to the question
			
			JSONArray choices = (JSONArray) jQuestion.get("choiceArray"); // choices to parse
			for(int j = 0; j < choices.size(); j++) { // iterate through choices
				JSONObject choice = (JSONObject) choices.get(i); // the given choice
				question.getAllAns().add((String) choice.get("choice")); // text of the given choice
				if(((String)choice.get("isCorrect")).equals("T")) { // if correct, adds to correct ans list in question
					question.getCorrectAns().add((String)choice.get("isCorrect"));
				}
			}
			
		}
	}

	private void buildMiddle() {
		VBox leftBox = new VBox(30);
		leftBox.setId("VBox");

		Label prompt = new Label("Please open and load a .json file");

		Label pathName = new Label("                                               ");
		pathName.setId("labelPathName");
		pathName.setBackground(new Background(new BackgroundFill(Color.GHOSTWHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		
		Button btnLoad = new Button("Load File");
		Button btnOpen = new Button("Open File");
		leftBox.getChildren().addAll(prompt, pathName, btnOpen);
		root.setCenter(leftBox);
		
		btnOpen.setOnAction(e -> {
			Stage s = new Stage();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select a .json file");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.json"));
			File selectedFile = fileChooser.showOpenDialog(s);
			if(selectedFile != null) {
				
				pathName.setText(selectedFile.getPath());
				if(!leftBox.getChildren().contains(btnLoad)) {
					leftBox.getChildren().addAll(btnLoad);
				}
			}
		});

		btnLoad.setOnAction(ev -> {
			try {
				if (pathName.getText() != null) {
					parseQuestions(pathName.getText());
				}
				mainScene.subStart(primaryStage);
			} catch (Exception ex) {
				CustomPopup pop = new CustomPopup();
				pop.setLabel("Oops. The format of this JSON file was incorrect.");
				pop.show(primaryStage);
			}
		});

	}

	private void buildBottom() {

		HBox bottomBox = new HBox();
		Button btnBack = new Button("Back");
		bottomBox.getChildren().addAll(btnBack);

		btnBack.setOnAction(e -> {
			mainScene.subStart(primaryStage);
		});

		root.setBottom(bottomBox);
	}
}
