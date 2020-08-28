package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	private FanPane fan;
	@Override
	public void start(Stage primaryStage) {
		StackPane pane = new StackPane();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				double x = 90;
				try {
					
					while(true) {
						
						if(x < 0) x = 90;
						 fan = new FanPane(x);
					
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								
								pane.getChildren().clear();
								pane.getChildren().addAll(fan);
							}
							
						});
						Thread.sleep(40);
						x-= 30;
					}
				}catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			
		}).start();
		Scene scene = new Scene(pane,400,400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
