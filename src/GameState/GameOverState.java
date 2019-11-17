/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import Audio.AudioPlayer;
import Fuentes.Fuentes;
import TileMap.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author Jaime Sierra
 */
public class GameOverState extends GameState {
    
    Background fondo;
    private Fuentes fuente= new Fuentes();
    private AudioPlayer deadMusic;
    String[] opciones = {
        "JUGAR",
        "AYUDA",
        "SALIR"
    };
    Color Titulo;
    Font titleFont;
    Font font;
    Font font2;
    int PlayerScore;

    public GameOverState (GameStateManager gsm) {
        this.gsm = gsm;
        try {
            fondo = new Background("/Backgrounds/fondo.gif",1);
            fondo.setVector(-0.3, 0);
            Titulo = new Color(230, 230, 0);
            titleFont = fuente.fuente(fuente.font1, 0, 40);
            font = fuente.fuente(fuente.font2, 0, 20);

        } catch (Exception e) {
            e.printStackTrace();
        }
        deadMusic = new AudioPlayer("/Music/lost.mp3");
        deadMusic.play2();
    }

    public void init() {

    }

    public void update() {
        fondo.update();
    }

    public void draw(Graphics2D g) {
        /**
         * Dibujar fondo
         */
        fondo.draw(g);
        /**
         * dibujar título
         */
        g.setColor(Titulo);
        g.setFont(titleFont);
        g.drawString("GAME OVER!",8,100);
        /**
         * Dibujar menú
         */
//        g.drawString(String.valueOf(PlayerScore), 30, 40);
        g.setFont(font);
        
        font2 = new Font("Arial", Font.PLAIN, 9);
        g.setFont(font2);
        g.drawString("Jaime Sierra.® 2019", 10, 232);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            gsm.setState(gsm.MENUSTATE);
            deadMusic.stop();
        }
        
    }

    public void keyReleased(int k) {

    }

    private void select() {
      
    }

}
