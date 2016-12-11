import java.io.Serializable;

import javafx.scene.paint.Color;

public class DRectModel extends DShapeModel implements Serializable{

	public DShape createShape(){
		DShape shape = new DRect();
		Color color = new Color(this.getRed(), this.getGreen(), this.getBlue(), this.getOpacity());
		shape.setDShapeModel(this.getX(), this.getY(), this.getWidth(), this.getHeight(), color);
		return shape;
	}

}
