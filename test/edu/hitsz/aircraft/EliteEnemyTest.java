package edu.hitsz.aircraft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {

    EliteEnemy eliteEnemy;

    @BeforeEach
    void setUp() {
        eliteEnemy = new EliteEnemy(10, 10, 0, -10, 50);
    }

    // 给定三次不同的参数，验证飞机的血量情况
    @DisplayName("Test EliteEnemy.decreaseHp method")
    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30})
    void decreaseHp(int hp) {
        int oldHp = eliteEnemy.getHp();
        eliteEnemy.decreaseHp(hp);
        int newHp = eliteEnemy.getHp();
        assertEquals(oldHp - hp, newHp);
    }

    @DisplayName("Test EliteEnemy.forward method")
    @Test
    void forward() {
        int speedY = eliteEnemy.getSpeedY();
        for (int i = 0; i < 3; i++) {
            // 重复三秒，验证飞机的位置情况
            int oldY = eliteEnemy.getLocationY();
            eliteEnemy.forward();
            int newY = eliteEnemy.getLocationY();
            assertEquals(oldY + speedY, newY);
        }
    }
}