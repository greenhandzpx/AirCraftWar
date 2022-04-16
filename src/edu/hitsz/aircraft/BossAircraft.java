package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bulletCurveStrategy.AbstractBulletCurveStrategy;
import edu.hitsz.bulletCurveStrategy.ScatterStrategy;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.prop.BulletProp;

import java.util.List;
import java.util.Random;

/**
 *@author greenhandzpx
 */
public class BossAircraft extends AbstractAircraft {


    private List<AbstractProp> props;

    /**攻击方式 */
    private final AbstractBulletCurveStrategy bulletCurveStrategy;

    /**
     * 子弹一次发射数量
     */
    int shootNum = 2;
    /**
     * 子弹伤害
     */
    int power = 10;
    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    int direction = 1;

    public int getPower() {
        return power;
    }
    public int getDirection() {
        return direction;
    }

    public BossAircraft(int locationX, int locationY, int speedX, int speedY,
                        int hp, List<AbstractProp> props) {
        super(locationX, locationY, speedX, speedY, hp);
        this.bulletCurveStrategy = new ScatterStrategy(power, shootNum, direction);
        this.props = props;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<AbstractBullet> shoot() {
        return bulletCurveStrategy.shoot(this);
    }

    @Override
    public void vanish() {
        // boss机死亡掉落三种道具
        props.add(new BulletProp(locationX, locationY, 0, 5));
        props.add(new BloodProp(locationX, locationY, 0, 5));
        props.add(new BombProp(locationX, locationY, 0, 5));
        super.vanish();
    }

}
