package edu.hitsz.bulletCurveStrategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatterStrategy implements AbstractBulletCurveStrategy {

    /**
     * 散射弹道
     */

    private final int shootNum;
    private final int direction;
    private final int power;

    public ScatterStrategy(int power, int shootNum, int direction) {
        this.power = power;
        this.shootNum = shootNum;
        this.direction = direction;
    }

    @Override
    public List<AbstractBullet> shoot(AbstractAircraft aircraft) {
        List<AbstractBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();
        int speedX;
        int speedY = aircraft.getSpeedY() + direction*3;
        AbstractBullet abstractBullet;
        for (int i = 0; i < shootNum; i++) {
            if (i < shootNum / 2) {
                speedX = -2 * (shootNum/2 - i);
            } else {
                speedX = 2 * (i - shootNum/2);
            }
            if (aircraft instanceof HeroAircraft) {
                speedY = aircraft.getSpeedY() + direction*23;
                abstractBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y,
                        speedX, speedY, power);
            } else if (aircraft instanceof BossAircraft || aircraft instanceof EliteEnemy) {
                abstractBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y,
                        speedX, speedY+3, power);
            } else {
                System.out.println("传入的飞机类型有误");
                return res;
            }
            res.add(abstractBullet);
        }
        return res;
    }
}
