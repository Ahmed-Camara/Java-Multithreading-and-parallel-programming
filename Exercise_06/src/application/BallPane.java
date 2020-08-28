package application;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class BallPane extends Pane{
	private Circle cirlce;
	private double centerX;
	private double centerY;
	public BallPane(double x,double y) {
		this.centerX = x;
		this.centerY = y;
		
		drawBall();
	}
	
	private void drawBall() {
		cirlce = new Circle(centerX,centerY,15);
		getChildren().add(cirlce);
	}
}
