package edu.hitsz.bulletCurveStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.AbstractBullet;

import java.util.ArrayList;
import java.util.List;

/**
 * 无子弹发射
 */
public class NoBulletStrategy implements AbstractBulletCurveStrategy {
    /**
     *
      * @param aircraft 发射子弹的飞机
     * @return 空列表
     */
    @Override
    public List<AbstractBullet> shoot(AbstractAircraft aircraft) {
        return new ArrayList<>();
    }
}
