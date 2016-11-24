import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Canvas extends Pane {
	public Canvas() {
		setMinSize(400,400);
		setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0,0,0,0))));
	}
}
