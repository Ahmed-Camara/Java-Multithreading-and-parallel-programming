package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;


public class Main extends Application {
	private clockPane clock;
	@Override
	public void start(Stage primaryStage) {
		
		
		StackPane root = new StackPane();
		
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				try {
					
					while(true) {
						clock = new clockPane();
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								
								root.getChildren().clear();
								root.getChildren().add(clock);
							}
							
						});
						Thread.sleep(100);
					}
				}catch(InterruptedException ex) {
					
				}
			}
			
		}).start();
		Scene scene = new Scene(root,400,400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
