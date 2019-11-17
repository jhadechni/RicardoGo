/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javax.swing.JFrame;

/**
 *
 * @author Jaime Sierra
 */
public class Game {

    public static void main(String[] args) {
        JFrame ventana = new JFrame("¡GoRicardoGo!");
        ventana.setContentPane(new GamePanel());
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.pack();
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

    }
}
