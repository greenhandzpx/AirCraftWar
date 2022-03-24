package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;

public class BloodPropFactory extends AbstractPropFactory {

    private final int recoverHp;

    public BloodPropFactory(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.recoverHp = hp;
    }
    @Override
    public AbstractProp createProp() {
        return new BloodProp(locationX, locationY, speedX, speedY, recoverHp);
    }
}
