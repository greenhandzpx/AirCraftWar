package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bulletCurveStrategy.AbstractBulletCurveStrategy;
import edu.hitsz.bulletCurveStrategy.StraightLineStrategy;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private AbstractBulletCurveStrategy bulletCurveStrategy;

    private volatile static HeroAircraft instance;

    /**
     * 子弹一次发射数量
     */
    int shootNum = 1;
    /**
     * 子弹伤害
     */
    int power = 20;
    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    int direction = -1;
    public int getShootNum() {
        return shootNum;
    }
    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }
    public int getPower() {
        return power;
    }
    public int getDirection() {
        return direction;
    }
    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);

        this.bulletCurveStrategy = new StraightLineStrategy(power, shootNum, direction);
    }

    public static HeroAircraft getInstance(int locationX, int locationY, int speedX, int speedY, int hp) {
        if (instance == null) {
            synchronized (HeroAircraft.class) {
                if (instance == null) {
                    instance = new HeroAircraft(locationX, locationY, speedX, speedY, hp);
                }
            }
        }
        return instance;
    }
    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    public void setBulletCurveStrategy(AbstractBulletCurveStrategy bulletCurveStrategy) {
        this.bulletCurveStrategy = bulletCurveStrategy;
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<AbstractBullet> shoot() {
        return bulletCurveStrategy.shoot(this);
    }

}
