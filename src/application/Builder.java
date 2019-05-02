package application;

/**
 * This is an interface for classes that use a BorderPane object
 * in a JavaFX application to use in their Scene
 * @author Allen, Noah, Jordan, Michael, Mradul
 *
 */
public interface Builder {
  
  /**
   * Build and set node for bottom pane
   */
  void buildBottom();
  
  /**
   * Build and set node for center pane
   */
  void buildCenter();
  
  /**
   * Build and set node for top pane
   */
  void buildTop();
  
  /**
   * Build and set node for left pane
   */
  void buildLeft();
  
  /**
   * Build and set node for right pane
   */
  void buildRight();
}
