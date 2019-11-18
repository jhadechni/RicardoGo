/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameState;

import java.util.*;

/**
 *
 * @author Jaime Sierra
 */
public class GameStateManager {

    GameState[] gameStates;
    int currentState;
    public static final int NUMGAMESTATES = 7;
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;
    public static final int GAMEOVERSTATE = 2;
    public static final int HOWTOPLAYSTATE=4;
    public static final int ENEMYSTATE=5;
    public static final int MOVEMENTSSTATE=6;

    public GameStateManager() {
        gameStates = new GameState[NUMGAMESTATES];
        currentState = MENUSTATE;
        loadState(currentState);

    }

    private void loadState(int state) {
        if (state == MENUSTATE) {
            gameStates[state] = new MenuState(this);
        }
        if (state == LEVEL1STATE) {
            gameStates[state] = new Level1State(this);
        }
        if (state == GAMEOVERSTATE) {
            gameStates[state] = new GameOverState(this);
        }
        if (state==HOWTOPLAYSTATE) {
            gameStates[state] = new HowToPlayState(this);
        }
        if (state == ENEMYSTATE) {
            gameStates[state] = new EnemiesState(this);
        }
         if (state == MOVEMENTSSTATE) {
            gameStates[state] = new Movements(this);
        }
  
    }

    private void unloadState(int state) {
        gameStates[state] = null;
    }

    public void setState(int state) {
        unloadState(currentState);
        currentState = state;
        loadState(currentState);
        //gameStates[currentState].init();
    }

    public void update() {
        try {
            gameStates[currentState].update();
        } catch (Exception e) {
     
        }
    }

    public void draw(java.awt.Graphics2D g) {
       try {
            gameStates[currentState].draw(g);
      } catch (Exception e) {
         
       }
    }

    public void keyPressed(int k) {
        try {
            gameStates[currentState].keyPressed(k);
        } catch (Exception e) {
           
        }
        
    }

    public void keyReleased(int k) {
        try {
            gameStates[currentState].keyReleased(k);
        } catch (Exception e) {
        }
          
        
       
    }

}
