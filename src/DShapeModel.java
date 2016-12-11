
import java.io.Serializable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DShapeModel implements Serializable{
	private SimpleIntegerProperty xProp, yProp, widthProp, heightProp;
	private int x, y, width, height;
	private Color color;
	private double red, green, blue, opacity;
	private String test;
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DShapeModel() {
		x = y = width = height = 0;
		xProp = new SimpleIntegerProperty(x);
		yProp = new SimpleIntegerProperty(y);
		widthProp = new SimpleIntegerProperty(width);
		heightProp = new SimpleIntegerProperty(height);
		color = Color.GRAY;
		test = "test";
		
		red = .8;
		green = .8;
		blue = .8;
	}
	
	public String getTest() {
		return test;
	}
	
	public void setTest(String test) {
		this.test = test;
	}

	public void setDefault() {
		opacity = 1;
		color = Color.GRAY;
		x = y = 10;
		xProp.setValue(10);
		yProp.setValue(10);
		width = height = 20;
		widthProp.setValue(20);
		heightProp.setValue(20);
	}

	public void setDShapeModel(int x, int y, int width, int height, Color color) {
		this.x = x;
		xProp.setValue(x);
		this.y = y;
		yProp.setValue(y);
		this.width = Math.abs(width);
		widthProp.setValue(Math.abs(width));
		this.height = Math.abs(height);
		heightProp.setValue(Math.abs(height));
		this.color = color;
	}
	
	public void setDShapeModel(int x, int y, int width, int height) {
		this.x = x;
		xProp.setValue(x);
		this.y = y;
		yProp.setValue(y);
		this.width = width;
		widthProp.setValue(width);
		this.height = height;
		heightProp.setValue(height);
	}

	public void move(int x, int y) {
		this.x = x;
		xProp.setValue(x);
		this.y = y;
		yProp.setValue(y);
	}
	
	public DShape createShape(){
		DShape shape = new DShape();
		Color color = new Color(this.getRed(), this.getGreen(), this.getBlue(), this.getOpacity());
		shape.setDShapeModel(this.getX(), this.getY(), this.getWidth(), this.getHeight(), color);
		return shape;
	}
	
	public void setColor(Color color) {
		this.color = color;
		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();
		opacity = color.getOpacity();
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setRed(double red){
		this.red = red;
	}
	
	public double getRed(){
		return red;
	}
	
	public void setGreen(double green){
		this.green = green;
	}
	
	public double getGreen(){
		return green;
	}
	
	public void setBlue(double blue){
		this.blue = blue;
	}
	
	public double getBlue(){
		return blue;
	}
	
	public void setOpacity(double opacity){
		this.opacity = opacity;
	}
	
	public double getOpacity(){
		return opacity;
	}
	
	public void setX(int x){
		this.x = x;
	}

	public int getX() {
		return x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth() {
		return width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	// for text
	void setText(String txt) { }
	void setWholeText(String txt) { }
	void setFont(Font font) { }
	void setFontSize(double size) { }
	String getText() { return ""; }
	String getWholeText() { return ""; }
	Font getFont() { return null; }
	void computeFont() { };
}
