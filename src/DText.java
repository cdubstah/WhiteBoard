import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DText extends DShape {
	
	public DText() {
		shape = new DTextModel();
		shape.setDefault();
	}
	
	public DText(String txt) {
		shape = new DTextModel(txt);
		shape.setDefault();
	}
	
	
	void draw(GraphicsContext gc) {
		gc.setFill(shape.getColor());
		gc.setFont(shape.getFont());
		gc.fillText(shape.getText(), shape.getX(), shape.getY());
	
	}
	
	void drawSelected(GraphicsContext gc) {
		gc.setFill(shape.getColor());
		gc.setFont(shape.getFont());
		gc.fillText(shape.getText(), shape.getX(), shape.getY());
		gc.setFill(Color.BLACK);
		drawKnobs(gc);
	}

	void drawSelected(GraphicsContext gc, Color color) {
		shape.setColor(color);
		gc.setFill(color);
		gc.fillText(shape.getText(), shape.getX(), shape.getY());
		gc.setFill(Color.BLACK);
		drawKnobs(gc);
	
	}
	
	void setText(String txt) {
		shape.setText(txt);
	}
	
	void setFont(Font font) {
		shape.setFont(font);
	}
	
	String getText() {
		return shape.getText();
	}
	
	Font getFont() {
		return shape.getFont();
	}
}
