package edu.hitsz.application;

public class NormalGame extends Game {
    public NormalGame() {
        super();
        this.bossHp = 1000;
    }

    @Override
    protected void updateConfig() {
        if (this.time % 10000 == 0) {
            eliteHp += 5;
            enemySpeedY += 2;
            enemyMaxNumber += 1;
            if (cycleDuration >= 300) {
                cycleDuration -= 10;
            }
            if (bossScoreThreshold >= 60) {
                bossScoreThreshold -= 5;
            }

            System.out.println("游戏难度提升！");
            System.out.printf("精英机血量:%d ", eliteHp);
            System.out.printf("敌机速度:%d ", enemySpeedY);
            System.out.printf("敌机最大数量:%d ", enemyMaxNumber);
            System.out.printf("敌机射击频率:%d ", cycleDuration);
            System.out.printf("Boss机出现的分数间隔:%d\n", bossScoreThreshold);
        }
    }

}
