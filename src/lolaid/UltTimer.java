/*Copyright (C) <2015>  <George Erfesoglou>
 * 
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.*/
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
    public String soundFile1;
    public String soundFile2;
    int x;
    int y;
    boolean ultOn = false;
    boolean ultOff = true;
    boolean enablePlayDown;
    public UltTimer(){
    }
    
    public UltTimer(String soundLocation1, String soundLocation2, int xPos, int yPos, boolean playDown){
        
        try {
            robot = new Robot();
            timer = new Timer();
        }
        catch (AWTException e) {
            e.printStackTrace();
        }
        //alreadyExecuted = AE;
        soundFile1 = soundLocation1;
        soundFile2 = soundLocation2;
        x = xPos;
        y = yPos;
        enablePlayDown = playDown;
        
    }
    
    void StartTimer(){

    }
    
    boolean CheckColor(){
        
      this.color = robot.getPixelColor(x, y);
      //System.out.println("Checking" + " " + alreadyExecuted);
        if ( !ultOn && (color.getGreen() > 250 && (color.getRed() < 153 && color.getBlue() < 153) || (color.getGreen() > 203 && (color.getRed() < 103 && color.getBlue() < 103) || (color.getGreen() > 50 && (color.getRed() < 3 && color.getBlue() < 3))))){
            AnnounceUltOn();
            ultOn = true;
            if(enablePlayDown)
                ultOff = true;
            //System.out.println("Done" + alreadyExecuted + " " + color.getGreen());
        }
        //in case where enable down is on we play the sound and toggle the ultOn 
        else if(enablePlayDown && ultOff && (color.getGreen() < 50 && color.getRed() < 50 && color.getBlue() < 50)){
            AnnounceUltOff();
            ultOn = false;
            ultOff = false;
            //System.out.println("false " + color.getGreen());
       }
       //to still toggle ultOn in case where user doesn't want to enable down sound. 
       else if(!enablePlayDown && (color.getGreen() < 50 && color.getRed() < 50 && color.getBlue() < 50)){
            ultOn = false;
            //System.out.println("false " + color.getGreen());
       }
       else{
        //System.out.println("No if true " + color.getGreen());
       }

        return ultOn;
    }
    
    void AnnounceUltOn(){
        try{
            // open the sound file as a Java input stream
            //soundFile = "./sounds/champ1green.wav";
            ClassLoader cl = this.getClass().getClassLoader();
            cl.getResource(soundFile1);
            InputStream in = new FileInputStream(soundFile1);
 
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
    void AnnounceUltOff(){
        try{
            // open the sound file as a Java input stream
            //soundFile = "./sounds/champ1green.wav";
            ClassLoader cl = this.getClass().getClassLoader();
            cl.getResource(soundFile2);
            InputStream in = new FileInputStream(soundFile2);
 
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

