package application;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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

public class SceneAddQuestion extends Application {
	Main mainClass;
	Scene scene1;
	ObservableList<String> topics;
	ArrayList<Question> allQuestions = new ArrayList<>();
	ObservableList<String> newTopics = FXCollections.observableArrayList();

	// needs to take in parameter for topic list
	public SceneAddQuestion(Main main, ObservableList<String> allTopics) {
		mainClass = main;
		topics = allTopics;
	}

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();

		primaryStage.setTitle("Add Question");

		Label text = new Label("Text");
		Label topic = new Label("Topic");
		Label addTopic = new Label("If question is being added to new topic");
		Label image = new Label("Image");
		Label choice = new Label("Options");
		Label correct = new Label("Correct");

		RadioButton choice1, choice2, choice3, choice4, choice5;
		choice1 = new RadioButton("A");
		choice2 = new RadioButton("B");
		choice3 = new RadioButton("C");
		choice4 = new RadioButton("D");
		choice5 = new RadioButton("E");

		TextField textField = new TextField();
		TextField imageFile = new TextField();
		TextField topicField = new TextField();
		TextField choiceOne, choiceTwo, choiceThree, choiceFour, choiceFive;
		choiceOne = new TextField();
		choiceTwo = new TextField();
		choiceThree = new TextField();
		choiceFour = new TextField();
		choiceFive = new TextField();
		choiceOne.setPrefWidth(800);

		textField.setPromptText("Enter Question Here");
		imageFile.setPromptText("Enter Image File Here");
		topicField.setPromptText("Enter new topic here");
		choiceOne.setPromptText("Option One");
		choiceTwo.setPromptText("Option Two");
		choiceThree.setPromptText("Option Three");
		choiceFour.setPromptText("Option Four");
		choiceFive.setPromptText("Option Five");

		Button add = new Button("Add");
		Button back = new Button("Back");
		Button loadImageFile = new Button("Load Image");
		add.setDisable(true);

		choice1.setOnAction(e -> add.setDisable(false));
		choice2.setOnAction(e -> add.setDisable(false));
		choice3.setOnAction(e -> add.setDisable(false));
		choice4.setOnAction(e -> add.setDisable(false));
		choice5.setOnAction(e -> add.setDisable(false));

		ComboBox<String> topicComboBox = new ComboBox<String>(topics);

		GridPane grid = new GridPane();

		grid.add(text, 0, 0, 1, 1);
		grid.add(topic, 0, 1, 1, 1);
		grid.add(image, 0, 2, 1, 1);

		grid.add(textField, 1, 0, 1, 1);
		grid.add(topicComboBox, 1, 1, 1, 1);
		grid.add(imageFile, 1, 2, 1, 1);

		grid.add(addTopic, 2, 0, 1, 1);
		grid.add(topicField, 2, 1, 1, 1);
		grid.add(loadImageFile, 2, 2, 1, 1);

		GridPane grid2 = new GridPane();

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

		HBox bottomButtons = new HBox(2);
		bottomButtons.getChildren().addAll(back, add);
		bottomButtons.setAlignment(Pos.CENTER);
		bottomButtons.setSpacing(200);

		grid.setVgap(30);
		grid.setHgap(200);
		grid.setAlignment(Pos.CENTER);

		grid2.setVgap(30);
		grid2.setHgap(200);
		grid2.setAlignment(Pos.CENTER);

		root.setCenter(grid2);
		root.setTop(grid);
		root.setBottom(bottomButtons);

		loadImageFile.setOnAction(ee -> {
			Stage s = new Stage(); // stage for file explorer
			FileChooser fileChooser = new FileChooser(); // to find file
			fileChooser.setTitle("Select a .json file"); // name of window
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("PNG File", "*.png"),
					new ExtensionFilter("JPEG File", "*.jpeg")); // only can select .json
																	// files
			File selectedFile = fileChooser.showOpenDialog(s); // show the file explorer
			if (selectedFile != null)
				imageFile.setText(selectedFile.getPath()); // get the path and display for user
		});

		add.setOnAction(eee -> {
			try {
				CustomPopup popup = new CustomPopup();

				File file = new File(imageFile.getText().toString()); // converts the path of the image to a file
				URL imageURL = file.toURI().toURL();
				Image qImage = new Image(imageURL.toString()); // converts the image url to an Image in fx
				ImageView imageView = new ImageView(qImage); // converts image so that it can be displayed in fx

				String topicChosen;
				boolean isNewTopic = false;
				if (topicField.getText().equals(""))
					topicChosen = topicComboBox.getValue();
				else {
					topicChosen = topicField.getText();
					isNewTopic = true;
				}

				String questionText = textField.getText();

				ArrayList<String> answers = new ArrayList<String>();
				answers.add(choiceOne.getText());
				answers.add(choiceTwo.getText());
				answers.add(choiceThree.getText());
				answers.add(choiceFour.getText());
				answers.add(choiceFive.getText());
				ArrayList<String> correctAnswers = new ArrayList<String>();

				boolean answersEmpty = true;
				int j = 0;
				for (int i = 0; i < answers.size(); ++i) {
					if (!answers.get(i).equals("")) {
						j++;
					}
				}

				if (j >= 2)
					answersEmpty = false;

				boolean bad = false;

				if (choice1.isSelected()) {
					if (!choiceOne.getText().equals(""))
						correctAnswers.add(choiceOne.getText());
					else {
						bad = true;
					}
				}
				if (choice2.isSelected()) {
					if (!choiceTwo.getText().equals(""))
						correctAnswers.add(choiceTwo.getText());
					else {
						bad = true;
					}
				}
				if (choice3.isSelected()) {
					if (!choiceThree.getText().equals(""))
						correctAnswers.add(choiceThree.getText());
					else {
						bad = true;
					}
				}
				if (choice4.isSelected()) {
					if (!choiceFour.getText().equals(""))
						correctAnswers.add(choiceFour.getText());
					else {
						bad = true;
					}
				}
				if (choice5.isSelected()) {
					if (!choiceFive.getText().equals(""))
						correctAnswers.add(choiceFive.getText());
					else {
						bad = true;
					}
				}

				if (correctAnswers.isEmpty() || bad) {
					popup.setLabel("Please select correct answers that have an answer given");
					popup.show(primaryStage);
				} else if (!isNewTopic && topicChosen == null) {
					popup.setLabel("Please pick a topic or enter new topic");
					popup.show(primaryStage);
				} else if ((String) topicComboBox.getValue() != null) {
					popup.setLabel("Please only pick a topic OR enter new topic");
					popup.show(primaryStage);
				} else if (questionText.equals("")) {
					popup.setLabel("Please enter text for a question");
					popup.show(primaryStage);
				} else if (answersEmpty) {
					popup.setLabel("Please enter at least two answers");
					popup.show(primaryStage);
				} else {

					if (isNewTopic) {
						newTopics.add(topicChosen);
						topics.add(topicChosen);
					}

					Question newQuestion = new Question(questionText);
					for (int k = 0; k < correctAnswers.size(); ++k)
						newQuestion.setCorrectAns(correctAnswers.get(k));
					for (int l = 0; l < answers.size(); ++l)
						newQuestion.setAllAns(answers.get(l));
					newQuestion.setTopic(topicChosen);
					if (!imageFile.getText().equals(""))
						newQuestion.setImage(imageView);

					allQuestions.add(newQuestion);
					if (allQuestions.contains(newQuestion)) {
						popup.setLabel("Question was added, press back to return home, or add a new question");
						popup.show(primaryStage);
					}
				}

			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

			subStart(primaryStage);

		});

		back.setOnAction(e -> mainClass.subStart(primaryStage));

		scene1 = new Scene(root, 1400, 864);
		primaryStage.setScene(scene1);

		scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.show();

	}

	public ObservableList<String> getTopics() {
		return newTopics;
	}

	public ArrayList<Question> getQuestions() {
		return allQuestions;
	}

	public void subStart(Stage primaryStage) {
		primaryStage.setScene(scene1);
		primaryStage.show();

	}

}
