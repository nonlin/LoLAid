/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lolaid;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author George
 */
public class CheckForUlts extends TimerTask{
    
        UltTimer champCheck1;// = new UltTimer("./sounds/champ1green.wav", 47, 195);
        UltTimer champCheck2; //= new UltTimer("./sounds/champ2green.wav", 47, 275);
        UltTimer champCheck3; //= new UltTimer("./sounds/champ3green.wav", 47, 355);
        UltTimer champCheck4; //= new UltTimer("./sounds/champ4green.wav", 47, 435);    
        
    public CheckForUlts(int x, int y, int nextY){
        
        champCheck1 = new UltTimer("./sounds/champ1green.wav", x, y);
        champCheck2 = new UltTimer("./sounds/champ2green.wav", x, y + nextY);
        champCheck3 = new UltTimer("./sounds/champ3green.wav", x, y + (nextY * 2));
        champCheck4 = new UltTimer("./sounds/champ4green.wav", x, y + (nextY * 3));
    }
    
 
    public void run() {
  
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        champCheck1.CheckColor();
        champCheck2.CheckColor();
        champCheck3.CheckColor();
        champCheck4.CheckColor();
        //System.out.println("Mouse X: " + x + " Mouse Y: " + y);
        
    }
}
