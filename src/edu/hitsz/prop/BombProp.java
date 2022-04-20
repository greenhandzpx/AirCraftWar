
package edu.hitsz.prop;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.musicThread.MusicThread;

public class BombProp extends AbstractProp {

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void propCallback(HeroAircraft hero) {
        new MusicThread("src/videos/bomb_explosion.wav", 0).start();
        System.out.println("BombSupply active!");
    }

}
