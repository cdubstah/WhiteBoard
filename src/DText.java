
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
		clipText(gc);
		gc.fillText(shape.getText(), shape.getX(), shape.getY() + shape.getHeight());
	}
	
	void drawSelected(GraphicsContext gc) {
		gc.setFill(shape.getColor());
		computeFont();
		gc.setFont(shape.getFont());
		clipText(gc);
		gc.fillText(shape.getText(), shape.getX(), shape.getY() + shape.getHeight());
		gc.setFill(Color.BLACK);
		drawKnobs(gc);
	}

	void drawSelected(GraphicsContext gc, Color color) {
		shape.setColor(color);
		gc.setFill(color);
		computeFont();
		clipText(gc);
		gc.fillText(shape.getText(), shape.getX(), shape.getY() + shape.getHeight());
		gc.setFill(Color.BLACK);
		drawKnobs(gc);
	}
	
	void setText(String txt) {
		shape.setText(txt);
	}
	
	void setWholeText(String txt) {
		shape.setWholeText(txt);
	}
	
	void setFont(Font font) {
		shape.setFont(font);
	}
	
	String getText() {
		return shape.getText();
	}
	
	String getWholeText() {
		return shape.getWholeText();
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
		while(textWidth > shape.getWidth()) {
			if(shape.getText().length() == 0)
				break;
			shape.setText(shape.getText().substring(0, shape.getText().length() - 1));
			newFont = Font.font(getFont().getName(), shape.getFont().getSize());
			fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
			textWidth = fm.computeStringWidth(shape.getText());
		}
		if(textWidth <= shape.getWidth()) {
			String test = shape.getText();
			newFont = Font.font(getFont().getName(), shape.getFont().getSize());
			fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
			textWidth = fm.computeStringWidth(test);
			while(textWidth <= shape.getWidth()) {
				if(test.length() == shape.getWholeText().length())
					break;
				test += String.valueOf(shape.getWholeText().charAt(test.length()));
				newFont = Font.font(getFont().getName(), shape.getFont().getSize());
				fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
				textWidth = fm.computeStringWidth(test);
			}
			shape.setText(test);
		}
	}
}
