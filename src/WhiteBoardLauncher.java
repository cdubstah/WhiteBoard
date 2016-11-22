import javafx.application.Application;
import javafx.stage.Stage;

public class WhiteBoardLauncher extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		WhiteBoardView app = new WhiteBoardView();
		app.start(primaryStage); // To change body of generated methods, choose
									// Tools | Templates.
	}

}
