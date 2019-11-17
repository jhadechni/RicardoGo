/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TileMap;

import Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Jaime Sierra
 */
public class Background {

    BufferedImage imagen;

    double x, y, dx, dy, moveScale;

    public Background(String s, double ms) {
        try {
            imagen = ImageIO.read(getClass().getResourceAsStream(s));
            moveScale = ms;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPosition(double x, double y) {
        this.x = (x * moveScale) % GamePanel.WIDTH;
        this.y = (y * moveScale) % GamePanel.HEIGHT;
    }

    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics2D g) {

        g.drawImage(imagen, (int) x, (int) y, null);

        if (x < 0) {
            g.drawImage(imagen, (int) x + GamePanel.WIDTH, (int) y, null);
        }
        if (x > 0) {
            g.drawImage(imagen, (int) x - GamePanel.WIDTH, (int) y, null);
        }
    }

}
