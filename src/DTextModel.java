import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

import javafx.scene.text.Font;

public class DTextModel extends DShapeModel {
	String txt;
	Font font;
	double fontSize;
	
	DTextModel() {
		txt = "Hello";
		fontSize = 20;
		font = Font.font("Dialog", fontSize);
	}
	
	DTextModel(String txt) {
		super();
		this.txt = txt;
		font = Font.font("Dialog", 12);
	}
	
	
	
	void setText(String txt) {
		this.txt = txt;
	}
	
	void setFont(Font font) {
		this.font = Font.font(font.getName(), this.font.getSize());
	}
	
	void setFontSize(double size) {
		this.fontSize = size;
		font = Font.font(font.getName(), size);
	}
	
	String getText() {
		return txt;
	}
	
	Font getFont() {
		return font;
	}
	
	double getFontSize() {
		return fontSize;
	}
	
	void computeFont() {
		double newSize = getFont().getSize();
		Font newFont = Font.font(getFont().getName(), newSize);
		FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
		double textHeight = (fm.getMaxAscent() + fm.getMaxDescent()) / 1.75;
		double textWidth = fm.computeStringWidth(getText());

		while(getHeight() > textHeight) {
			newSize = (newSize * 1.1) + 1;
			newFont = Font.font(getFont().getName(), newSize);
			fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
			textHeight = (fm.getMaxAscent() + fm.getMaxDescent()) / 1.75;
		}
		while(getHeight() * 1.1 < textHeight) {
			newSize = (newSize * .9) - 1;
			if(newSize < 1) {
				newSize = 1;
				break;
			}
			newFont = Font.font(getFont().getName(), newSize);
			fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
			textHeight = (fm.getMaxAscent() + fm.getMaxDescent()) / 1.75;
		}
		setFontSize(newSize);
	}
}