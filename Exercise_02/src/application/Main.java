package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	
	private Rectangle body;
	private Circle back;
	private Circle front;
	private Polygon polygon;
	@Override
	public void start(Stage primaryStage) {
		Pane pane = new Pane();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				double xPos = 30.0;
				double yPos = 400.0;
				
				try {
					while(true) {
					
					if(xPos > 800) {
						xPos = 30.0;
					}
						back = new Circle(xPos,yPos,8);
						front = new Circle(xPos + 50,yPos,8);
						body = new Rectangle(xPos - 20,yPos - 20,90,15);
						polygon = 
								new Polygon(xPos-5,yPos - 20,xPos+10,yPos-30,xPos+40,yPos-30,xPos+55,yPos - 20);
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								
								pane.getChildren().clear();
								pane.getChildren().addAll(back,front,body,polygon);
							}
							
						});
						Thread.sleep(10);
						xPos++;
					}
				}catch(Exception ex) {
					
				}
			}
			
		}).start();

	    Scene scene = new Scene(pane, 800, 500);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}