/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author Jaime Sierra
 */
public class HUD {
    private Player player;
	
	private BufferedImage image1,image2;
	private Font font;
	
	public HUD(Player p) {
		player = p;
		try {
			image1 = ImageIO.read(getClass().getResourceAsStream("/HUD/hud1.png"));
                        image2 = ImageIO.read(getClass().getResourceAsStream("/HUD/hud2.png"));
			font = new Font("Arial", Font.PLAIN, 14);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g) {
		
		g.drawImage(image1, 0, 10, null);
                g.drawImage(image2, 276, 10, null);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString(player.getHealth() + "/" + player.getMaxHealth(),30,25);
		g.drawString(player.getFire() / 100 + "/" + player.getMaxFire() / 100,30,45);
                
                g.drawString(String.valueOf(player.getScore()),302,45);
		g.drawString(player.getTimeToString(),302,25);
		
	}
}
