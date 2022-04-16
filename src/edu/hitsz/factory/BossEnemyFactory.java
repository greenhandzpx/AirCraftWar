package edu.hitsz.factory;

import edu.hitsz.aircraft.BossAircraft;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.prop.AbstractProp;

import java.util.List;

public class BossEnemyFactory extends AbstractEnemyFactory{

    private List<AbstractProp> props;
    public BossEnemyFactory(List<AbstractProp> props) {
        this.props = props;
    }

    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new BossAircraft(locationX, locationY, speedX, speedY, hp, props);
    }
}
