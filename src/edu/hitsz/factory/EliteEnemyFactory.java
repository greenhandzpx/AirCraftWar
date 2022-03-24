package edu.hitsz.factory;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.AbstractAircraft;

public class EliteEnemyFactory extends AbstractEnemyFactory {
//    private final int locationX;
//    private final int locationY;
//    private final int speedX;
//    private final int speedY;
//    private final int hp;
//
    public EliteEnemyFactory(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
//        this.locationX = locationX;
//        this.locationY = locationY;
//        this.speedX = speedX;
//        this.speedY = speedY;
//        this.hp = hp;
    }
    @Override
    public AbstractAircraft createEnemy() {
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
