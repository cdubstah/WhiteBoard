
import javafx.scene.paint.Color;

public class DShapeModel {
	private int x, y, width, height;
	private Color color;

	public DShapeModel() {
		x = y = width = height = 0;
		color = Color.GRAY;
	}

	void setDefault() {
		x = y = 10;
		width = height = 20;
	}

	void setDShapeModel(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	void setDShapeModel(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	void move(int x, int y) {
		this.x = x;
		this.y = y;
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

}
