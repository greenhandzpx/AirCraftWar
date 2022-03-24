package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombProp;

public class BombPropFactory extends AbstractPropFactory {

    public BombPropFactory(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public AbstractProp createProp() {
        return new BombProp(locationX, locationY, speedX, speedY);
    }
}
