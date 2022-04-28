package edu.hitsz.prop;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bulletCurveStrategy.ScatterStrategy;
import edu.hitsz.bulletCurveStrategy.StraightLineStrategy;
import edu.hitsz.musicThread.MusicThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author greenhandzpx
 */
public class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void propCallback(HeroAircraft hero) {
        new MusicThread("src/videos/get_supply.wav", 0).start();

        Runnable r = () -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrupted");
            }
            Lock lock = new ReentrantLock();
            lock.lock();
            try {
                hero.setShootNum(hero.getShootNum()-2);
                hero.setBulletCurveStrategy(new StraightLineStrategy(hero.getPower(), hero.getShootNum(), hero.getDirection()));
            } finally {
                lock.unlock();
            }
        };

        new Thread(r, "Thread for timeout").start();

        hero.setShootNum(hero.getShootNum()+2);
        hero.setBulletCurveStrategy(new ScatterStrategy(hero.getPower(), hero.getShootNum(), hero.getDirection()));
        System.out.println("FireSupply active!");
    }

}
