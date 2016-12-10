import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WhiteBoardView extends Application {
	ArrayList<DShape> shapes = new ArrayList<DShape>();
	ObservableList<DShapeModel> tableData = FXCollections.observableArrayList();
	TableView<DShapeModel> table;
	DShape knobs[] = new DShape[4]; // 0 == TOP_LEFT, 1 == TOP_RIGHT, 2 ==
									// BOTTOM_LEFT, 3 == BOTTOM_RIGHT
	DShape lineKnobs[] = new DShape[2];
	Canvas canvas;
	GraphicsContext gc;
	DShape selected;
	ColorPicker cp;
	boolean isResizing = false, isDragging = false;
	int currentKnob;
	
	public void start(Stage primaryStage) throws Exception {
		startDraw(primaryStage);
	}

	public void startDraw(Stage primaryStage) {
		Pane p = new Pane();
		p.setMinSize(1920, 1080);
		p.setStyle("-fx-background-color : white");
		canvas = new Canvas(1920, 1080);// CANVAS

		p.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		// handles selection of shape
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				double x = e.getX();
				double y = e.getY();
				boolean isShape = false;
				for (int i = shapes.size() - 1; i >= 0; i--) {
					double shapeX = shapes.get(i).getX();
					double shapeWidth = shapes.get(i).getWidth();
					if (shapeX <= x && x <= shapeX + shapeWidth) {
						double shapeY = shapes.get(i).getShapeModel().getY();
						double shapeHeight = shapes.get(i).getHeight();
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
								if (selected == s) {
									if (selected instanceof DLine) {
										//System.out.println("I AM A DLINE");
										isShape = true;
										int xpos = selected.getX() - DShape.getKnobLength() / 2;
										int ypos = selected.getY() - DShape.getKnobLength() / 2;
										int xWidth = selected.getWidth();
										int yHeight = selected.getHeight();
										lineKnobs = new DShape[2];
										lineKnobs[0] = new DShape();
										lineKnobs[0].setDShapeModel(xpos, ypos, DShape.getKnobLength(),
												DShape.getKnobLength(), Color.BLACK);
										lineKnobs[1] = new DShape();
										lineKnobs[1].setDShapeModel(xpos + xWidth, ypos + yHeight, DShape.getKnobLength(),
												DShape.getKnobLength(), Color.BLACK);
										selected.drawSelected(gc);
										gc.setFill(prev);
									} else {
										isShape = true;
										int xpos = selected.getX() - DShape.getKnobLength() / 2;
										int ypos = selected.getY() - DShape.getKnobLength() / 2;
										int xWidth = selected.getWidth();
										int yHeight = selected.getHeight();
										knobs = new DShape[4];
										knobs[0] = new DShape();
										knobs[0].setDShapeModel(xpos, ypos, DShape.getKnobLength(),
												DShape.getKnobLength(), Color.BLACK);
										knobs[1] = new DShape();
										knobs[1].setDShapeModel((xpos + xWidth), ypos, DShape.getKnobLength(),
												DShape.getKnobLength(), Color.BLACK);
										knobs[2] = new DShape();
										knobs[2].setDShapeModel(xpos, (ypos + yHeight), DShape.getKnobLength(),
												DShape.getKnobLength(), Color.BLACK);
										knobs[3] = new DShape();
										knobs[3].setDShapeModel(xpos + xWidth, ypos + yHeight, DShape.getKnobLength(),
												DShape.getKnobLength(), Color.BLACK);
										selected.drawSelected(gc);
										gc.setFill(prev);
									}
								}
							}
							break;
						}
					}
				}
				if (!isShape) {
					if(selected instanceof DLine) {
						for (int i = 0; i < lineKnobs.length; i++) {
							// if mouse location is over a knob
							int x1 = lineKnobs[i].getX();
							if (x1 <= e.getX() && e.getX() <= x1 + DShape.getKnobLength()) {
								int y1 = lineKnobs[i].getY();
								if (y1 <= e.getY() && y1 + DShape.getKnobLength() >= e.getY()) {
									// System.out.println("Knob Detected: Will
									// not de-select");
									return;
								}
							}
						}
					} else if (knobs[0] != null) {
						for (int i = 0; i < knobs.length; i++) {
							// if mouse location is over a knob
							int x1 = knobs[i].getX();
							if (x1 <= e.getX() && e.getX() <= x1 + DShape.getKnobLength()) {
								int y1 = knobs[i].getY();
								if (y1 <= e.getY() && y1 + DShape.getKnobLength() >= e.getY()) {
									// System.out.println("Knob Detected: Will
									// not de-select");
									return;
								}
							}
						}
					}
					selected = null;
					redraw();
				}

			}
		});

		// handles shape movement and resizing
		canvas.addEventHandler(MouseDragEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (selected == null)
					return;
				// loop through knob array
				if (!isDragging) {
					if (isResizing) {
						if(selected instanceof DLine) {
							int oldX = selected.getX();
							int oldY = selected.getY();
							int newX = (int) e.getX();
							int newY = (int) e.getY();
							switch(currentKnob) {
							case 0:
								// resize top left
								lineKnobs[0].move(newX - lineKnobs[0].getWidth() / 2, newY - lineKnobs[0].getHeight() / 2);
								selected.setDShapeModel(newX, newY, selected.getWidth() + (oldX - newX),
										selected.getHeight() + (oldY - newY));
								break;
							case 1:
								// resize bottom right
								lineKnobs[1].move(newX - lineKnobs[1].getWidth() / 2, newY - lineKnobs[1].getHeight() / 2);
								int newHeight3 = selected.getHeight() + (newY - (oldY + selected.getHeight()));
								selected.setDShapeModel(oldX, oldY,
										selected.getWidth() + (newX - (oldX + selected.getWidth())), newHeight3);
								break;
							}
						} else {
							int oldX = selected.getX();
							int oldY = selected.getY();
							int newX = (int) e.getX();
							int newY = (int) e.getY();
							switch (currentKnob) {
							case 0:
								// resize top left
								int newWidth = selected.getWidth();
								int newHeight = selected.getHeight();
								if (newWidth >= 0 && newHeight >= 0) {
									knobs[0].move(newX - knobs[0].getWidth() / 2, newY - knobs[0].getHeight() / 2);
									// update 1
									knobs[1].move(knobs[1].getX(), newY - knobs[1].getHeight() / 2);
									// update 2
									
									knobs[2].move(newX - knobs[2].getWidth() / 2, knobs[2].getY());
									selected.setDShapeModel(newX, newY, selected.getWidth() + (oldX - newX),
											selected.getHeight() + (oldY - newY));
								} else if (newWidth < 0) {
									DShape temp = knobs[0];
									knobs[0] = knobs[1];
									knobs[1] = temp;
									currentKnob = 1;
								} else {
									DShape temp = knobs[0];
									knobs[0] = knobs[2];
									knobs[2] = temp;
									currentKnob = 2;
								}
								break;
							case 1:
								// resize top right
								knobs[1].move(newX - knobs[1].getWidth() / 2, newY - knobs[1].getHeight() / 2);
								// update 0
								knobs[0].move(knobs[0].getX(), newY - knobs[0].getHeight() / 2);
								// update 3
								knobs[3].move(newX - knobs[3].getHeight() / 3, knobs[3].getY());
								int newHeight1 = selected.getHeight();
								int newWidth1 = selected.getWidth() + (newX - (oldX + selected.getWidth()));
								if (newHeight1 >= 0 && newWidth1 >= 0) {
									selected.setDShapeModel(oldX, newY,
											selected.getWidth() + (newX - (oldX + selected.getWidth())),
											selected.getHeight() + (oldY - newY));
								} else if (newHeight1 < 0) {
									DShape temp = knobs[1];
									knobs[1] = knobs[3];
									knobs[3] = temp;
									currentKnob = 3;
								} else {
									DShape temp = knobs[1];
									knobs[1] = knobs[0];
									knobs[0] = temp;
									currentKnob = 0;
								}
								break;
							case 2:
								// resize bottom left
								knobs[2].move(newX - knobs[2].getWidth() / 2, newY - knobs[2].getHeight() / 2);
								// update 0
								knobs[0].move(newX - knobs[0].getWidth() / 2, knobs[0].getY());
								// update 3
								knobs[3].move(knobs[3].getX(), newY - knobs[3].getHeight() / 2);
								int newHeight2 = selected.getHeight() + (newY - (oldY + selected.getHeight()));
								int newWidth2 = selected.getWidth();
								if (newHeight2 >= 0 && newWidth2 >= 0) {
									selected.setDShapeModel(newX, oldY, selected.getWidth() + (oldX - newX),
											selected.getHeight() + (newY - (oldY + selected.getHeight())));
								} else if (newHeight2 < 0) {
									DShape temp = knobs[2];
									knobs[2] = knobs[0];
									knobs[0] = temp;
									currentKnob = 0;
								} else {
									DShape temp = knobs[2];
									knobs[2] = knobs[3];
									knobs[3] = temp;
									currentKnob = 3;
								}
								break;
							case 3:
								// resize bottom right
								knobs[3].move(newX - knobs[3].getWidth() / 2, newY - knobs[3].getHeight() / 2);
								// update 1
								knobs[1].move(newX - knobs[1].getWidth() / 2, knobs[1].getY());
								// update 2
								knobs[2].move(knobs[2].getX(), newY - knobs[2].getHeight() / 2);
								int newHeight3 = selected.getHeight() + (newY - (oldY + selected.getHeight()));
								int newWidth3 = selected.getWidth() + (newX - (oldX + selected.getWidth()));
								if (newHeight3 >= 0 && newWidth3 >= 0) {
									selected.setDShapeModel(oldX, oldY,
											selected.getWidth() + (newX - (oldX + selected.getWidth())), newHeight3);
								} else if (newHeight3 < 0) {
									DShape temp = knobs[3];
									knobs[3] = knobs[1];
									knobs[1] = temp;
									currentKnob = 1;
								} else {
									DShape temp = knobs[3];
									knobs[3] = knobs[2];
									knobs[2] = temp;
									currentKnob = 2;
								}
								break;
							}
						}
						// clear board
						Paint prev = gc.getFill();
						redrawPrev(prev);
						return;
					}
					
					// which knob?
					if(selected instanceof DLine) {
						for (int i = 0; i < lineKnobs.length; i++) {
							// if mouse location is over a knob
							int x = lineKnobs[i].getX();
							if (x <= e.getX() && e.getX() <= x + DShape.getKnobLength()) {
								int y = lineKnobs[i].getY();
								if (y <= e.getY() && y + DShape.getKnobLength() >= e.getY()) {
									isResizing = true;
									// resize and return
									switch (i) {
									case 0:
										currentKnob = 0;
										break;
									case 1:
										currentKnob = 1;
										break;
									}
									// clear board
									Paint prev = gc.getFill();
									redrawPrev(prev);
									return;
								}
							}
						}
					} else {
						for (int i = 0; i < knobs.length; i++) {
							// if mouse location is over a knob
							int x = knobs[i].getX();
							if (x <= e.getX() && e.getX() <= x + DShape.getKnobLength()) {
								int y = knobs[i].getY();
								if (y <= e.getY() && y + DShape.getKnobLength() >= e.getY()) {
									isResizing = true;
									// resize and return
									switch (i) {
									case 0:
										// top left knob
										currentKnob = 0;
										break;
									case 1:
										// top right knob
										currentKnob = 1;
										break;
									case 2:
										// bottom left knob
										currentKnob = 2;
										break;
									case 3:
										// bottom right knob
										currentKnob = 3;
										break;
									}
									// clear board
									Paint prev = gc.getFill();
									redrawPrev(prev);
									return;
								}
							}
						}
					}
				}

				// otherwise drag
				// only drag if selected
				if (!isResizing) {
					isDragging = true;
					int newX = (int) e.getX();
					int newY = (int) e.getY();
					selected.move(newX - selected.getWidth() / 2, newY - selected.getHeight() / 2);
					// relocate knobs
					int xpos = selected.getX() - DShape.getKnobLength() / 2;
					int ypos = selected.getY() - DShape.getKnobLength() / 2;
					int xWidth = selected.getWidth();
					int yHeight = selected.getHeight();
					knobs[0] = new DShape();
					knobs[0].setDShapeModel(xpos, ypos, DShape.getKnobLength(), DShape.getKnobLength(), Color.BLACK);
					knobs[1] = new DShape();
					knobs[1].setDShapeModel((xpos + xWidth), ypos, DShape.getKnobLength(), DShape.getKnobLength(),
							Color.BLACK);
					knobs[2] = new DShape();
					knobs[2].setDShapeModel(xpos, (ypos + yHeight), DShape.getKnobLength(), DShape.getKnobLength(),
							Color.BLACK);
					knobs[3] = new DShape();
					knobs[3].setDShapeModel(xpos + xWidth, ypos + yHeight, DShape.getKnobLength(),
							DShape.getKnobLength(), Color.BLACK);
					// clear board
					Paint prev = gc.getFill();
					redrawPrev(prev);
				}
			}
		});

		canvas.addEventHandler(MouseDragEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				isDragging = isResizing = false;
				currentKnob = -1;
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
			tableData.add(s.getShapeModel());
			table.setItems(tableData);
			table.refresh();
		});

		Button oval = new Button("Oval");
		oval.setOnMouseReleased(e -> {
			DShape s = new DOval();
			s.draw(gc);
			shapes.add(s);
			tableData.add(s.getShapeModel());
		});

		Button line = new Button("Line");
		line.setOnMouseReleased(e -> {
			DShape s = new DLine();
			s.draw(gc);
			shapes.add(s);
			tableData.add(s.getShapeModel());
		});
		Button text = new Button("Text");
		text.setOnMouseReleased(e -> {
			DShape s = new DText();
			s.draw(gc);
			shapes.add(s);
			tableData.add(s.getShapeModel());
		});

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
			if (selected == null)
				return;
			// change selected color
			selected.setColor(cp.getValue());
			// clear board
			Paint prev = gc.getFill();
			redrawPrevColor(prev);
		});

		Button delete = new Button("Delete Selected");
		delete.setOnMouseReleased(e -> {
			// delete nothing if no selected shape
			if (selected == null)
				return;
			// remove selected
			shapes.remove(shapes.indexOf(selected));
			tableData.remove(tableData.indexOf(selected.getShapeModel()));
			selected = null;
			// clear board
			redraw();
		});

		Button reset = new Button("RESET");
		reset.setTextFill(Color.RED);
		reset.setOnMouseReleased(e -> {
			selected = null;
			shapes = new ArrayList<DShape>();
			gc.setFill(Color.WHITE);
			tableData = FXCollections.observableArrayList();
			table.refresh();
			startDraw(primaryStage);
		});

		Button moveFront = new Button("Move to Front");
		moveFront.setOnMouseReleased(e -> {
			if (selected != null) {
				shapes.remove(shapes.indexOf(selected));
				tableData.remove(tableData.indexOf(selected.getShapeModel()));
				shapes.add(selected);
				tableData.add(selected.getShapeModel());
			}
			// clear board
			redraw();
		});

		Button moveBack = new Button("Move to Back");
		moveBack.setOnMouseReleased(e -> {
			if (selected != null) {
				shapes.remove(shapes.indexOf(selected));
				tableData.remove(tableData.indexOf(selected.getShapeModel()));
				shapes.add(0, selected);
				tableData.add(0, selected.getShapeModel());
			}
			redraw();
		});
		
		modifyButtons.getChildren().addAll(modifyLabel, cp, setColor, delete, reset, moveFront, moveBack);
		// MODIFICATION BUTTONS ABOVE //

		// TEXT MODIFICATION BUTTONS //
		FlowPane textButtons = new FlowPane();
		textButtons.setHgap(10);
		textButtons.setVgap(10);

		TextField textField = new TextField("Hello");
		
		ObservableList<String> allFonts = FXCollections.observableList(Font.getFamilies());
		ComboBox<String> fontMenu = new ComboBox<String>(allFonts);
		fontMenu.setValue("Dialog");
		
		Button setText = new Button("Set Text");
		setText.setOnMouseReleased(e -> {
			if (selected == null)
				return;
			selected.setText(textField.getText());
			selected.setWholeText(textField.getText());
			selected.setFont(Font.font(fontMenu.getValue()));
			redraw();
		});

		textButtons.getChildren().addAll(textField, fontMenu, setText);
		// TEXT MODIFICATION BUTTONS ABOVE //
		
		// TABLE //
		table = new TableView<DShapeModel>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setMaxHeight(195);
		
		TableColumn<DShapeModel, String> xPosCol = new TableColumn<DShapeModel, String>("X");
		xPosCol.setCellValueFactory(new PropertyValueFactory<DShapeModel, String>("x"));
		
		TableColumn<DShapeModel, Integer> yPosCol = new TableColumn<>("Y");
		yPosCol.setCellValueFactory(new PropertyValueFactory<>("y"));
		
		TableColumn<DShapeModel, Integer> widthCol = new TableColumn<>("Width");
		widthCol.setCellValueFactory(new PropertyValueFactory<>("width"));
		
		TableColumn<DShapeModel, Integer> heightCol = new TableColumn<>("Height");
		heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
		table.getColumns().addAll(xPosCol, yPosCol, widthCol, heightCol);
		table.setItems(tableData);
		// TABLE ABOVE //

		controls.getChildren().addAll(shapeSelector, modifyButtons, textButtons, table);
		bp.setRight(p);
		bp.setLeft(controls);
		Scene scene = new Scene(bp, 800, 400);
		primaryStage.setTitle("WHITEBOARD");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void redraw() {

		// clear board
		Paint prev = gc.getFill();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 1920, 1080);
		gc.setFill(prev);
		// draw all shapes
		for (DShape s : shapes) {
			if (s != selected) {
				s.draw(gc);
			}
			if (s == selected) {
				selected.drawSelected(gc);
			}
		}
		table.refresh();
	}
	
	
	public void redrawPrevColor(Paint prev) {

		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 1920, 1080);
		gc.setFill(prev);
		// draw all shapes
		for (DShape s : shapes) {
			if (s != selected) {
				s.draw(gc);
			}
			if (s == selected) {
				selected.drawSelected(gc, cp.getValue());
			}
			gc.setFill(prev);
		}
		table.refresh();
	}

	public void redrawPrev(Paint prev) {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 1920, 1080);
		gc.setFill(prev);
		// draw all other shapes
		for (DShape s : shapes) {
			if (s != selected) {
				s.draw(gc);
			}
			// draw selected
			if (selected == s)
				selected.drawSelected(gc);
			gc.setFill(prev);
		}
		table.refresh();
	}

	public static void main(String[] args) {
		launch(args);
	}
}