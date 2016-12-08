import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DShape{
	DShapeModel shape;

	public DShape() {
		shape = new DRectModel();
		shape.setDefault();
	}
	
	DShapeModel getShapeModel() {
		return shape;
	}
	void draw(GraphicsContext gc) { }
	
	void drawSelected(GraphicsContext gc) { }
	
	void drawSelected(GraphicsContext gc, Color color ) { }
	
	void move(int x, int y) {
		shape.move(x, y);
		
	}
	
	void setColor(Color color) {
		shape.setColor(color);
	}
}
