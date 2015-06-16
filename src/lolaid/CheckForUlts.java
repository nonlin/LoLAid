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

import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.layout.Pane;
import javax.swing.SwingUtilities;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author George
 */
public class CheckForUlts extends TimerTask implements NativeKeyListener {

    UltTimer champCheck1; // = new UltTimer("./sounds/champ1green.wav", 47, 195);
    UltTimer champCheck2; //= new UltTimer("./sounds/champ2green.wav", 47, 275);
    UltTimer champCheck3; //= new UltTimer("./sounds/champ3green.wav", 47, 355);
    UltTimer champCheck4; //= new UltTimer("./sounds/champ4green.wav", 47, 435); 
    long start = System.currentTimeMillis();
    int count = 0;

    public CheckForUlts(int x, int y, int nextY, boolean playUp, boolean playDown, String soundC1, String soundC2, String soundC3, String soundC4, int scale, Pane root) {

        champCheck1 = new UltTimer("./sounds/Up/" + soundC1 + "Up.wav", "./sounds/Down/" + soundC1 + "Down.wav", x, y, playDown, scale, root, start, 1);
        champCheck2 = new UltTimer("./sounds/Up/" + soundC2 + "Up.wav", "./sounds/Down/" + soundC2 + "Down.wav", x, y + nextY, playDown, scale, root, start, 2);
        champCheck3 = new UltTimer("./sounds/Up/" + soundC3 + "Up.wav", "./sounds/Down/" + soundC3 + "Down.wav", x, y + (nextY * 2), playDown, scale, root, start, 3);
        champCheck4 = new UltTimer("./sounds/Up/" + soundC4 + "Up.wav", "./sounds/Down/" + soundC4 + "Down.wav", x, y + (nextY * 3), playDown, scale, root, start, 4);
        //Initialze native hook.
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            ex.printStackTrace();

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(this);
        //Disable Logging
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Change the level for all handlers attached to the default logger.
        Handler[] handlers = Logger.getLogger("").getHandlers();
    }

    public void run() {

        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        champCheck1.CheckColor();
        //champCheck1.UpdateTimerDisplay();

        champCheck2.CheckColor();
        //champCheck2.UpdateTimerDisplay();

        champCheck3.CheckColor();
        //champCheck3.UpdateTimerDisplay();

        champCheck4.CheckColor();
       // champCheck4.UpdateTimerDisplay();

        //System.out.println(count++);
        //System.out.println("Mouse X: " + x + " Mouse Y: " + y);
    }

    public void windowClosing(WindowEvent e) { /* Unimplemented */ }

    public void windowIconified(WindowEvent e) { /* Unimplemented */ }

    public void windowDeiconified(WindowEvent e) { /* Unimplemented */ }

    public void windowActivated(WindowEvent e) { /* Unimplemented */ }

    public void windowDeactivated(WindowEvent e) { /* Unimplemented */ }

    public void nativeKeyPressed(NativeKeyEvent e) {

        if (e.getKeyCode() == NativeKeyEvent.VC_TAB) {

            champCheck1.UpdateTimerDisplay();
            champCheck2.UpdateTimerDisplay();
            champCheck3.UpdateTimerDisplay();
            champCheck4.UpdateTimerDisplay();

        } else {

            champCheck1.HideTimerDisplay();
            champCheck2.HideTimerDisplay();
            champCheck3.HideTimerDisplay();
            champCheck4.HideTimerDisplay();

        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_TAB) {
            champCheck1.HideTimerDisplay();
            champCheck2.HideTimerDisplay();
            champCheck3.HideTimerDisplay();
            champCheck4.HideTimerDisplay();
        }
    }

    public void nativeKeyTyped(NativeKeyEvent e) { /* Unimplemented */ }
}
