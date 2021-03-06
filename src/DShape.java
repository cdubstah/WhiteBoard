import java.io.Serializable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DShape implements Serializable{
	DShapeModel shape;
	final static transient int KNOB_LENGTH = 9;

	public DShape() {
		shape = new DShapeModel();
		shape.setDefault();
	}
	
	DShapeModel getShapeModel() {
		return shape;
	}
	
	void draw(GraphicsContext gc) { }
	
	void drawSelected(GraphicsContext gc) { }
	
	void drawSelected(GraphicsContext gc, Color color ) { }
	
	void drawKnobs(GraphicsContext gc) {
		int shapeX = shape.getX();
		int shapeY = shape.getY();
		int shapeWidth = shape.getWidth();
		int shapeHeight = shape.getHeight();
		gc.fillRect(
				shapeX - KNOB_LENGTH / 2, shapeY - KNOB_LENGTH / 2, KNOB_LENGTH, 9);
		gc.fillRect(
				(shapeX + shapeWidth) - KNOB_LENGTH / 2, 
				shapeY - KNOB_LENGTH / 2, KNOB_LENGTH, 9);
		gc.fillRect(
				shapeX - KNOB_LENGTH / 2, 
				(shapeY + shapeHeight) - KNOB_LENGTH / 2, KNOB_LENGTH, 9);
		gc.fillRect(
				(shapeX + shapeWidth) - KNOB_LENGTH / 2, 
				(shapeY + shapeHeight) - KNOB_LENGTH / 2, KNOB_LENGTH, 9);
	}
	
	void move(int x, int y) {
		shape.move(x, y);
	}
	
	void setColor(Color color) {
		shape.setColor(color);
	}
	
	static int getKnobLength() {
		return KNOB_LENGTH;
	}
	
	int getX() {
		return shape.getX();
	}
	
	int getY() {
		return shape.getY();
	}
	
	int getWidth()  {
		return shape.getWidth();
	}
	
	int getHeight() {
		return shape.getHeight();
	}
	
	void setDShapeModel(int x, int y, int width, int height, Color color) {
		shape.setDShapeModel(x, y, width, height, color);
	}
	
	void setDShapeModel(int x, int y, int width, int height) {
		shape.setDShapeModel(x, y, width, height);
	}
	
	int getId() {
		return shape.getId();
	}
	
	void setId(int id) {
		shape.setId(id);
	}
	
	public DShapeModel getShape() {
		return shape;
	}

	public void setShape(DShapeModel shape) {
		this.shape = shape;
	}

	// for text
	void setText(String txt) { }
	void setWholeText(String txt) { }
	void setFont(Font font) { }
	public String getFontName() { return ""; }
	public void setFontName(String fontName) { }
	double getFontSize() { return shape.getFontSize(); }
	String getText() { return ""; }
	String getWholeText() { return ""; }
	Font getFont() { return Font.getDefault(); }

}
