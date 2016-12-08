import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DOval extends DShape {
	public DOval() {
		super();
	}

	void draw(GraphicsContext gc) {
		gc.strokeText("", shape.getX() + shape.getWidth() / 2, shape.getY() + shape.getHeight() / 2);
		gc.setFill(shape.getColor());
		gc.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());

	}


	
	void drawSelected(GraphicsContext gc, Color color) {
		shape.setColor(color);
		gc.setFill(color);
		gc.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		gc.strokeText("x", shape.getX() + shape.getWidth() / 2, shape.getY() + shape.getHeight() / 2);
	}
	
	
}
