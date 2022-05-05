
package edu.hitsz.prop;
import com.sun.source.tree.Tree;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.musicThread.MusicThread;
import edu.hitsz.observerPattern.Subscriber;

import java.util.HashSet;
import java.util.TreeSet;

public class BombProp extends AbstractProp {

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
        subscribers = new HashSet<>();
    }

    @Override
    public void propCallback(HeroAircraft hero) {
        new MusicThread("src/videos/bomb_explosion.wav", 0).start();
        notifySubscribers();
    }

    private final HashSet<Subscriber> subscribers;

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        if (!subscribers.contains(subscriber)) {
            System.out.println("不存在该订阅者");
            return;
        }
        subscribers.remove(subscriber);
    }

    public void notifySubscribers() {
        System.out.println(subscribers.size());
        for (Subscriber subscriber : subscribers) {
            subscriber.update();
//            System.out.println("update all observers");
//            unsubscribe(subscriber);
        }
    }

}
