package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;

public class MobEnemyFactory extends AbstractEnemyFactory {

//    private final int locationX;
//    private final int locationY;
//    private final int speedX;
//    private final int speedY;
//    private final int hp;
//
    public MobEnemyFactory(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }
    @Override
    public AbstractAircraft createEnemy() {
        return new MobEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
