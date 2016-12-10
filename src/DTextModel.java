import javafx.scene.text.Font;

public class DTextModel extends DShapeModel {
	String txt;
	Font font;
	
	DTextModel() {
		txt = "Hello";
		font = Font.font("Dialog", 12);
		
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
		this.font = font;
	}
	
	String getText() {
		return txt;
	}
	
	Font getFont() {
		return font;
	}
}
