import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DTextModel extends DShapeModel {
	String txt, wholetxt;
	Font font;
	double fontSize;
	String fontName;
	
	public DTextModel() {
		txt = wholetxt = "Hello";
		fontSize = 20;
		fontName = "Dialog";
		font = Font.font(fontName, fontSize);
	}
	
	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public DTextModel(String txt) {
		super();
		this.txt = txt;
		font = Font.font("Dialog", 12);
	}
	
	public DShape createShape(){
		DShape shape = new DText();
		Color color = new Color(this.getRed(), this.getGreen(), this.getBlue(), this.getOpacity());
		shape.setDShapeModel(this.getX(), this.getY(), this.getWidth(), this.getHeight(), color);
		return shape;
	}
	
	public void setWholeText(String txt) {
		wholetxt = txt;
	}
	
	public void setText(String txt) {
		this.txt = txt;
	}
	
	public void setFont(Font font) {
		this.fontName = font.getName();
		this.font = Font.font(fontName, this.font.getSize());
	}
	
	public void setFontSize(double size) {
		this.fontSize = size;
		font = Font.font(font.getName(), size);
	}
	
	public String getText() {
		return txt;
	}
	
	public String getWholeText() {
		return wholetxt;
	}
	
	public Font getFont() {
		return font;
	}
	
	public double getFontSize() {
		return fontSize;
	}
	
	public void computeFont() {
		double newSize = getFont().getSize();
		Font newFont = Font.font(getFont().getName(), newSize);
		FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
		double textHeight = fm.getMaxAscent() / 1.25;

		while(getHeight() > textHeight) {
			newSize = (newSize * 1.1) + 1;
			newFont = Font.font(getFont().getName(), newSize);
			fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
			textHeight = fm.getMaxAscent() / 1.25;
		}
		while(getHeight() * 1.1 < textHeight) {
			newSize = (newSize * .9) - 1;
			if(newSize < 1) {
				newSize = 1;
				break;
			}
			newFont = Font.font(getFont().getName(), newSize);
			fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(newFont);
			textHeight = fm.getMaxAscent() / 1.25;
		}
		setFontSize(newSize);
	}
}