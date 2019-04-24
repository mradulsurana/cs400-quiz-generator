package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SceneLoadFile extends Application{
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Load Quiz Data");
		BorderPane root = new BorderPane();
		
		Scene sceneLoad = new Scene(root, 1000, 800);
		sceneLoad.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		
		HBox userInteraction = new HBox();
		userInteraction.setSpacing(50);
		Label prompt = new Label("Enter file path to load");
		TextField getFileName = new TextField();
		getFileName.setPromptText("ex: C:\\Users\\name\\folder\\fileName.json");
		getFileName.setFocusTraversable(false);
		getFileName.setMinWidth(550);
		userInteraction.getChildren().addAll(prompt, getFileName);
		
		
		root.setLeft(userInteraction);
		
		primaryStage.setScene(sceneLoad);
		primaryStage.show();
		
	}
}
