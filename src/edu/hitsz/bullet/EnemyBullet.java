package edu.hitsz.bullet;

import edu.hitsz.observerPattern.Subscriber;

/**
 * @Author hitsz
 */
public class EnemyBullet extends AbstractBullet implements Subscriber {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void update() {
        vanish();
    }
}
