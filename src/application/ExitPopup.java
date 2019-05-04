package application;

import java.util.ArrayList;
import java.util.Optional;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

public class ExitPopup {
  private Popup popup;
  private ArrayList<Question> questions;
  
  public ExitPopup(ArrayList<Question> questions) {
	  this.popup = new Popup();
	  this.questions = questions;
  }

  public static void main(String[] args) {
 
  }
  
  //Shows the ExitPopup alert
  public void show(Stage primaryStage) {
    popup.show(primaryStage);
  }
  
  //Takes the questions passed in, and creates a JSONArray from the question info
  private JSONObject createJSON(ArrayList<Question> questions) {
	JSONObject resultsObj = new JSONObject();
	
	JSONArray questionArray = new JSONArray();
	
	//Loops through the questions to create a JSONObject for each question
	for (int i=0; i < questions.size(); i++) {
		Question question = questions.get(i);
		
		System.out.println(question.getCorrectAns().get(0));
		
		JSONObject questionData = new JSONObject();
		
		questionData.put("meta-data",question.getMetaData());
		questionData.put("questionText",question.getQuestion());
		questionData.put("topic",question.getTopic());
		
		if (question.getImage() == null) {
			questionData.put("image","none");
		} else {
			questionData.put("image",question.getImageName());
		}
		
		JSONArray choicesArray = new JSONArray();
		ArrayList<String> choices = question.getAllAns();
		ArrayList<String> correctChoices = question.getCorrectAns();
		for (int j=0; j < choices.size(); j++) {
			JSONObject choice = new JSONObject();
			
			//Figures out if the current choice is one of the correct choices
			boolean isCorrect = false;
			for (int k=0; k < correctChoices.size(); k++) {
				if (choices.get(j).equals(correctChoices.get(k))) {
					isCorrect = true;
				}
			}
			if (isCorrect) {
				choice.put("isCorrect","T");
			} else {
				choice.put("isCorrect","F");
			}
			
			choice.put("choice",choices.get(j));
			
			choicesArray.add(choice);
		}
		
		questionData.put("choiceArray",choicesArray);
		questionArray.add(questionData);
	}
	
	resultsObj.put("questionArray", questionArray);
	
	return resultsObj;
  }
  
  public void start(Stage primaryStage) {
    Alert exitPopup = new Alert(AlertType.CONFIRMATION);
    exitPopup.setTitle("Quiz Generator");
    exitPopup.setHeaderText("Do you want to save your results?");
    
    //Creates the buttons that goes on the alert
    ButtonType btnClose = new ButtonType("Exit without saving");
    ButtonType btnSave = new ButtonType("Save and exit");
    ButtonType btnCancel = new ButtonType("Cancel");

    //Remove the default ButtonTypes
    exitPopup.getButtonTypes().clear();  
    
    //Add the new custom buttons
    exitPopup.getButtonTypes().addAll(btnSave, btnClose, btnCancel);
    
    //Brings up the alert that the player gets when they press "Exit", with different options
    Optional<ButtonType> option = exitPopup.showAndWait();
    if (option.get() == null) {
    	
    /* 
     * The event that happens if the player presses "Exit without Saving"
     * Gives the player a goodbye message and then closes Quiz Generator
    */
    } else if (option.get() == btnClose) {
    	Alert popup = new Alert(AlertType.INFORMATION);
		popup.setHeaderText("Goodbye, have a great day!");
		
		Optional<ButtonType> result = popup.showAndWait();
		if(!result.isPresent()) {
			//Platform.exit(); //alert is exited via the x, no button was pressed
		} else if(result.get() == ButtonType.OK) {
			Platform.exit();
    	} else if(result.get() == ButtonType.CANCEL) {
		    //cancel button is pressed (unused)
    	}
    	
	/* 
	 * The event that happens if the player presses "Save and Exit"
	 * Creates a JSON file from the quiz questions and prompts the user to save the files somewhere.
	 * Closes Quiz Generator after saving
	 */
    } else if (option.get() == btnSave) {
    	Stage s = new Stage(); // stage for file explorer
    	
    	//Sets up the file explorer for the user to save the JSON file
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Results");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName("quiz_results.json");
        FileChooser.ExtensionFilter jsonExtensionFilter = 
        		new FileChooser.ExtensionFilter("JSON Format (.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonExtensionFilter);
        fileChooser.setSelectedExtensionFilter(jsonExtensionFilter);
        File userFile = fileChooser.showSaveDialog(s);
        
        if (userFile != null) {
	        try {
				//Create the JSON object
				JSONObject resultsList = createJSON(this.questions);
				
				//Write the JSON object to the JSON file
				FileWriter file = new FileWriter(userFile);
	            file.write(resultsList.toJSONString());
				file.flush();
				
				/*
				 * Bring up an alert that notifies their file successfully saved
				 * and then lets them exit the program by clicking OK.
				 */
				Alert popup = new Alert(AlertType.INFORMATION);
				popup.setHeaderText("Successfully saved to " + userFile.getPath() + "!");
				popup.setContentText("Click OK to finish. Goodbye!");
				Optional<ButtonType> result = popup.showAndWait();
				if(!result.isPresent()) {
					//Platform.exit(); //alert is exited via the x, no button was pressed
				} else if(result.get() == ButtonType.OK) {
					Platform.exit();
	        	} else if(result.get() == ButtonType.CANCEL) {
				    //cancel button is pressed (unused)
	        	}
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        }
    	
    /* 
     * The event that happens if the player presses "Cancel"
     * Closes the alert
     */
    } else if (option.get() == btnCancel) {
    	System.out.println(""); //Print needs to be here, else cancel button doesn't work
    }
 
  }
}
