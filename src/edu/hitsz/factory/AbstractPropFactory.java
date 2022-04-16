package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;

public abstract class AbstractPropFactory {
    public AbstractPropFactory() {
    }
    public abstract AbstractProp createProp(int locationX, int locationY, int speedX, int speedY);
}
