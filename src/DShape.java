import javafx.scene.canvas.GraphicsContext;

public class DShape {
	DShapeModel shape;
	public DShape() {
		shape = new DRectModel();
		shape.randomize();
	}
	
	void draw(GraphicsContext gc){
		
	}
}
