package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

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
         hero.decreaseHp(-recoverHp);
    }

}
