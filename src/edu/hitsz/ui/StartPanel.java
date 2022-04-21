package edu.hitsz.ui;

import edu.hitsz.application.ImageManager;
import edu.hitsz.musicThread.MusicThread;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 游戏难度
 */
enum Difficulty {
    EASY,
    NORMAL,
    HARD
}

public class StartPanel {
    public JPanel panel1;
    private JButton button1;
    private JPanel bottomPanel;
    private JButton button2;
    private JButton button3;
    private JComboBox comboBox1;
    private JLabel voiceLabel;
    private JPanel topPanel;


    public static boolean EXIT = false;
    public static Difficulty DIFFICULTY = Difficulty.EASY;

    public StartPanel() {

        comboBox1.setSelectedIndex(1);
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Thread thread = new MusicThread("src/videos/bullet_hit.wav");
                if (comboBox1.getSelectedIndex() == 0) {
                    System.out.println("开！");
                    StartPanel.EXIT = false;
                    Thread thread = new MusicThread("src/videos/bgm.wav", 1) {
                        @Override
                        public void run() {
                            while (!StartPanel.EXIT) {
                                InputStream stream = new ByteArrayInputStream(this.samples);
                                play(stream);
                            }
                        }
                    };
                    thread.start();
//                    Runnable r = () -> {
//                        while (!StartPanel.EXIT) {
//                            Thread thread = new MusicThread("src/videos/bgm.wav");
//                            thread.start();
//                            try {
//                                thread.join();
//                            } catch (InterruptedException exception) {
//                            }
//                        }
//                    };
//                    new Thread(r, "repeat music").start();

                } else {
                    System.out.println("关闭");
                    // TODO
                    StartPanel.EXIT = true;
//                    thread.interrupt();
                }
            }
        } );
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageManager.bgImg = "src/images/bg.jpg" ;
                DIFFICULTY = Difficulty.EASY;
                synchronized (panel1) {
                    panel1.notify();
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg2.jpg"));
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
                DIFFICULTY = Difficulty.NORMAL;
                synchronized (panel1) {
                    panel1.notify();
                }

            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageManager.BACKGROUND_IMAGE = ImageIO.read(new FileInputStream("src/images/bg3.jpg"));
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
                DIFFICULTY = Difficulty.HARD;
                synchronized (panel1) {
                    panel1.notify();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("StartPanel");
        frame.setContentPane(new StartPanel().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
