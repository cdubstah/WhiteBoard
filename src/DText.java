
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

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
		computeFont();
		gc.setFont(shape.getFont());
		gc.fillText(shape.getText(), shape.getX(), shape.getY() + shape.getHeight());
		clipText(gc);
	}
	
	void drawSelected(GraphicsContext gc) {
		gc.setFill(shape.getColor());
		computeFont();
		gc.setFont(shape.getFont());
		gc.fillText(shape.getText(), shape.getX(), shape.getY() + shape.getHeight());
		gc.setFill(Color.BLACK);
		drawKnobs(gc);
	}

	void drawSelected(GraphicsContext gc, Color color) {
		shape.setColor(color);
		gc.setFill(color);
		computeFont();
		gc.fillText(shape.getText(), shape.getX(), shape.getY() + shape.getHeight());
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
	
	void computeFont() {
		shape.computeFont();
	}
	
	void clipText(GraphicsContext gc) {
		Font newFont = Font.font(getFont().getName(), shape.getFont().getSize());
		FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
		double textWidth = fm.computeStringWidth(shape.getText());
//		if(textWidth > shape.getWidth()) {
//			// clip
//			gc.rect(shape.getX() + shape.getWidth(), shape.getY(), textWidth - shape.getWidth(), shape.getHeight());
//			gc.clip();
//		}
	}
}
