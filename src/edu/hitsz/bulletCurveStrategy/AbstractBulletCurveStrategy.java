package edu.hitsz.bulletCurveStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

/**
 * @author greenhandzpx
 */
public interface AbstractBulletCurveStrategy {
    /**
     * @param aircraft 发射子弹的飞机
     * @return 返回一串子弹
     */
    List<AbstractBullet> shoot(AbstractAircraft aircraft);

}
