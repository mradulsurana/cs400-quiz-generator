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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

	public SceneLoadFile(Main mainScene) {
		this.mainScene = mainScene;
		questions = new ArrayList();
		root = new BorderPane();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		primaryStage.setTitle("Load Quiz Data");

		Scene sceneLoad = new Scene(root, 1000, 800);
		sceneLoad.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		buildLeft();
		buildBottom();
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

		// TODO parse together the questions
	}

	private void buildLeft() {
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
