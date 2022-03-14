package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BloodProp extends AbstractProp {

    private int recoverHp;
    public BloodProp(int locationX, int locationY, int speedX, int speedY, int recoverHp) {
        super(locationX, locationY, speedX, speedY);
        this.recoverHp = recoverHp;
    }

    @Override
    public void propCallback(HeroAircraft hero) {
         hero.decreaseHp(-recoverHp);
    }

}
