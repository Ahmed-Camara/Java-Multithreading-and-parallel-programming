package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Main extends Application {
	private BallPane ball;
	@Override
	public void start(Stage primaryStage) {
		
		Pane pane = new Pane();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				double dx = 1,dy =1;
				double x = 20.0;
				double y = 20.0;
				try {
					while(true) {
						
						if(x > 900 || x < 0) dx *= -1;
						if(y > 600 || y < 0) dy *= -1;
						ball = new BallPane(x,y);
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								pane.getChildren().clear();
								pane.getChildren().add(ball);
							}
						});
						Thread.sleep(1);
						x+=dx;
						y+=dy;
					}
				}catch(InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}).start();
		Scene scene = new Scene(pane,900,600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
