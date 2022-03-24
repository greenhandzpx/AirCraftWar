package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BulletProp;

public class BulletPropFactory extends AbstractPropFactory {

    BulletPropFactory(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public AbstractProp createProp() {
        return new BulletProp(locationX, locationY, speedX, speedY);
    }

}
