package edu.hitsz.factory;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.prop.AbstractProp;

import java.util.List;

public class EliteEnemyFactory extends AbstractEnemyFactory {

    private List<AbstractProp> props;
    public EliteEnemyFactory(List<AbstractProp> props) {
        this.props = props;
    }
    @Override
    public AbstractAircraft createEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp, props);
    }
}
