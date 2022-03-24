package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;

public abstract class AbstractPropFactory {
    protected int locationX;
    protected int locationY;
    protected int speedX;
    protected int speedY;

    public AbstractPropFactory(int locationX, int locationY, int speedX, int speedY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
    }
    public abstract AbstractProp createProp();
}
