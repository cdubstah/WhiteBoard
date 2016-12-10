import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DLine extends DShape {
	public DLine() {
		shape = new DLineModel();
		shape.setDefault();
	}
	
	void drawKnobs(GraphicsContext gc) {
		int shapeX = shape.getX();
		int shapeY = shape.getY();
		int shapeWidth = shape.getWidth();
		int shapeHeight = shape.getHeight();
		gc.fillRect(
				shapeX - KNOB_LENGTH / 2, shapeY - KNOB_LENGTH / 2, KNOB_LENGTH, 9);
//		gc.fillRect(
//				(shapeX + shapeWidth) - KNOB_LENGTH / 2, 
//				shapeY - KNOB_LENGTH / 2, KNOB_LENGTH, 9);
//		gc.fillRect(
//				shapeX - KNOB_LENGTH / 2, 
//				(shapeY + shapeHeight) - KNOB_LENGTH / 2, KNOB_LENGTH, 9);
		gc.fillRect(
				(shapeX + shapeWidth) - KNOB_LENGTH / 2, 
				(shapeY + shapeHeight) - KNOB_LENGTH / 2, KNOB_LENGTH, 9);
	}
	
	void draw(GraphicsContext gc) {
		gc.setStroke(shape.getColor());
		gc.strokeLine(shape.getX(), shape.getY(), shape.getX()+shape.getWidth(), shape.getY()+ shape.getHeight());
	}
	
	void drawSelected(GraphicsContext gc) {
		gc.setStroke(shape.getColor());
		gc.strokeLine(shape.getX(), shape.getY(), shape.getX()+shape.getWidth(), shape.getY()+ shape.getHeight());
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.BLACK);
		drawKnobs(gc);
	}

	void drawSelected(GraphicsContext gc, Color color) {
		shape.setColor(color);
		gc.setStroke(color);
		gc.strokeLine(shape.getX(), shape.getY(), shape.getX()+shape.getWidth(), shape.getY()+shape.getHeight());
		gc.setStroke(Color.BLACK);
		gc.setFill(Color.BLACK);
		drawKnobs(gc);
	}
}
