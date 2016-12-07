import javafx.scene.canvas.GraphicsContext;

public class DOval extends DShape {
	public DOval() {
		super();
	}

	void draw(GraphicsContext gc) {
		gc.setFill(shape.getColor());
		gc.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());

	}
}
