/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Audio.AudioPlayer;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jaime Sierra
 */
class FireBall extends MapObject {

    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprites;
    private BufferedImage[] hitSprites;
  

    /**
     *
     * @param tileMap
     * @param right
     */
    public FireBall(TileMap tileMap, boolean right) {
        super(tileMap);
        facingRight = right;

        moveSpeed = 3.8;
        if (right) {
            dx = moveSpeed;
        } else {
            dx = -moveSpeed;
        }

        width = 30;
        height = 30;
        cwidth = 14;
        cheight = 14;

        // load sprites
        try {

            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/SpritesPlayer/fireball.gif"));

            sprites = new BufferedImage[4];
            for (int i = 0; i < sprites.length; i++) {
                sprites[i] = spritesheet.getSubimage(i * width, 0, width, height);
            }

            hitSprites = new BufferedImage[3];
            for (int i = 0; i < hitSprites.length; i++) {
                hitSprites[i] = spritesheet.getSubimage(i * width, height, width, height);
            }
           

            animation = new Animation();
            animation.setFrames(sprites);
            animation.setDelay(70);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void setHit() {
        if (hit) {
            return;
        }
        hit = true;
        animation.setFrames(hitSprites);
        animation.setDelay(70);
        dx = 0;
    }

    /**
     *
     * @return
     */
    public boolean shouldRemove() {
        return remove;
    }

    /**
     *
     */
    public void update() {

        checkTileMapCollision();
        setPosition(tempX, tempY);

        if (dx == 0 && !hit) {
            setHit();
        }

        animation.update();
        if (hit && animation.hasPlayedOnce()) {
            remove = true;
        }

    }

    /**
     *
     * @param g
     */
    public void draw(Graphics2D g) {

        setMapPosition();

        super.draw(g);

    }

}
