import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WhiteBoardView extends Application {

	public void start(Stage primaryStage) throws Exception {
		
		Canvas canvas = new Canvas();        // CANVAS
		BorderPane bp = new BorderPane(); // BORDER PANE
		VBox controls = new VBox();        // CONTROL VBOX
		FlowPane shapeSelector = new FlowPane();
		
		// THIS SHAPE SELECTING BUTTONS //
		shapeSelector.setHgap(10);
		Label addLabel = new Label("Add");
		Button rectangle = new Button("Rectangle");
		Button oval = new Button("Oval");
		Button line = new Button("Line");
		Button text = new Button("Text");
		// SHAPE SELECTING BUTTONS ABOVE // 
		
		shapeSelector.getChildren().addAll(addLabel , rectangle, oval ,line, text);
		controls.getChildren().add(shapeSelector);
		bp.setRight(canvas);
		bp.setLeft(controls);
		Scene scene = new Scene(bp,800,400);
		primaryStage.setTitle("WHITEBOARD");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
	
	public static void main (String[] args) {
		launch(args);
	}
}
