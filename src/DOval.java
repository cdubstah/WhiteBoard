import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DOval extends DShape {
	
	void draw(GraphicsContext gc) {
		gc.setFill(shape.getColor());
		gc.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
	}


	
	void drawSelected(GraphicsContext gc) {
		gc.setFill(shape.getColor());
		gc.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		gc.setFill(Color.BLACK);
		drawKnobs(gc);
	}
	
	void drawSelected(GraphicsContext gc, Color color) {
		shape.setColor(color);
		gc.setFill(color);
		gc.fillOval(shape.getX(), shape.getY(), shape.getWidth(), shape.getHeight());
		gc.setFill(Color.BLACK);
		drawKnobs(gc);
	}
	
	
}
