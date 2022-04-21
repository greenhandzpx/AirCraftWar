package edu.hitsz.application;


import javax.swing.*;
import java.awt.*;


import edu.hitsz.ui.EndPanel;
import edu.hitsz.ui.StartPanel;
/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // 开始游戏时进入开始游戏界面
        Container startPanel = new StartPanel().panel1;
        frame.setContentPane(startPanel);
        frame.setVisible(true);
        synchronized (startPanel) {
            try {
                startPanel.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 选中难度后进入游戏界面
        frame.setVisible(false);
        System.out.println("开始游戏");
        // 用户选中难度后开始游戏
        frame.remove(startPanel);
        frame = new JFrame("Aircraft War");
        // 获得屏幕的分辨率，初始化 Frame
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        frame.add(game);
        frame.setVisible(true);
        game.action(frame);
        synchronized (frame) {
            try {
                frame.wait();
                System.out.println("醒了");
            } catch (InterruptedException e) {
            }
        }

        Container endPanel = new EndPanel(game.score).panel1;
        frame.setContentPane(endPanel);
        frame.setVisible(true);
    }
}
