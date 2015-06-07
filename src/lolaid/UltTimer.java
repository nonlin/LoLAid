/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolaid;
import java.applet.*;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.*;
import java.util.Timer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
/**
 *
 * @author George
 */
public class UltTimer {
    
    public Robot robot; 
    public Timer timer; 
    public Color color;
    public String soundFile;
    int x;
    int y;
    boolean alreadyExecuted = false;
    public UltTimer(){
    }
    
    public UltTimer(String soundLocation, int xPos, int yPos){
        
        try {
            robot = new Robot();
            timer = new Timer();
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
        //alreadyExecuted = AE;
        soundFile = soundLocation;
        x = xPos;
        y = yPos;
        
    }
    
    void StartTimer(){

    }
    
    boolean CheckColor(){
        
      this.color = robot.getPixelColor(x, y);
      //System.out.println("Checking" + " " + alreadyExecuted);
        if ( !alreadyExecuted && (color.getGreen() > 250 && (color.getRed() < 153 && color.getBlue() < 153) || (color.getGreen() > 203 && (color.getRed() < 103 && color.getBlue() < 103) || (color.getGreen() > 50 && (color.getRed() < 3 && color.getBlue() < 3))))){
            AnnounceUlt();
            alreadyExecuted = true;
            //System.out.println("Done" + alreadyExecuted + " " + color.getGreen());
        }
        else if(color.getGreen() < 50 && color.getRed() < 50 && color.getBlue() < 50){
            alreadyExecuted = false;
            //System.out.println("false " + color.getGreen());
       }
       else{
        //System.out.println("No if true " + color.getGreen());
       }

        return alreadyExecuted;
    }
    
    void AnnounceUlt(){
        try{
            // open the sound file as a Java input stream
            //soundFile = "./sounds/champ1green.wav";
            ClassLoader cl = this.getClass().getClassLoader();
            cl.getResource(soundFile);
            InputStream in = new FileInputStream(soundFile);
 
            // create an audiostream from the inputstream
            AudioStream audioStream = new AudioStream(in);
 
            // play the audio clip with the audioplayer class

            AudioPlayer.player.start(audioStream);
            //System.out.println(AudioPlayer.player.getId());
            //Thread.sleep(1500);
            //AudioPlayer.player.stop(audioStream);
            //
            }catch(Exception e){System.out.println(e);}
        }
}

