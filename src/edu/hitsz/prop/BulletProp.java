package edu.hitsz.prop;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bulletCurveStrategy.ScatterStrategy;

/**
 * @author greenhandzpx
 */
public class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void propCallback(HeroAircraft hero) {
        hero.setShootNum(5);
        hero.setBulletCurveStrategy(new ScatterStrategy(hero.getPower(), hero.getShootNum(), hero.getDirection()));
        System.out.println("FireSupply active!");
    }

}
