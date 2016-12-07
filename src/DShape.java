import javafx.scene.canvas.GraphicsContext;

public class DShape{
	DShapeModel shape;

	public DShape() {
		shape = new DRectModel();
		shape.randomize();
		
	}
	
	DShapeModel getShapeModel() {
		return shape;
	}
	void draw(GraphicsContext gc) {

	}
	
	void drawSelected(GraphicsContext gc ) {
		
	}
	

}
