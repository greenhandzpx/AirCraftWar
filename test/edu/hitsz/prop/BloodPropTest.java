package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloodPropTest {

    BloodProp bloodProp;

    @BeforeEach
    void setUp() {
        bloodProp = new BloodProp(20, 20, 0, 10);
    }

    @DisplayName("Test BloodProp.vanish method")
    @Test
    void vanish() {
        bloodProp.vanish();
        assert(bloodProp.notValid());
    }

    @DisplayName("Test BloodProp.propCallback method")
    @Test
    void propCallback() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance(20, 20, 10, 10, 30) ;
        int oldHp = heroAircraft.getHp();
        bloodProp.propCallback(heroAircraft);
        int newHp = heroAircraft.getHp();
        // 由于上面设定了加血道具每次加20点血
        assertEquals(oldHp + 20, newHp);
    }
}