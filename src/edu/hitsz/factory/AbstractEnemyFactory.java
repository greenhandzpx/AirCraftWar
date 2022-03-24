package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;

public abstract class AbstractEnemyFactory {
    protected int locationX;
    protected int locationY;
    protected int speedX;
    protected int speedY;
    protected int hp;

    public AbstractEnemyFactory(int locationX, int locationY, int speedX, int speedY, int hp) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.hp = hp;
    }
    public abstract AbstractAircraft createEnemy();
}
