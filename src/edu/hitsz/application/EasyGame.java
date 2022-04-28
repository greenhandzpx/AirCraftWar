package edu.hitsz.application;

public class EasyGame extends Game {

    public EasyGame() {
        super();

    }

    @Override
    protected boolean createBoss() {
        return false;
    }

    @Override
    protected void updateConfig() {
    }
}
