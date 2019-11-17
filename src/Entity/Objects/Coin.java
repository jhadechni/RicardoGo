/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.Objects;

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
public class Coin extends Enemy {
    private BufferedImage[] sprites;
    
    public Coin(TileMap tileMap) {
        super(tileMap);
        
        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;
        
        //load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/coins.png/"));

            sprites = new BufferedImage[3];
            for (int i = 0; i < sprites.length; i++) {
                if (i!=1) {
                    sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
                }
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(100);

        right = true;
        facingRight = true;
    }
   
    @Override
    public void update() {

        // update position
        checkTileMapCollision();
        setPosition(tempX, tempY);
        

//        // check flinching
//        if (flinching) {
//            long elapsed= (System.nanoTime() - flinchTimer) / 1000000;
//            if (elapsed > 400) {
//                flinching = false;
//            }
//        }
//
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
    @Override
     public void draw(Graphics2D g) {

        //if(notOnScreen()) return;
        setMapPosition();

        super.draw(g);

    }
}
