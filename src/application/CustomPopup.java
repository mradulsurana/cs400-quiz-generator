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
  
  public CustomPopup() {
	  popup = new Alert(AlertType.INFORMATION);
  }

  public void show(Stage primaryStage) {
    popup.show();
  }
  
  public void setLabel(String label) {
	  popup.setHeaderText(label);
  }
}
