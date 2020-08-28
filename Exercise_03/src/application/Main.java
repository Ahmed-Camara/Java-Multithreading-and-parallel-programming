package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Pane pane = new Pane();
		Image image = 
				new Image(getClass().getResource("../images/4.jpg").toString());
		ImageView imageView = new ImageView(image);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				double xPos = 100.0;
				 double yPos = 300.0;
				try {
					
					while(true) {
						
						if(yPos < 0) {
							yPos = 300.0;
						}
						double y = yPos;
						
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								imageView.setX(xPos);
								imageView.setY(y);
								pane.getChildren().clear();
								pane.getChildren().add(imageView);
							}
							
						});
						Thread.sleep(50);
						yPos -= 10.0;
					}
				}catch(Exception ex) {
					
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
