/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Enemies;

import Entity.*;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jaime Sierra
 */
public class Snail extends Enemy {

    private BufferedImage[] sprites;
    String s;
    /**
     * Constructor
     *
     * @param tileMap - Mapa
     */
    public Snail(TileMap tileMap, int d) {
        super(tileMap);

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        gravity = 0.2;
        maxGravity = 10.0;

        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        health = maxHealth = 3;
        damage = 1;

        // load sprites
        switch (d) {
            case 1:
                s="/SpritesEnemies/appleSnail.png";
                break;
            case 2:
                s="/SpritesEnemies/cubanSnail.png";
        }
        try {

            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(s));

            sprites = new BufferedImage[3];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

        right = true;
        facingRight = true;
    }

    /**
     * Obtiene la siguiente posición del personaje
     */
    private void getNextPosition() {

        // movement
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        }

        // falling
        if (falling) {
            dy += gravity;
        }

    }

    /**
     * Actualiza al personaje si ocurre una acción
     */
    @Override
    public void update() {

        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(tempX, tempY);

        // check flinching
        if (flinching) {
            long elapsed= (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }

        // Si choca cambia la dirección.
        if (right && dx == 0) {
            right = false;
            left = true;
            facingRight = false;
        } else if (left && dx == 0) {
            right = true;
            left = false;
            facingRight = true;
        }

        // update animation
        animation.update();

    }

    /**
     *
     * @param g - Representa el panel en donde se está dibujando
     */
    @Override
    public void draw(Graphics2D g) {

        //if(notOnScreen()) return;
        setMapPosition();

        super.draw(g);

    }

}
