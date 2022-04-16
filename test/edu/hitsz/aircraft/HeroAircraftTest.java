package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        heroAircraft = HeroAircraft.getInstance(20, 10, 0, 10, 50);
    }

    @DisplayName("Test heroAircraft.getInstance method")
    @Test
    void getInstance() {
        // 传入不同的参数
        HeroAircraft heroAircraftCopy = HeroAircraft.getInstance(10, 50, 0, 10, 50);

        // 判断返回的对象地址是否相同
        assertEquals (heroAircraftCopy, heroAircraft);
    }

    @DisplayName("Test HeroAircraft.shoot method")
    @Test
    void shoot() {

        // 验证shoot方法是否返回一个列表
        assert (heroAircraft.shoot() instanceof List);
        List<AbstractBullet> bullets = heroAircraft.shoot();
        // 验证shoot方法返回的子弹列表不为NULL
        assertNotNull (bullets);
        // 验证shoot方法返回的子弹个数是否大于0
        assert (bullets.size() > 0) ;
        for (AbstractBullet bullet: bullets) {
            // 验证shoot方法返回的子弹是否为HeroBullet的实例
            assert (bullet instanceof HeroBullet);
        }
    }
}