package edu.hitsz.application;

import edu.hitsz.musicThread.MusicThread;
import edu.hitsz.observerPattern.Subscriber;
import edu.hitsz.prop.BombProp;
import edu.hitsz.record.FileRecordDAOImpl;
import edu.hitsz.record.RecordDAO;
import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.prop.AbstractProp;

import edu.hitsz.factory.*;

import edu.hitsz.ui.StartPanel;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    private int backGroundTop = 0;


    /**
     * 敌机纵向速度
     */
    protected int enemySpeedY = 5;
    /**
     * Boss 血量
     */
    protected int bossHp = 1000;

    /**
     * 精英敌机血量
     */
    protected int eliteHp = 50;
    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * Boss机出现需要的分数
     */
    protected int bossScoreThreshold = 100;
    /**
     * 敌机的最大数量
     */
    protected int enemyMaxNumber = 5;
    /**
     * 标记是否已经有Boss机
     */
    private boolean hasBoss = false;
    /**
     * 时间间隔(ms)，控制刷新频率
     */
    //private int timeInterval = 40;
    private final int timeInterval = 20;

    /**
     * 文件形式记录分数及排名
     */
    private final RecordDAO recordDAOImpl = new FileRecordDAOImpl("hardRecords.csv");

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<AbstractBullet> heroBullets;
    private final List<AbstractBullet> enemyBullets;
    private final List<AbstractProp> props;


    private boolean gameOverFlag = false;
    public int score = 0;
    protected int time = 0;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 600;
    private int cycleTime = 0;

    /**
     * 创建相应的工厂
     */
//    private final BulletPropFactory bulletPropFactory = new BulletPropFactory();
//    private final BloodPropFactory bloodPropFactory = new BloodPropFactory();
//    private final BombPropFactory bombPropFactory = new BombPropFactory();

    private final MobEnemyFactory mobEnemyFactory = new MobEnemyFactory();
    private final EliteEnemyFactory eliteEnemyFactory;
    private final BossEnemyFactory bossEnemyFactory;


    /**
     * 用来配置参数
     */
    public void config() {

    }

    public Game() {
        heroAircraft = HeroAircraft.getInstance(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                0, 0, 500);

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        eliteEnemyFactory = new EliteEnemyFactory(props);
        bossEnemyFactory = new BossEnemyFactory(props);
        /*
          Scheduled 线程池，用于定时任务调度
          关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
          apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action(JFrame frame) {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    Random r = new Random();
                    if (r.nextBoolean()) {
                        enemyAircrafts.add(mobEnemyFactory.createEnemy(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                                0,
                                enemySpeedY,
                                30
                        ));
                    } else {
                        enemyAircrafts.add(eliteEnemyFactory.createEnemy(
                                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                                // 横向速度为+-2
                                -6 + 4 * (new Random()).nextInt(2),
                                enemySpeedY,
                                eliteHp
                        ));
                    }
                }

                // 飞机射出子弹
                shootAction();
            }

            // 判断是否需要更新难度
            updateConfig();

            // Boss机产生
            createBoss();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();
            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                new MusicThread("src/videos/game_over.wav", 0).start();
                System.out.println("Game Over!");

                // 等待500ms让游戏结束音效生效，再停止音乐
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 停掉背景音乐
                StartPanel.EXIT = true;
                // 通知切换为结束页面
                synchronized (frame) {
                    frame.notify();
                }

//                // 将当前日期转为String格式
//                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
//                String date = sdf.format(new Date());
//                // 新建一份记录
//                Record record = new Record("testUser", score, date);
//                // 添加到历史记录
//                recordDAOImpl.addRecord(record);
//                // 拿到所有历史记录
//                List<String[]> records = recordDAOImpl.getAllRecords();
//
//                System.out.println("*********************************************************");
//                System.out.println("                        得分排行榜");
//                System.out.println("*********************************************************");
//                for (String[] oldRecord: records) {
//                    System.out.println(String.join(", ", oldRecord));
//                }
            }

        };

        /*
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    /**
     * 检查是否需要更新参数
     */
    abstract protected void updateConfig();

    protected boolean createBoss() {
        if (score > 0 && (score % bossScoreThreshold == 0) && !hasBoss) {
            enemyAircrafts.add(bossEnemyFactory.createEnemy(
                    (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                    (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                    5,
                    0,
                    bossHp
            ));
            hasBoss = true;
            return true;
        }
        return false;
    }
    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击
        for (AbstractAircraft aircraft : enemyAircrafts) {
            // 减缓敌机射击频率
            Random r = new Random();
            if (r.nextBoolean()) {
                enemyBullets.addAll(aircraft.shoot());
            }
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
        heroBullets.addAll(heroAircraft.shoot());
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (AbstractBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (AbstractBullet bullet : enemyBullets) {
            bullet.forward();
        }
        for (AbstractProp prop: props) {
            prop.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (AbstractBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机撞击到子弹
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (AbstractBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    new MusicThread("src/videos/bullet_hit.wav", 0).start();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给

                        score += 10;

                        if (enemyAircraft instanceof BossAircraft) {
                            hasBoss = false;
                        }

                    }

                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop: props) {
            if (prop.notValid()) {
                continue;
            }
            // 英雄机碰撞道具
            if (heroAircraft.crash(prop)) {
                // 触发道具回调
                if (prop instanceof BombProp) {
                    // 如果是炸弹道具
                    for (AbstractAircraft aircraft: enemyAircrafts) {
                        if (aircraft instanceof Subscriber && !aircraft.notValid()) {
                            // 如果是订阅者
                            ((BombProp) prop).subscribe((Subscriber) aircraft);
                            score += 10;
                        }
                    }
                    // 加入敌机子弹
                    for (AbstractBullet enemyBullet: enemyBullets) {
                        if (!enemyBullet.notValid()) {
                            ((BombProp) prop).subscribe((Subscriber) enemyBullet);
                        }
                    }
                }
                prop.propCallback(heroAircraft);
                prop.vanish();
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        // 绘制道具
        paintImageWithPositionRevised(g, props);

        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
