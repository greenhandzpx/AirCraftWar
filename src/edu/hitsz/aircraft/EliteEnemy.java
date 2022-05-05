package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bulletCurveStrategy.AbstractBulletCurveStrategy;
import edu.hitsz.bulletCurveStrategy.StraightLineStrategy;
import edu.hitsz.observerPattern.Subscriber;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.prop.BulletProp;

import java.util.List;
import java.util.Random;

/**
 * @author greenhandzpx
 */
public class EliteEnemy extends AbstractAircraft implements Subscriber {
    /**攻击方式 */
    private final AbstractBulletCurveStrategy bulletCurveStrategy;

    private final List<AbstractProp> props;

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY,
                      int hp, List<AbstractProp> props) {
        super(locationX, locationY, speedX, speedY, hp);
        // 子弹一次发射数量
        int shootNum = 2;
        // 子弹伤害
        int power = 10;
        // 子弹射击方向 (向上发射：1，向下发射：-1)
        int direction = 1;
        this.bulletCurveStrategy = new StraightLineStrategy(power, shootNum, direction);
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
        // 随机掉落一种道具或不掉落
        Random r = new Random();
        int choice = r.nextInt(4);
        // 三种情况分别掉落不同的道具
        if (choice == 1) {
            props.add(new BombProp(locationX, locationY, 0, 5));
        } else if (choice == 2) {
            props.add(new BloodProp(locationX, locationY, 0, 5));
        } else if (choice == 3) {
            props.add(new BulletProp(locationX, locationY, 0, 5));
        }
        super.vanish();
    }

    @Override
    public void update() {
        // 当发布者状态变化时，调用该方法
//        System.out.println("call update in elite");
        super.vanish();
    }
}
