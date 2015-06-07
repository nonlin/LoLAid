/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolaid;

import java.awt.Toolkit;
import java.util.Timer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author George
 */
public class LoLAid extends Application {
    
    int scale = -1;
    double pixleCount = 0;
    @Override
    public void start(Stage primaryStage) {

//Creating a GridPane container
GridPane grid = new GridPane();
grid.setPadding(new Insets(10, 10, 10, 10));
grid.setVgap(5);
grid.setHgap(5);

//Defining the Name text field
final TextField scaleInput = new TextField();
scaleInput.setPromptText("Enter Lol Hud Scale Factor.");
GridPane.setConstraints(scaleInput, 0, 0);
grid.getChildren().add(scaleInput);

//Defining the Submit button
Button submit = new Button("Submit");
GridPane.setConstraints(submit, 1, 0);
grid.getChildren().add(submit);

//Defining the Clear button
Button clear = new Button("Stop");
GridPane.setConstraints(clear, 1, 1);
grid.getChildren().add(clear);

//Adding a Label
final Label label = new Label();
GridPane.setConstraints(label, 0, 3);
GridPane.setColumnSpan(label, 2);
grid.getChildren().add(label);

//Adding a Label
final Label description = new Label();
GridPane.setConstraints(description, 0, 2);
GridPane.setColumnSpan(description, 2);
grid.getChildren().add(description);
description.setText("Enter your Lol Hud Scale Factor in the above text field.");
submit.setOnAction((ActionEvent e) -> {
    if (
        (Integer.parseInt(scaleInput.getText()) >= 0 && Integer.parseInt(scaleInput.getText()) <= 100 && !scaleInput.getText().isEmpty())
    ) {
   // label.setText("Scale Factor Set At " + scaleInput.getText() );
    //Eseentialy stop timer to restart it;
    Timer timer = new Timer();
    runCheck(Integer.parseInt(scaleInput.getText()),label, timer, clear, scaleInput, submit);

    } else {
        label.setText("You have not enetred a scale factor.");
    }
});



        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setTitle("LolAid - Ult Notifier 1.0");
        primaryStage.setScene(scene);
        primaryStage.show();
        
       
    }
     @Override
      public void stop()
      {
          System.exit(0);
      }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);

    }
    
    public void runCheck(int scale,Label label, Timer timer, Button clear, TextField scaleInput, Button submit){
    
        submit.setDisable(true);
        double maxSupportedPixleCount = 3686400;
        
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        pixleCount = height * width;
        System.out.println(pixleCount/maxSupportedPixleCount);
        double pixleCountRatio = pixleCount/maxSupportedPixleCount;
        //Scale is from 0 to 100 and ratio is from .45 to 100. 
        //scale = 50;
        double baseRatio = 0.467;
        //Since raio range is .55, to calculate the how much a scale step is to a ratio step see how many times it takes 100 to get to .55
        //0.55 / 100 = 0.0055
        double ratioStepPerScaleStep = 0.0055;
        //Pixle Mod is the amount based on the scale and ratio to modify the pixle placement of x and y
        //If Scale is 0 return the baseRatio, unchanged. 
        //Else return the scale * the ratio steps we need to take to accomidate a scale step plus the baseRatio to get actual Mod amount;
        double pixleMod =   (scale == 0 ? baseRatio :(scale * ratioStepPerScaleStep) + baseRatio);
        int maxValueX = 70;
        //Based on 2560x1440 resolution
        int maxValueY = (int)(280 * pixleCountRatio);
        int maxNextY = (int)(118 * pixleCountRatio);
        
        int x = (int)(maxValueX * (pixleMod));
        int y = (int)(maxValueY * (pixleMod));
        int nextY =  (int)(maxNextY * (pixleMod));
        
        label.setText("Scale Factor Set At " + scale + "\n" + "Checking Ults\n" + "Screen Size " + width + "x" + height  + "\n" + "X Value is :" + x + " " + "Y Value is :" + y);
        clear.setOnAction((ActionEvent e) -> {
            scaleInput.clear();
            label.setText(null);
            timer.cancel();
            timer.purge();
            submit.setDisable(false);
        });       
        //CheckForUlts test = new CheckForUlts();
        System.out.println("Checking Ults");
        System.out.println("Screen Size " + width + "x" + height + " PixleRatio: " + pixleCountRatio);
        System.out.println("X Value is :" + x + " " + "Y Value is :" + y);
        timer.scheduleAtFixedRate(new CheckForUlts(x,y,nextY), 0, 1000);
    }
    
}
