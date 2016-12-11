import javafx.scene.paint.Color;

public class DLineModel extends DShapeModel {
	
	public DShape createShape(){
		DShape shape = new DLine();
		Color color = new Color(this.getRed(), this.getGreen(), this.getBlue(), this.getOpacity());
		shape.setDShapeModel(this.getX(), this.getY(), this.getWidth(), this.getHeight(), color);
		return shape;
	}
	
}
