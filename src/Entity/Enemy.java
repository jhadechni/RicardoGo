/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import TileMap.TileMap;

/**
 *
 * @author Jaime Sierra
 */
public class Enemy extends MapObject {

    public Enemy(TileMap tileMap) {
        super(tileMap);
    }
    protected int health, maxHealth, damage;
    protected boolean dead, flinching;
    protected long flinchTimer;

    public boolean isDead() {
        return dead;
    }

    public int getDamage() {
        return damage;
    }

    public void hit(int damage) {
        if (dead || flinching) {
            return;
        }
        health = 0;
        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            dead = true;
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    public void update() {
    }
}
