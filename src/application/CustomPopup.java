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

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CustomPopup {
  private Alert popup;
  
  /**
   * alert of type "information"
   */
  public CustomPopup() {
	  popup = new Alert(AlertType.INFORMATION);
  }

  /**
   * shows the alert
   * @param primaryStage // not needed but class was altered after other classes used this class 
   */
  public void show(Stage primaryStage) {
    popup.show();
  }
  
  /** sets the message for the alert to print to the screen
   * 
   * @param label to display to user
   */
  public void setLabel(String label) {
	  popup.setHeaderText(label);
  }
}
