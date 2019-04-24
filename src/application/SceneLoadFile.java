package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneLoadFile extends Application{
	
	Main mainScene;
	ArrayList<Question> questions;
	
	public SceneLoadFile(Main mainScene) {
		this.mainScene = mainScene;
		questions = new ArrayList();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Load Quiz Data");
		BorderPane root = new BorderPane();
		
		Scene sceneLoad = new Scene(root, 1000, 800);
		sceneLoad.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		
		VBox userInteraction = new VBox(10);
		
		Label prompt = new Label("Enter file path to load");
		
		TextField getFileName = new TextField();
		getFileName.setPromptText("ex: C:\\Users\\name\\folder\\fileName.json");
		getFileName.setFocusTraversable(false);
		getFileName.setMinWidth(550);
		
		Button btnLoad = new Button("Load File");
		btnLoad.setOnAction(e -> {
			try {
				parseQuestions(getFileName.getText());
				mainScene.subStart();	
			} catch (Exception ex){
				CustomPopup pop = new CustomPopup(primaryStage);
				pop.setLabel("Oops. Something went wrong.\nMake sure the file path name is correct and try again.");
				//TODO
			}
		});
		
		userInteraction.getChildren().addAll(prompt, getFileName, btnLoad);
		
		Button btnBack = new Button("Back");
		btnBack.setOnAction(e -> {
			mainScene.subStart();
			//TODO
		});
		
		root.setLeft(userInteraction);
		
		primaryStage.setScene(sceneLoad);
		primaryStage.show();
		
	}
	
	public ArrayList<Question> getQuizData() {
		return questions;
	}
	
	private void parseQuestions(String filePath) throws Exception {
		Object obj = new JSONParser().parse(new FileReader(filePath));
		JSONObject jo = (JSONObject) obj;
		JSONArray qArr = (JSONArray) jo.get("questionArray");

		//TODO parse together the questions
	}
}
