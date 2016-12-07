import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class WhiteBoardView extends Application {
	ArrayList<DShape> shapes = new ArrayList<DShape>();
	Canvas canvas;
	GraphicsContext gc;
	public void start(Stage primaryStage) throws Exception {
		// INSTEAD OF CANVAS CLASS WE just utilized canvas built into 
		Pane p = new Pane();
		p.setMinSize(400, 400);
		p.setStyle("-fx-background-color : white");
		canvas = new Canvas(400,400);// CANVAS
		p.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		
		// this is how we draw shapes
//		gc.setFill(Color.GREEN);
//		gc.strokeOval(20, 20, 20, 20);
//		gc.fillOval(20, 20, 20, 20);

//		DShape o = new DOval();
//		o.draw(gc);
		
		
		
		BorderPane bp = new BorderPane(); // BORDER PANE
		VBox controls = new VBox();        // CONTROL VBOX
		FlowPane shapeSelector = new FlowPane();
		
		// THIS SHAPE SELECTING BUTTONS //
		shapeSelector.setHgap(10);
		Label addLabel = new Label("Add");
		Button rectangle = new Button("Rectangle");
		rectangle.setOnMouseReleased(e -> {
			DShape s = new DRect();
			s.draw(gc);
			shapes.add(s);
		});
				

		Button oval = new Button("Oval");
		oval.setOnMouseReleased(e -> {
			DShape s = new DOval();
			s.draw(gc);
			shapes.add(s);
		});
		Button line = new Button("Line");
		Button text = new Button("Text");
		// SHAPE SELECTING BUTTONS ABOVE // 
		
		shapeSelector.getChildren().addAll(addLabel , rectangle, oval ,line, text);
		controls.getChildren().add(shapeSelector);
		bp.setRight(p);
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