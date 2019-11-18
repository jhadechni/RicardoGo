/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import Entity.Enemies.Snail;
import Entity.Enemy;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.Objects.Coin;
import Entity.Objects.PowerUp;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import Audio.*;
import Entity.Animation;
import Entity.Enemies.Bee;
import Entity.Enemies.Boss;
import Entity.Enemies.Flower;
import Entity.bossExplosion;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jaime Sierra
 */
public class Level1State extends GameState {

    private TileMap tileMap;
    private Background bg;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private ArrayList<bossExplosion> bossExplosions;
    private ArrayList<Coin> coins;
    private ArrayList<PowerUp> Pu;
    private ArrayList<Boss> boss;
    private HUD hud;
    private AudioPlayer bgMusic, enemy;
    private int PlayerScore;

    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
        init();

    }

    @Override
    public void init() {
        tileMap = new TileMap(30);
        tileMap.loadTiles("/Tilesets/grasspane.png");
        tileMap.loadMap("/Maps/map.map");
        tileMap.setPosition(0, 0);
        tileMap.setTween(0.07);

        bg = new Background("/Backgrounds/bggame.gif", 0.1);
        player = new Player(tileMap);
        player.setPosition(100, 100);

        enemies = new ArrayList<Enemy>();
        explosions = new ArrayList<Explosion>();
        bossExplosions = new ArrayList<bossExplosion>();
        coins = new ArrayList<Coin>();
        Pu = new ArrayList<PowerUp>();
        boss = new ArrayList<Boss>();
        populateEnemies();
        populateCoins();
        drawPowerUp();
        drawBoss();
        hud = new HUD(player);
        bgMusic = new AudioPlayer("/Music/bg2.mp3");
        
        enemy = new AudioPlayer("/SFX/explode.mp3");
        bgMusic.play2();

    }

    @Override
    public void update() {
        //update player
        player.update();

        //update TileMape
        tileMap.setPosition(GamePanel.WIDTH / 2 - player.getX(), GamePanel.WIDTH / 2 - player.getY());

        // attack enemies
        player.checkAttack(enemies, coins, Pu, boss);

        //Coins
//        player.checkCoins(power);
        //Set background
        bg.setPosition(tileMap.getX(), tileMap.getY());

        //update Enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
            if (e.isDead()) {
                enemies.remove(i);
                i--;
                player.increaseScore(50);
                explosions.add(new Explosion(e.getX(), e.getY()));
//                System.out.println((int) e.getX());
            }
        }
        //update explosions
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();

            if (explosions.get(i).shouldRemove()) {

                explosions.remove(i);
                i--;
            }
        }
        //update BossExplosion
//        for (int i = 0; i < bossExplosions.size(); i++) {
//            bossExplosions.get(i).update();
//
//            if (bossExplosions.get(i).shouldRemove()) {
//
//                bossExplosions.remove(i);
//                i--;
//            }
//        }

        //update coins
        for (int i = 0; i < coins.size(); i++) {
            Coin Co = coins.get(i);
            Co.update();
            if (Co.isDead()) {
                coins.remove(i);
                i--;

            }
        }

        //update powerUP
        for (int i = 0; i < Pu.size(); i++) {
            PowerUp pu = Pu.get(i);
            pu.update();
            if (pu.isDead()) {
                Pu.remove(i);
                i--;
            }
        }
        //Update boss
        for (int i = 0; i < boss.size(); i++) {
            boss.get(i).update();
            if (boss.get(i).isDead()) {
                boss.remove(i);
                i--;
                player.increaseScore(100);
                explosions.add(new Explosion(boss.get(i).getX(), boss.get(i).getY()));
//                System.out.println((int) boss.get(i).getX());

            }
        }
      
        if (player.isDead()) {
            bgMusic.stop();
            gsm.setState(2);
        }
        if (player.getY()>=220) {
            player.setDead(true);
        }

    }

    @Override
    public void draw(Graphics2D g) {
        //Dibujar fondo
        bg.draw(g);

        //Dibujar tileMap
        tileMap.draw(g);

        //Dibujar player
        player.draw(g);

        //Draw boss
        for (int i = 0; i < boss.size(); i++) {
            boss.get(i).draw(g);
        }

        //Dibujar enemigos
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        //Dibujar explosiones
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).setMapPosition((int) tileMap.getX(), (int) tileMap.getY());
            explosions.get(i).draw(g);
        }
        //Dibujar bossExplosions
//        for (int i = 0; i < bossExplosions.size(); i++) {
//            bossExplosions.get(i).setMapPosition((int) tileMap.getX(), (int) tileMap.getY());
//            bossExplosions.get(i).draw(g);
//        }
        //Draw Coins
        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).draw(g);
        }

        //Draw PowerUp
        for (int i = 0; i < Pu.size(); i++) {
            Pu.get(i).draw(g);
        }

        //Dibujar HUD
        hud.draw(g);

    }

    @Override
    public void keyPressed(int k) {

        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }
        if (k == KeyEvent.VK_UP) {
            player.setUp(true);
        }
        if (k == KeyEvent.VK_DOWN) {
            player.setDown(true);
        }
        if (k == KeyEvent.VK_W) {
            player.setJumping(true);
        }
        if (k == KeyEvent.VK_E) {
            player.setGliding(true);
        }
        if (k == KeyEvent.VK_R) {
            player.setScratching();
        }
        if (k == KeyEvent.VK_F) {
            if (player.isFirePower()) {
                player.setFiring();
            }

        }
    }

    @Override
    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        if (k == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
        if (k == KeyEvent.VK_UP) {
            player.setUp(false);
        }
        if (k == KeyEvent.VK_DOWN) {
            player.setDown(false);
        }
        if (k == KeyEvent.VK_W) {
            player.setJumping(false);
        }
        if (k == KeyEvent.VK_E) {
            player.setGliding(false);
        }
        if (k == KeyEvent.VK_F) {
            if (player.isFirePower()) {
                player.setFiring();
            }

        }
    }

    private void populateEnemies() {

        enemies = new ArrayList<Enemy>();

        Snail s;
        Flower f;
        Bee b;

        Random r;
        Point[] points = new Point[]{
            new Point(200, 60),
            new Point(860, 200),
            new Point(1525, 200),
            new Point(1680, 200),
            new Point(1800, 200),
            new Point(3000, 200),
            new Point(2757, 200),
            new Point(3054, 173)
        };
        Point[] points2 = new Point[]{
            new Point(471, 105),
            new Point(1020, 196),
            new Point(1844, 168),
            new Point(2540, 168),
            new Point(2598, 168)
        };
        Point[] points3= new Point[]{
             new Point(1912, 45),
             new Point(1912, 45),
             new Point(1912, 100),
        };
        for (int i = 0; i < points.length; i++) {
            int numero = (int) (Math.random() * 2 + 1);
            s = new Snail(tileMap, numero);
            s.setPosition(points[i].x, points[i].y);
            enemies.add(s);
        }
        for (int i = 0; i < points2.length; i++) {
            if (points2[i].x != 2598) {
                f = new Flower(tileMap, true);
                f.setPosition(points2[i].x, points2[i].y);
                enemies.add(f);
            } else {
                f = new Flower(tileMap, false);
                f.setPosition(points2[i].x, points2[i].y);
                enemies.add(f);
            }

        }
        for (int i = 0; i < points3.length; i++) {
            b = new Bee(tileMap);
            b.setPosition(points3[i].x, points3[i].y);
            enemies.add(b);
        }

    }

    private void populateCoins() {
        Coin p;

        Point[] points = new Point[]{
            new Point(196, 76),
            new Point(218, 76),
            new Point(240, 76),
            new Point(260, 76),
            new Point(1022, 110),
            new Point(1576, 170),
            new Point(2417, 110),
            new Point(1800, 200),
            new Point(2962, 200),
            new Point(2982, 200),
            new Point(3000, 200),
            new Point(3021, 200),
            new Point(3041, 200),
            new Point(3061, 200),
            new Point(3081, 200),
            new Point(3101, 200),
            new Point(3121, 200),
            new Point(3141, 200),
            //            new Point(3161, 200),

            new Point(2962, 170),
            new Point(2982, 170),
            new Point(3000, 170),
            new Point(3021, 170),
            new Point(3041, 170),
            new Point(3061, 170),
            new Point(3081, 170),
            new Point(3101, 170),
            new Point(3121, 170),
            new Point(3141, 170),
            //            new Point(3161, 710),

            new Point(2962, 130),
            new Point(2982, 130),
            new Point(3021, 130),
            new Point(3041, 130),
            new Point(3061, 130),
            new Point(3081, 130),
            new Point(3101, 130),
            new Point(3121, 130),
            new Point(3141, 130),
            //            new Point(3161, 130),
            new Point(3000, 130)
        };
        for (int i = 0; i < points.length; i++) {

            p = new Coin(tileMap);
            p.setPosition(points[i].x, points[i].y);
            coins.add(p);
        }
    }

    public void drawPowerUp() {
        PowerUp p2;
        Point[] points = new Point[]{
            new Point(1244, 80),
            new Point(1726, 170)
        };
        for (int i = 0; i < points.length; i++) {

            p2 = new PowerUp(tileMap);
            p2.setPosition(points[i].x, points[i].y);
            Pu.add(p2);
        }
    }

    private void drawBoss() {
        Boss b;
        Point[] points = new Point[]{
            new Point(3054, 173)

        };
        for (int i = 0; i < points.length; i++) {

            b = new Boss(tileMap);
            b.setPosition(points[i].x, points[i].y);
            boss.add(b);
        }
    }

}
