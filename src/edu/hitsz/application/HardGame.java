package edu.hitsz.application;

public class HardGame extends Game {

    public HardGame() {
        super();
        this.enemyMaxNumber = 7;
        this.bossHp = 1500;
        this.bossScoreThreshold = 100;
        this.cycleDuration = 500;
    }

    @Override
    public boolean createBoss() {
        if (super.createBoss()) {
            // 说明创建boss成功
            this.bossHp += 500;
            return true;
        }
        return false;
    }

    @Override
    protected void updateConfig() {
        if (this.time % 10000 == 0) {
            System.out.println("游戏难度提升！");
            eliteHp += 10;
            enemySpeedY += 4;
            enemyMaxNumber += 3;
            if (cycleDuration >= 300) {
                cycleDuration -= 20;
            }
            if (bossScoreThreshold >= 60) {
                bossScoreThreshold -= 10;
            }
        }
    }

}
