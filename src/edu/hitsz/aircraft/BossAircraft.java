package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bulletCurveStrategy.AbstractBulletCurveStrategy;
import edu.hitsz.bulletCurveStrategy.ScatterStrategy;
import edu.hitsz.musicThread.MusicThread;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodProp;
import edu.hitsz.prop.BombProp;
import edu.hitsz.prop.BulletProp;
import edu.hitsz.ui.StartPanel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 *@author greenhandzpx
 */
public class BossAircraft extends AbstractAircraft {

    public static boolean STOP_BOSS_BGM = true;

    private List<AbstractProp> props;

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    int shootNum = 5;
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

        setBulletCurveStrategy(new ScatterStrategy(power, shootNum, direction));
//        this.bulletCurveStrategy = new ScatterStrategy(power, shootNum, direction);
        this.props = props;

        BossAircraft.STOP_BOSS_BGM = false;
        Thread thread = new MusicThread("src/videos/bgm_boss.wav", 2) {
            @Override
            public void run() {
                while (!StartPanel.EXIT && !BossAircraft.STOP_BOSS_BGM) {
                    InputStream stream = new ByteArrayInputStream(this.samples);
                    play(stream);
                }
            }
        };
        thread.start();

    }

    public void setBulletCurveStrategy(AbstractBulletCurveStrategy bulletCurveStrategy) {
        this.bulletCurveStrategy = bulletCurveStrategy;
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

//    @Override
//    public List<AbstractBullet> shoot() {
//        return bulletCurveStrategy.shoot(this);
//    }

    @Override
    public void vanish() {
        // boss机死亡掉落三种道具
        props.add(new BulletProp(locationX, locationY, 0, 5));
        props.add(new BloodProp(locationX, locationY, 0, 5));
        props.add(new BombProp(locationX, locationY, 0, 5));
        BossAircraft.STOP_BOSS_BGM = true;
        super.vanish();
    }

}
