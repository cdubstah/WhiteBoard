
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DShapeModel {
	private SimpleIntegerProperty xProp, yProp, widthProp, heightProp;
	private int x, y, width, height;
	private Color color;

	public DShapeModel() {
		x = y = width = height = 0;
		xProp = new SimpleIntegerProperty(x);
		yProp = new SimpleIntegerProperty(y);
		widthProp = new SimpleIntegerProperty(width);
		heightProp = new SimpleIntegerProperty(height);
		color = Color.GRAY;
	}

	void setDefault() {
		x = y = 10;
		xProp.setValue(10);
		yProp.setValue(10);
		width = height = 20;
		widthProp.setValue(20);
		heightProp.setValue(20);
	}

	void setDShapeModel(int x, int y, int width, int height, Color color) {
		this.x = x;
		xProp.setValue(x);
		this.y = y;
		yProp.setValue(y);
		this.width = Math.abs(width);
		widthProp.setValue(Math.abs(width));
		this.height = Math.abs(height);
		heightProp.setValue(Math.abs(height));
		this.color = color;
	}
	
	void setDShapeModel(int x, int y, int width, int height) {
		this.x = x;
		xProp.setValue(x);
		this.y = y;
		yProp.setValue(y);
		this.width = width;
		widthProp.setValue(width);
		this.height = height;
		heightProp.setValue(height);
	}

	void move(int x, int y) {
		this.x = x;
		xProp.setValue(x);
		this.y = y;
		yProp.setValue(y);
	}
	
	void setColor(Color color) {
		this.color = color;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	int getWidth() {
		return width;
	}

	int getHeight() {
		return height;
	}

	Color getColor() {
		return color;
	}
	
	// for text
	void setText(String txt) { }
	void setWholeText(String txt) { }
	void setFont(Font font) { }
	void setFontSize(double size) { }
	String getText() { return ""; }
	String getWholeText() { return ""; }
	Font getFont() { return null; }
	void computeFont() { };
}
