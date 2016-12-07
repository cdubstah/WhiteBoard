import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class DRect extends DShape {
	
	public DRect() {
	super();
	}
	void draw(GraphicsContext gc) {
		gc.setFill(shape.getColor());
		gc.fillRect(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	
	}
}
