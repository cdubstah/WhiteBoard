import javafx.scene.paint.Color;

public class DShapeModel {
	private int x, y, width, height;
	private Color color;

	public DShapeModel(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	void move(int x, int y, int width, int height) {
		this.x = x;
		this.y = x;
		this.width = width;
		this.height = height;
	}

	void changeColor(Color color) {
		this.color = color;
	}

	
}
