/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jaime Sierra
 */
public class bossExplosion {
    private int x;
    private int y;
    private int xmap;
    private int ymap;

    private int width;
    private int height;

    private Animation animation;
    private BufferedImage[] sprites;

    private boolean remove;

    public bossExplosion(int x, int y) {

        this.x = x;
        this.y = y;

        try {

            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/3.png"));

            sprites = new BufferedImage[7];
            sprites[0]=ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/1.png"));
            sprites[1]=ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/3_1.png"));
            sprites[2]=ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/4.png"));
            sprites[3]=ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/5.png"));
            sprites[4]=ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/6.png"));
            sprites[5]=ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/7.png"));
            sprites[6]=ImageIO.read(getClass().getResourceAsStream("/SpritesEnemies/8.png"));
           

        } catch (Exception e) {
            e.printStackTrace();
        }

        animation = new Animation();
        animation.setFrames(sprites);
        animation.setDelay(80);

    }

    public void update() {
        animation.update();
        if (animation.hasPlayedOnce()) {
            remove = true;
        }
    }

    public boolean shouldRemove() {
        return remove;
    }

    public void setMapPosition(int x, int y) {
		xmap = x;
		ymap = y;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(),x + xmap - width / 2,y + ymap - height / 2,null);
	}
}
