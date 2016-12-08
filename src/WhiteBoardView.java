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
	DShape knobs[] = new DShape[4]; // 0 == TOP_LEFT, 1 == TOP_RIGHT, 2 == BOTTOM_LEFT, 3 == BOTTOM_RIGHT
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
							// draw all other shapes
							for (DShape s : shapes) {
								if (s != selected) {
									s.draw(gc);
								}
								// draw selected
								if(selected == s) {
									int xpos = selected.getX() - DShape.getKnobLength() / 2;
									int ypos = selected.getY() - DShape.getKnobLength() / 2;
									int xWidth = selected.getWidth();
									int yHeight = selected.getHeight();
									knobs[0] = new DShape();
									knobs[0].setDShapeModel(
											xpos, ypos, DShape.getKnobLength(), DShape.getKnobLength(), Color.BLACK);
									knobs[1] = new DShape();
									knobs[1].setDShapeModel(
											(xpos + xWidth), ypos,
											DShape.getKnobLength(), DShape.getKnobLength(), Color.BLACK);
									knobs[2] = new DShape();
									knobs[2].setDShapeModel(
											xpos, (ypos + yHeight),
											DShape.getKnobLength(), DShape.getKnobLength(), Color.BLACK);
									knobs[3] = new DShape();
									knobs[3].setDShapeModel(
											xpos + xWidth, ypos + yHeight,
											DShape.getKnobLength(), DShape.getKnobLength(), Color.BLACK);
									selected.drawSelected(gc);
									gc.setFill(prev);
								}
							}
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
				if(selected == null)
					return;
				// loop through knob array
				for(int i = 0; i < knobs.length; i++) {
					// if mouse location is over a knob
					int x = knobs[i].getX();
					if (x <= e.getX() && e.getX() <= x + DShape.getKnobLength()) {
						int y = knobs[i].getY();
						if (y <= e.getY() && y + DShape.getKnobLength() >= e.getY()) {
							System.out.println("i: " + i);
							// resize and return
							switch(i) {
							case 0:
								// resize top left
								break;
							case 1:
								// resize top right
								break;
							case 2:
								// resize bottom left
								break;
							case 3:
								// resize bottom right
								break;
							}
							return;
						}
					}
				}
				
				// otherwise drag
				// only drag if selected
				
				selected.move((int) e.getX() - selected.getWidth() / 2,(int) e.getY() - selected.getHeight() / 2);
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
					// draw selected
					if(selected == s)
						selected.drawSelected(gc);
					gc.setFill(prev);
				}
			}
		});

		// this is how we draw shapes
		// gc.setFill(Color.GREEN);
		// gc.strokeOval(20, 20, 20, 20);
		// gc.fillOval(20, 20, 20, 20);

		// DShape o = new DOval();
		// o.draw(gc);

		BorderPane bp = new BorderPane(); // BORDER PANE
		VBox controls = new VBox(20); // CONTROL VBOX
		

		// THIS SHAPE ADDITION BUTTONS //
		FlowPane shapeSelector = new FlowPane();
		shapeSelector.setVgap(10);
		shapeSelector.setHgap(10);
		
		
		Label addLabel = new Label("Add:");
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
		
		shapeSelector.getChildren().addAll(addLabel, rectangle, oval, line, text);
		// SHAPE SELECTING BUTTONS ABOVE //
		
		// MODIFICATION BUTTONS //
		FlowPane modifyButtons = new FlowPane();
		modifyButtons.setHgap(10);
		modifyButtons.setVgap(10);
		
		Label modifyLabel = new Label("Modify: ");
		
		cp = new ColorPicker(Color.BLUE);
		Button setColor = new Button("Set Color");
		setColor.setOnMouseReleased(e -> {
			// do nothing if no selected shape
			if(selected == null)
				return;
			// change selected color
			selected.setColor(cp.getValue());
			// clear board
			Paint prev = gc.getFill();
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, 400, 400);
			gc.setFill(prev);
			// draw all shapes
			for (DShape s : shapes) {
				if (s != selected) {
					s.draw(gc);
				}
				if(s == selected) {
					selected.drawSelected(gc, cp.getValue());
				}
				gc.setFill(prev);
			}
		});
		
		Button delete = new Button("Delete Selected");
		delete.setOnMouseReleased(e -> {
			// delete nothing if no selected shape
			if(selected == null)
				return;
			// remove selected
			shapes.remove(shapes.indexOf(selected));
			selected = null;
			// clear board
			Paint prev = gc.getFill();
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, 400, 400);
			gc.setFill(prev);
			// draw all shapes
			for (DShape s : shapes) {
				if (s != selected) {
					s.draw(gc);
				}
			}
		});
		
		Button reset = new Button("RESET");
		reset.setTextFill(Color.RED);
		reset.setOnMouseReleased(e -> {
			selected = null;
			shapes = new ArrayList<DShape>();
			gc.setFill(Color.WHITE);
			startDraw(primaryStage);
			
		});
		
		Button moveFront = new Button("Move to Front");
		moveFront.setOnMouseReleased(e -> {
			if(selected != null) {
				shapes.remove(shapes.indexOf(selected));
				shapes.add(selected);
			}
			// clear board
			Paint prev = gc.getFill();
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, 400, 400);
			gc.setFill(prev);
			// draw all shapes
			for (DShape s : shapes) {
				if (s != selected) {
					s.draw(gc);
				}
				if(s == selected) {
					selected.drawSelected(gc);
				}
			}
		});
		
		Button moveBack = new Button("Move to Back");
		moveBack.setOnMouseReleased(e -> {
			if(selected != null) {
				shapes.remove(shapes.indexOf(selected));
				shapes.add(0, selected);
			}
			// clear board
			Paint prev = gc.getFill();
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, 400, 400);
			gc.setFill(prev);
			// draw all shapes
			for (DShape s : shapes) {
				if (s != selected) {
					s.draw(gc);
				}
				if(s == selected) {
					selected.drawSelected(gc);
				}
			}
		});
		
		modifyButtons.getChildren().addAll(modifyLabel, cp, setColor, delete, reset, moveFront, moveBack);
		// MODIFICATION BUTTONS ABOVE //
		
		controls.getChildren().addAll(shapeSelector, modifyButtons);
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
	
	private void reDrawCanvas() {
		// clear board
		Paint prev = gc.getFill();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 400, 400);
		gc.setFill(prev);
		// draw all shapes
		for (DShape s : shapes) {
			if (s != selected) {
				s.draw(gc);
			}
			if(s == selected) {
				selected.drawSelected(gc, cp.getValue());
			}
		}
	}
}