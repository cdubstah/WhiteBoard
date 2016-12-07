import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

public class DRect extends DShape {

	public DRect() {
		super();
	}

	void draw(GraphicsContext gc) {
		gc.setFill(shape.getColor());
		gc.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	}

	void drawSelected(GraphicsContext gc) {
		gc.setFill(shape.getColor());
	
		gc.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		gc.strokeText("x", shape.getX() + shape.getWidth() / 2, shape.getY() + shape.getHeight() / 2);
		gc.setTextAlign(TextAlignment.CENTER);

	}
}
