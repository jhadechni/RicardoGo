/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import Audio.AudioPlayer;
import Fuentes.Fuentes;
import Main.GamePanel;
import TileMap.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;


/**
 *
 * @author Jaime Sierra
 */
public class MenuState extends GameState {

    private Background fondo;
    private Fuentes fuente= new Fuentes();
    private int currentChoice = 0;
    String[] opciones = {
        "JUGAR",
        "AYUDA",
        "SALIR"
    };
    private Color Titulo;
    private Font titleFont;
    private Font font;
    private Font font2;
    private HashMap<String,AudioPlayer> music;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        try {
            fondo = new Background("/Backgrounds/fondo.gif",1);
            fondo.setVector(-0.5, 0);
            Titulo = new Color(230, 230, 0);
            titleFont = fuente.fuente(fuente.font1, 0, 40);
            font = fuente.fuente(fuente.font2, 0, 20);
            music = new HashMap<String, AudioPlayer>();
            music.put("in", new AudioPlayer("/SFX/menuoption.mp3"));
            music.put("select", new AudioPlayer("/SFX/menuselect.mp3"));
            music.put("music", new AudioPlayer("/SFX/Title Theme.mp3"));
            music.get("music").play2();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        fondo.update();
    }

    @Override
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
        g.drawString("RicardoGo!",25,70);
        /**
         * Dibujar menú
         */
        g.setFont(font);
        for (int i = 0; i < opciones.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.BLACK);
            } else {
                g.setColor(Color.YELLOW);
            }
            g.drawString(opciones[i], 143, 140 + i * 30);
        }
        
        font2 = new Font("Arial", Font.PLAIN, 9);
        g.setFont(font2);
        g.drawString("Jaime Sierra.® 2019", 10, 232);
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            music.get("select").play();
            select();
        }
        if (k == KeyEvent.VK_UP) {
              music.get("in").play();
            currentChoice--;
            if (currentChoice == -1) {
                currentChoice = opciones.length - 1;
            }

        }
        if (k == KeyEvent.VK_DOWN) {
             music.get("in").play();
            currentChoice++;
            if (currentChoice == opciones.length) {
                currentChoice = 0;
            }
        }
    }

    @Override
    public void keyReleased(int k) {
 
    }

    private void select() {
        music.get("music").stop();
        switch (currentChoice) {
            case 0:
                //Start
              
                gsm.setState(GameStateManager.LEVEL1STATE);
                break;
            case 1:
                //Help
                break;
            case 2:
                
                System.exit(0);
                break;
        }
    }

}
