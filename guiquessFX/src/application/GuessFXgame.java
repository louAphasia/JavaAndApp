package application;
	

import java.util.Random;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.beans.property.*;



public class GuessFXgame extends Application {
	
	private TextField text;
	private Label lbOutput;
	private int Nr;
	
	public void newGame() {
		 Nr=(int)(Math.random()*100+1);

	}
	
	public void checkQuess() {
		String quess=text.getText();
		String mess="";
		
		try {
			int guess=Integer.parseInt(quess);
			do {
			if(guess<Nr)
				mess=guess+"  is too low";
			else if(guess>Nr)
				mess= guess+ "  is too high";
			}
			while(guess!=Nr);
			
				mess= guess + " is correct";	
				
			
		}catch (Exception e){
			mess="Enter whole nr between 0 nad 100";
		}finally
		{
			lbOutput.setText(mess);
			text.requestFocus();
			
		text.selectAll();
		
	
		}
	}
	

	
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Zgaduj");
        
        VBox root = new VBox(10);
        Scene scene = new Scene(root, 500, 600);
        
   scene.setFill(Color.ANTIQUEWHITE);
       
        
        Label label2= new Label(" my game");
        label2.setFont(new Font("Thoma", 19));
        label2.setAlignment(Pos.TOP_RIGHT);
     
      
    Label labelnr= new Label("guess the number from 0 to 100");
   

       text= new TextField();
        text.setPrefColumnCount(10);
       
       
     
        Button btn=new Button("Guess");
    btn.setAlignment(Pos.TOP_LEFT);
        btn.setOnAction(e->{
        	checkQuess();
        });
        
        btn.setLayoutX(50);
        btn.setLayoutY(30);
      
       
        
        lbOutput = new Label("enter number above and click >quess");
        lbOutput.setFont(new Font("arial",24));
        
       
      
      root.getChildren().addAll(label2, labelnr, lbOutput, btn ,text);
        
        
        
   
		
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			
		primaryStage.setScene(scene);
			primaryStage.show();
	
		
	}
	
	public static void main(String[] args) {
		GuessFXgame test=new GuessFXgame();
		test.newGame();
		launch(args);
		
		
		
		
		
		
	}
}
