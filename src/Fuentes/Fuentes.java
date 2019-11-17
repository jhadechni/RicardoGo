/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Fuentes;

import java.awt.Font;
import java.io.InputStream;

/**
 *
 * @author Jaime Sierra
 */
public class Fuentes {
    private Font font= null;
    public String font1="80s.TTF";
    public String font2="Minecraft.ttf";
    
    
      /* Font.PLAIN = 0 , Font.BOLD = 1 , Font.ITALIC = 2
 * tamanio = float
 */
    public Font fuente( String fontName, int estilo, float tamanio)
    {
         try {
            //Se carga la fuente
            InputStream is =  getClass().getResourceAsStream(fontName);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (Exception ex) {
            //Si existe un error se carga fuente por defecto ARIAL
            System.err.println(fontName + " No se cargo la fuente");
            font = new Font("Arial", Font.PLAIN, 14);            
        }
        Font tfont = font.deriveFont(estilo, tamanio);
        return tfont;
    }
}
