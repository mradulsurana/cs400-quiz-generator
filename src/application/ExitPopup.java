package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.File;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ExitPopup {
  private Popup popup = new Popup();
  private Label popupLabel = new Label();
  
  public ExitPopup(Stage primaryStage) {
    setUp(primaryStage);
  }

  public static void main(String[] args) {
 
  }
  
  public void show(Stage primaryStage) {
    popup.show(primaryStage);
  }
  
  public void setLabel(String label) {
    
  }
  
  public void setUp(Stage primaryStage) {
    
    VBox vbox = new VBox(); // create vbox to hold
    vbox.setId("popup");

    Button btnClose = new Button("Exit without saving");
    Button btnSave = new Button("Save");
    
    popupLabel.setMinWidth(120);
    popupLabel.setMinHeight(200);
    
    popupLabel.setText("Do you want to save your results?");
    
    // popup.getContent().add(btnClose);
    popup.getContent().add(vbox);
    vbox.getChildren().add(popupLabel);
    vbox.getChildren().add(btnClose);
    btnClose.setOnAction(f -> {
    	//popup.hide();
    	
    	CustomPopup pop = new CustomPopup();
		pop.setLabel("Goodbye, have a great day!");
		pop.show(primaryStage);
		
		//some kinda wait here
    	//Platform.exit();
  	});
    
    vbox.getChildren().add(btnSave); // the event for the save button
    btnSave.setOnAction(f -> {
    	Stage s = new Stage(); // stage for file explorer
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Results");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName("quiz_results.json");
        FileChooser.ExtensionFilter jsonExtensionFilter =
                new FileChooser.ExtensionFilter("JSON Format (.json)", "*.json");
        fileChooser.getExtensionFilters().add(jsonExtensionFilter);
        fileChooser.setSelectedExtensionFilter(jsonExtensionFilter);
        File userFile = fileChooser.showSaveDialog(s);
        
//        //First
//        JSONObject resultsDetails = new JSONObject();
//        resultsDetails.put("result1", "0");
//        resultsDetails.put("result2", "0");
//        resultsDetails.put("result3", "0");
//         
//        JSONObject resultsObject = new JSONObject();
//        resultsObject.put("result 1", resultsDetails);
//         
//        //Second
//        JSONObject resultsDetails2 = new JSONObject();
//        resultsDetails2.put("result1", "0");
//        resultsDetails2.put("result2", "0");
//        resultsDetails2.put("result3", "0");
//         
//        JSONObject resultsObject2 = new JSONObject();
//        resultsObject2.put("result 2", resultsDetails2);
//         
//        //Add results to list
//        JSONArray resultsList = new JSONArray();
//        resultsList.add(resultsObject);
//        resultsList.add(resultsObject2);
         
        if (userFile != null) {
	        //Write JSON file
	        try {// (FileWriter file = new FileWriter("employees.json")) {
	            //file.write(resultsList.toJSONString());
	            //file.flush();
	 
	            CustomPopup pop = new CustomPopup();
				pop.setLabel("Successfully saved to " + userFile.getPath() + "! Closing...");
				pop.show(primaryStage);
				
				//some kinda wait here
		    	//Platform.exit();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        }
        
        
        
//        if (userFile != null) {
//            try {
//            	BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//    			JSONObject data = Dungeon.writeToJSON(this.dungeon);
//    			JSON.write(writer, data);
//    			writer.flush();
//    			writer.close();

//            	CustomPopup pop = new CustomPopup();
//    			pop.setLabel("Successfully saved to " + userFile.getPath() + "! Closing...");
//    			pop.show(primaryStage);
//    			
//    			//some kinda wait here
//    	    	//Platform.exit();
//            	
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    	
  	});

    popupLabel.setStyle("-fx-padding: 0 20 0 20;");
    vbox.setAlignment(Pos.CENTER);
    VBox.setMargin(btnClose, new Insets(30));
    VBox.setMargin(btnSave, new Insets(10));
    
    popupLabel.setId("popupLabel");
    
    
  }
}
