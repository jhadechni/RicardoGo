/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import GameState.GameStateManager;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author Jaime Sierra
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    /**
     * Dimensiones
     */
    public static final int WIDTH = 350;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    /**
     * Hilos del Juego
     */
    Thread thread;
    boolean running;
    int FPS = 60;
    long targetTime = 1000 / FPS;

    /**
     * Imágenes
     */
    BufferedImage imagen;
    Graphics2D g;
    GameStateManager gsm;

    /**
     * Constructor
     */
    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();

    }

    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }

    private void init() {
        imagen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        g = (Graphics2D) imagen.getGraphics();
        running = true;
        gsm = new GameStateManager();
    }

    public void run() {
        long start;
        long elapsed;
        long wait;

        init();
        /**
         * Game loop
         */
        while (running) {
            start = System.nanoTime();

            update();
            draw();
            drawToScreen();

            elapsed = System.nanoTime() - start;

            wait = targetTime - elapsed / 1000000;
            if (wait<0) {
                wait=6;
            }
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());
    }

    private void update() {
        gsm.update();
    }

    private void draw() {
        gsm.draw(g);
    }

    private void drawToScreen() {
        Graphics2D g2 = (Graphics2D) getGraphics();
        g2.drawImage(imagen, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        g2.dispose();
    }

}
