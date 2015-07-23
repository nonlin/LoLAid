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
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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
    int scale;
    Pane root;
    boolean ultOn = false;
    boolean ultOff = true;
    boolean enablePlayDown;
    long start;
    int seconds;
    int id;
    boolean startCount = false;
    int tempCoolDown = 0;
    boolean ultCoolDownSet = false;
    boolean countingDown = false;
    AnimationTimer ultCounter;
    AnimationTimer coolDownCounter;
    final Label counter_1 = new Label("");

    public UltTimer() {
    }

    public UltTimer(String soundLocation1, String soundLocation2, int xPos, int yPos, boolean playDown, int Scale, Pane Root, long st, int ID) {

        try {
            robot = new Robot();
            timer = new Timer();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        id = ID;
        soundFile1 = soundLocation1;
        soundFile2 = soundLocation2;
        x = xPos;
        y = yPos;
        scale = Scale;
        root = Root;
        start = st;
        enablePlayDown = playDown;
        createTimer();
        // StartTimer();
    }

    void InitilizeUltCounter() {

        ultCounter = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long time = System.currentTimeMillis() - start;
                seconds = (int) (time / 1000);

                System.out.println("Setting Timer: " + id + " " + seconds);

            }
        };
    }

    void UltCountDown() {

        coolDownCounter = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long time = System.currentTimeMillis() - start;
                seconds = (int) (time / 1000);
                System.out.println("CoolDown: " + id + " with " + tempCoolDown + " - " + seconds + " = " + (tempCoolDown - seconds));
                seconds = tempCoolDown - seconds;

            }
        };
    }

    boolean CheckColor() {

        this.color = robot.getPixelColor(x, y);
       // System.out.println("X:" + x + " Y:"+ y + " " + this.color);
        //Do when Ult is Up
        if (!ultOn && (color.getGreen() > 140 && (color.getRed() <140 && color.getBlue() > 90) )) {
            AnnounceUltOn();
            UltTimerLogicUp();
            ultOn = true;
            if (enablePlayDown) {
                ultOff = true;
            }

        } //in case where enable down is on we play the sound and toggle the ultOn 
        else if (enablePlayDown && ultOff && ((color.getGreen() < 50 && color.getRed() < 50 && color.getBlue() < 50) || (color.getGreen() < 90 && color.getRed() < 50 && color.getBlue() > 50))) {
            AnnounceUltOff();
            UltTimerLogicDown();
            ultOn = false;
            ultOff = false;

        } //to still toggle ultOn in case where user doesn't want to enable down sound. 
        else if (!enablePlayDown && ((color.getGreen() < 50 && color.getRed() < 50 && color.getBlue() < 50) || (color.getGreen() < 90 && color.getRed() < 50 && color.getBlue() > 50))) {
            UltTimerLogicDown();
            ultOn = false;

        } else {
            //System.out.println("No if true " + color.getGreen());
        }

        return ultOn;
    }

    void AnnounceUltOn() {
        try {
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void AnnounceUltOff() {
        try {
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
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void createTimer() {
        //start();
        int offSet = 34;
        double maxFontSize =  (65 * (scale/100.0));

        if(scale <= 10){
            maxFontSize =  (400 * (scale/100.0));
        }
        if(scale <= 5){
            maxFontSize =  (800 * (scale/100.0));
        }
        if(scale <= 2){
            maxFontSize =  (1600 * (scale/100.0));
        }
        
         
        String couterStyle = "-fx-text-fill: rgba(250, 250, 250, 1.0); -fx-font-style: italic; -fx-font-size: " + maxFontSize + "; -fx-font-weight: bold; -fx-padding: 0 0 20 0;";

        //Use Platform.runlater to create labels outside of fx thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                counter_1.setLayoutX(x - offSet);
                counter_1.setLayoutY(y);
                root.getChildren().add(counter_1);
                counter_1.setStyle(couterStyle);
                System.out.println("X:" + x + " " + "Y:" + y);
                // counter_1.setText(Integer.toString(seconds));
            }
        });

    }

    public void UpdateTimerDisplay() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                root.setVisible(true);
                counter_1.setVisible(true);
                counter_1.setText(Integer.toString(seconds));
            }
        });
    }

    public void HideTimerDisplay() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                root.setVisible(false);
                counter_1.setVisible(false);

            }
        });
    }

    public void removeTimer() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                root.getChildren().remove(counter_1);
            }
        });
    }

    public void resetUltTimer() {
        startCount = true;
        tempCoolDown = 0;
        ultCoolDownSet = false;
        countingDown = false;
        seconds = 0;
        removeTimer();
    }

    private void UltTimerLogicDown() {
        //if we've been given the ok to start Counting and we haven't already gotten a coolDown Down then we need to prepare to get one
        if (startCount && !ultCoolDownSet && !countingDown) {
            start = System.currentTimeMillis();
            InitilizeUltCounter();
            ultCounter.start();
            startCount = false;
        }
        //ONce we have one start the count down
        if (ultCoolDownSet) {
            start = System.currentTimeMillis();
            UltCountDown();
            coolDownCounter.start();
            countingDown = true;
            ultCoolDownSet = false;
        }
    }

    private void UltTimerLogicUp() {
        //if we haven't yet set a cool down counter
        if (!countingDown) {
            if (startCount) {

                startCount = false;
            } else {//This is where we don't need set the initialCount
                startCount = true;
                if (ultCounter != null) {
                    ultCounter.stop();
                    tempCoolDown = seconds;
                    System.out.println("CoolDown Set to " + tempCoolDown);
                    //so next time ult goes down we can use this instead, a count down timer
                    ultCoolDownSet = true;
                }
            }
        } else {

            //This is where we have a coolDownTimer when we go green we need to stop the coolDown timer from counting anymore. 
            if (coolDownCounter != null) {
                coolDownCounter.stop();
                countingDown = false;
                if (seconds > 0) {
                    //this is where we go green before the timer finished (meaning inaccurate timer) So we need to start the update for the timer again and reset everything)
                    startCount = true;
                    tempCoolDown = 0;
                    ultCoolDownSet = false;
                    countingDown = false;
                    seconds = 0;
                } //This is where are timer was "right" just prevent setting new cooldown and starting timer over
                else if (seconds == 0) {
                    startCount = false;
                    ultCoolDownSet = true;
                    countingDown = true;
                    seconds = 0;
                } //in case ult timer is off becuase ult has gone longer than expected add the increased time to correct the ult timer
                else if (seconds < 0) {
                    startCount = false;
                    ultCoolDownSet = true;
                    countingDown = true;
                    tempCoolDown = tempCoolDown + (seconds * -1);
                    seconds = 0;
                }
            }
        }
    }
}
