import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class WhiteBoardView extends Application {
	ArrayList<DShape> shapes = new ArrayList<DShape>();
	Canvas canvas;
	GraphicsContext gc;
	DShape selected;
	ColorPicker cp;

	public void start(Stage primaryStage) throws Exception {
		startDraw(primaryStage);
		
	}
	
	public void startDraw(Stage primaryStage) {
		Pane p = new Pane();
		p.setMinSize(400, 400);
		p.setStyle("-fx-background-color : white");
		canvas = new Canvas(400, 400);// CANVAS
		
		p.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		// handles selection of shape
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double x = e.getX();
				double y = e.getY();
				
				for (int i = shapes.size() - 1; i >= 0; i--) {
					double shapeX = shapes.get(i).getShapeModel().getX();
					double shapeWidth = shapes.get(i).getShapeModel().getWidth();
					if (shapeX <= x && x <= shapeX + shapeWidth) {
						double shapeY = shapes.get(i).getShapeModel().getY();
						double shapeHeight = shapes.get(i).getShapeModel().getHeight();
						if (shapeY <= y && shapeY + shapeHeight >= y) {
							// clear board
							Paint prev = gc.getFill();
							gc.setFill(Color.WHITE);
							gc.fillRect(0, 0, 400, 400);
							gc.setFill(prev);
							selected = shapes.get(i);
							// make selected on top
							shapes.remove(i);
							shapes.add(selected);
							// draw all other shapes
							for (DShape s : shapes) {
								if (s != selected) {
									s.draw(gc);
								}
							}
							// draw selected
							if(selected != null)
								selected.drawSelected(gc, cp.getValue());
							break;
						}
					}
				}
			}
		});
		
		// handles shape movement
		canvas.addEventHandler(MouseDragEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				// only drag if selected
				if(selected == null)
					return;
				selected.move((int) e.getX(),(int) e.getY());
				// clear board
				Paint prev = gc.getFill();
				gc.setFill(Color.WHITE);
				gc.fillRect(0, 0, 400, 400);
				gc.setFill(prev);
				// draw all other shapes
				for (DShape s : shapes) {
					if (s != selected) {
						s.draw(gc);
					}
				}
				// draw selected
				if(selected != null)
					selected.drawSelected(gc, cp.getValue());
			}
		});

		// this is how we draw shapes
		// gc.setFill(Color.GREEN);
		// gc.strokeOval(20, 20, 20, 20);
		// gc.fillOval(20, 20, 20, 20);

		// DShape o = new DOval();
		// o.draw(gc);

		BorderPane bp = new BorderPane(); // BORDER PANE
		VBox controls = new VBox(); // CONTROL VBOX
		FlowPane shapeSelector = new FlowPane();
		shapeSelector.setVgap(10);
		cp = new ColorPicker(Color.BLUE);
		

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
		
		Button reset = new Button("RESET");
		reset.setOnMouseReleased(e -> {
			shapes = new ArrayList<DShape>();
			startDraw(primaryStage);
			
		});
		shapeSelector.getChildren().addAll(addLabel, rectangle, oval, line, text, cp, reset);
		controls.getChildren().add(shapeSelector);
		bp.setRight(p);
		bp.setLeft(controls);
		Scene scene = new Scene(bp, 800, 400);
		primaryStage.setTitle("WHITEBOARD");
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	public static void main(String[] args) {
		launch(args);
	}
}