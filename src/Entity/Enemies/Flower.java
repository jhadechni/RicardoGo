/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Enemies;

import Entity.Animation;
import Entity.Enemy;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jaime Sierra
 */
public class Flower extends Enemy {
    
    private BufferedImage[] sprites;
 
    /**
     * Constructor
     *
     * @param tileMap - Mapa
     */
    public Flower(TileMap tileMap, boolean side) {
        super(tileMap);

        moveSpeed = 0.3;
        maxSpeed = 0.3;
        gravity = 0.2;
        maxGravity = 10.0;

        width = 61;
        height = 45;
        cwidth = 40;
        cheight = 26;

        health = maxHealth = 3;
        damage = 1;

        // load sprites
       
        try {

            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/piraña.png"));

            sprites = new BufferedImage[4];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(300);

//        right = true;
        facingRight = side;
    }

    /**
     * Obtiene la siguiente posición del personaje
     */
//    private void getNextPosition() {
//
//        // movement
//        if (left) {
//            dx -= moveSpeed;
//            if (dx < -maxSpeed) {
//                dx = -maxSpeed;
//            }
//        } else if (right) {
//            dx += moveSpeed;
//            if (dx > maxSpeed) {
//                dx = maxSpeed;
//            }
//        }
//
//        // falling
//        if (falling) {
//            dy += gravity;
//        }
//
//    }

    /**
     * Actualiza al personaje si ocurre una acción
     */
    @Override
    public void update() {

        // update position
//        getNextPosition();
        checkTileMapCollision();
        setPosition(tempX, tempY);

        // check flinching
        if (flinching) {
            long elapsed= (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 400) {
                flinching = false;
            }
        }

//        // Si choca cambia la dirección.
//        if (right && dx == 0) {
//            right = false;
//            left = true;
//            facingRight = false;
//        } else if (left && dx == 0) {
//            right = true;
//            left = false;
//            facingRight = true;
//        }

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
