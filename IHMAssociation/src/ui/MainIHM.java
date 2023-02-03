package ui;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Classe principale de l'IHM.
 */
public class MainIHM extends Application {
  
  @Override
  public void start(Stage primaryStage) {
    try {
      final URL url = getClass().getResource("association.fxml");
      final FXMLLoader fxmlLoader = new FXMLLoader(url);
      final GridPane root = (GridPane) fxmlLoader.load();
      final Scene scene = new Scene(root, 1000, 750);
      primaryStage.setScene(scene);
      primaryStage.setResizable(true);
    } catch (IOException ex) {
      System.err.println("Erreur au chargement: " + ex);
    }
    primaryStage.setTitle("Association");
    primaryStage.show();
  }
  
  /**
   * Méthode principale de l'IHM.
   *
   * @param args not used
   */
  public static void main(String[] args) {
    launch(args);
  }
}