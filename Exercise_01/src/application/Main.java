package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;


public class Main extends Application {
	private TextArea tf = new TextArea();
	@Override
	public void start(Stage primaryStage) {
		Scene scene = new Scene(tf,400,200);
		
		
		
		Runnable printChr1 = new printChar('a',100);
		Runnable printChr2 = new printChar('b',100);
		Runnable printNumber = new printNum(100);
		
		Thread th1 = new Thread(printChr1);
		Thread th2 = new Thread(printChr2);
		Thread th3 = new Thread(printNumber);
		
		th1.start();
		th2.start();
		th3.start();
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
class printChar implements Runnable{
		
		private char charToPrint;
		private int times;
		
		public printChar(char c,int t) {
			this.charToPrint = c;
			this.times = t;
		}
		@Override
		public void run() {
			
			for(int i = 0; i < times; i++) {
				tf.appendText(charToPrint+"");
			}
			tf.appendText("\n");
		}
	}
	class printNum implements Runnable{
		
		private int lastNum;
		
		public printNum(int n) {
			this.lastNum = n;
		}
		@Override
		public void run() {
			for(int i = 1; i <= lastNum; i++) {
				tf.appendText(i+"");
			}
			tf.appendText("\n");
		}
		
	}
}

