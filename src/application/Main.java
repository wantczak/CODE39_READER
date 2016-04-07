package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/code39/view/GuiView.fxml"));
			Scene scene = new Scene(root,700,450);
			scene.getStylesheets().add(getClass().getResource("/code39/view/application.css").toExternalForm());
			primaryStage.setTitle("PRP - Program do odczytu CODE39");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
