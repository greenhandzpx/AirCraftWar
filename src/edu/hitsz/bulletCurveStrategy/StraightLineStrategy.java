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

public class StraightLineStrategy implements AbstractBulletCurveStrategy {
    /**
     * 直线弹道
     */

    private int shootNum;
    private int direction;
    private int power;

    public StraightLineStrategy(int power, int shootNum, int direction) {
        this.power = power;
        this.shootNum = shootNum;
        this.direction = direction;
    }
//    public void setPower(int power) {
//        this.power = power;
//    }
//    public void setShootNum(int shootNum) {
//        this.shootNum = shootNum;
//    }
//    public void setDirection(int direction) {
//        this.direction = direction;
//    }

    @Override
    public List<AbstractBullet> shoot(AbstractAircraft aircraft) {
        List<AbstractBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY();
        int speedX = 0;
        int speedY = aircraft.getSpeedY() + direction*3;
        AbstractBullet abstractBullet;
        for (int i = 0; i < shootNum; i++) {
            if (aircraft instanceof HeroAircraft) {
                speedY = aircraft.getSpeedY() + direction*23;
                abstractBullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y,
                        speedX, speedY, power);
            } else if (aircraft instanceof BossAircraft || aircraft instanceof EliteEnemy) {
                abstractBullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y,
                        speedX, speedY, power);
            } else {
                System.out.println("传入的飞机类型有误");
                return res;
            }
            res.add(abstractBullet);
        }
        return res;
    }

}
