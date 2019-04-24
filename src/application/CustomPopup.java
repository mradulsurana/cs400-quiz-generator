package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CustomPopup {
  private Popup popup = new Popup();
  private Label popupLabel = new Label();
  private Stage primaryStage = new Stage();
  
  public CustomPopup(Stage primaryStage) {
    this.primaryStage = primaryStage;
    setUp();
  }

  public static void main(String[] args) {
 
  }
  
  public void show() {
    popup.show(primaryStage);
  }
  
  public void setLabel(String label) {
    popupLabel.setText(label);
  }
  
  public void setUp() {
    
    VBox vbox = new VBox(); // create vbox to hold
    vbox.setId("popup");

    Button btnClose = new Button("Ok");
    popupLabel.setMinWidth(120);
    popupLabel.setMinHeight(200);
    // popup.getContent().add(btnClose);
    popup.getContent().add(vbox);
    vbox.getChildren().add(popupLabel);
    vbox.getChildren().add(btnClose);
    btnClose.setOnAction(f -> popup.hide());

    popupLabel.setStyle("-fx-padding: 0 20 0 20;");
    vbox.setAlignment(Pos.CENTER);
    VBox.setMargin(btnClose, new Insets(20));
    
    
  }
}
