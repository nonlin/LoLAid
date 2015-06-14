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
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author George
 */
public class CheckForUlts extends TimerTask {

    UltTimer champCheck1; // = new UltTimer("./sounds/champ1green.wav", 47, 195);
    UltTimer champCheck2; //= new UltTimer("./sounds/champ2green.wav", 47, 275);
    UltTimer champCheck3; //= new UltTimer("./sounds/champ3green.wav", 47, 355);
    UltTimer champCheck4; //= new UltTimer("./sounds/champ4green.wav", 47, 435); 

    int count = 0;

    public CheckForUlts(int x, int y, int nextY, boolean playUp, boolean playDown, String soundC1, String soundC2, String soundC3, String soundC4) {

        champCheck1 = new UltTimer("./sounds/Up/" + soundC1 + "Up.wav", "./sounds/Down/" + soundC1 + "Down.wav", x, y, playDown);
        champCheck2 = new UltTimer("./sounds/Up/" + soundC2 + "Up.wav", "./sounds/Down/" + soundC2 + "Down.wav", x, y + nextY, playDown);
        champCheck3 = new UltTimer("./sounds/Up/" + soundC3 + "Up.wav", "./sounds/Down/" + soundC3 + "Down.wav", x, y + (nextY * 2), playDown);
        champCheck4 = new UltTimer("./sounds/Up/" + soundC4 + "Up.wav", "./sounds/Down/" + soundC4 + "Down.wav", x, y + (nextY * 3), playDown);
    }

    public void run() {

        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        champCheck1.CheckColor();
        champCheck2.CheckColor();
        champCheck3.CheckColor();
        champCheck4.CheckColor();
		//System.out.println(count++);
        //System.out.println("Mouse X: " + x + " Mouse Y: " + y);

    }
}
