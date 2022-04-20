package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.musicThread.MusicThread;

/**
 * @author greenhandzpx
 */
public class BloodProp extends AbstractProp {

    private final int recoverHp = 20;
    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void propCallback(HeroAircraft hero) {
        new MusicThread("src/videos/get_supply.wav", 0).start();
        hero.decreaseHp(-recoverHp);
    }

}
