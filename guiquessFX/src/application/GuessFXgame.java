package application;
	


import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.*;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.property.*;


public class GuessFXgame extends Application {
	
	private StringProperty text;
	private Label lbOutput;
	private int Nr;
	
	public void checkQuess() {
		String gtekst=text.getValue();
		String mess="";
		
		try {
			int quess=Integer.parseInt(gtekst);
			if(quess<Nr)
				mess=quess+"is too low";
			else if(quess>Nr)
				mess= quess+ "is too high";
			else {
				mess= quess + "is correct";
				newGame();
			}
		}catch (Exception e){
			mess="Enter whole nr between 0 nad 100";
		}finally
		{
			lbOutput.setText(mess);
			
		//text.selectAll();
		}
	}
	
	public void newGame() {
		Nr=(int)(Math.random()*100+1);
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Zgaduj");
        
        Group root = new Group();
        Scene scene = new Scene(root, 500, 600, Color.AQUA);
        
        Label label2= new Label(" my game");
        label2.setFont(new Font("Thoma", 19));
      //label horizontal aligment(CENTER)
        //setBound
        
        root.getChildren().add(label2);
        
        Label labelnr= new Label("guess the number from 0 to 100");
        
        root.getChildren().add(labelnr);
        
        text = new SimpleStringProperty();
         text.addListener( Observable  -> { 
        	checkQuess();
        	
        	 });
         
         Text textfield= new Text();
         textfield.textProperty().bind(text);
         textfield.fillProperty();
         
         textfield.setVisible(true);
         
        root.getChildren().add(textfield);
        
       //set columns
        
        Button btn=new Button("Guess");
        btn.setOnAction(e->{
        	checkQuess();
        });
        
        btn.setLayoutX(50);
        btn.setLayoutY(30);
        
        root.getChildren().add(btn);
        
        lbOutput = new Label("enter number above and click >quess");
        
        
        root.getChildren().add(lbOutput);
   
			
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
	
		
	}
	
	public static void main(String[] args) {
		launch(args);
		
		GuessFXgame test= new GuessFXgame();
		test.newGame();
		
	}
}
