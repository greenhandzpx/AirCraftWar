package edu.hitsz.prop;


import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.aircraft.HeroAircraft;
/**
 * 道具类
 */
public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {

    }

    /**
     * 道具的功能回调
     *
     */
    public abstract void propCallback(HeroAircraft hero);
}
