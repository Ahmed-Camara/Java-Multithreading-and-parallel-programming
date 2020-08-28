package application;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class FanPane extends Pane{
	Arc []arc = new Arc[4];
	private double startAngle;
	private double x = 100;
	private double y = 100;
	public FanPane(double x) {
		this.startAngle = x;
		drawFan();
	}

	private void drawFan() {
		
		for(int i = 0; i < arc.length; i++) {
			arc[i] = new Arc(x,y,50,50,startAngle,60);
			arc[i].setType(ArcType.ROUND);
			getChildren().add(arc[i]);
			startAngle+= 90;
		}
		
	}

}
