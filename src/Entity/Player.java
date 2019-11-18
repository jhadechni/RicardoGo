/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Audio.AudioPlayer;
import Entity.Enemies.Boss;
import Entity.Objects.Coin;
import Entity.Objects.PowerUp;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Jaime Sierra
 */
public class Player extends MapObject {

    public Player(TileMap tileMap) {

        super(tileMap);

        //Configuración de valores iniciales de cada uno de los atributos.
        width = 30;
        height = 30;
        cwidth = 20;
        cheight = 20;

        moveSpeed = 0.3;
        maxSpeed = 1.3;
        stopSpeed = 0.4;
        gravity = 0.15;
        maxGravity = 4.0;
        jumpStart = -4.8; //Es negativo pues si no lo es el jugador no podrá saltar
        stopJumpSpeed = 0.32;

        facingRight = true;

        health = maxHealth = 3;
        score = 000;
        maxFire = 1000;
        fire = 0;

        fireCost = 100;
        firePower = false;
        fireBallDamage = 8;
        fireBalls = new ArrayList<FireBall>();

        scratchDamage = 5;
        scratchRange = 50; //Rango en píxeles

        // Load sprites
        try {
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/SpritesPlayer/playersprites.png"));
            sprites = new ArrayList<BufferedImage[]>();

            for (int i = 0; i < 7; i++) {
                BufferedImage[] bi = new BufferedImage[numFrames[i]];

                for (int j = 0; j < numFrames[i]; j++) {

                    if (i != SCRATCHING) {//El sprite tiene un tamaño más grande que los normales
                        bi[j] = spritesheet.getSubimage(j * width, i * height, width, height);
                    } else {
                        bi[j] = spritesheet.getSubimage(j * width * 2, i * height, width * 2, height);
                    }

                }

                sprites.add(bi);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        animation = new Animation();
        currentAction = IDLE;
        animation.setFrames(sprites.get(IDLE));
        animation.setDelay(400);
        sfx = new HashMap<String, AudioPlayer>();
        sfx.put("coin", new AudioPlayer("/SFX/coin.wav"));
        sfx.put("fireball", new AudioPlayer("/SFX/fireball.mp3"));
        sfx.put("hit", new AudioPlayer("/SFX/hit.mp3"));
        sfx.put("playerjump", new AudioPlayer("/SFX/playerjump.mp3"));
        sfx.put("powerup", new AudioPlayer("/SFX/powerUP.mp3"));

    }

    // Player Stuffs
    private int health, maxHealth, fire, maxFire, score;
    private boolean dead, flinching;
    private long flinchTimer;
    private long time;
    //Fireball  
    private boolean firing, firePower;
    private int fireCost, fireBallDamage;
    private ArrayList<FireBall> fireBalls;

    // Scratch
    private boolean scratching;
    private int scratchDamage;
    private int scratchRange;

    // Gliding
    private boolean gliding;

    // Animations
    private ArrayList<BufferedImage[]> sprites;
    private final int[] numFrames = {2, 8, 1, 2, 4, 2, 5}; //Cada posición del vector representa el número de frames que tiene cada actividad.

    // Animation Actions
    private static final int IDLE = 0;//Cuando está en espera
    private static final int WALKING = 1; // Cuando está caminando
    private static final int JUMPING = 2;//Cuando está saltando
    private static final int FALLING = 3;//Cuando está cayendo
    private static final int GLIDING = 4;//Cuando se está deslizando
    private static final int FIREBALL = 5; //Bola de fuego
    private static final int SCRATCHING = 6; //Aruño
    private HashMap<String, AudioPlayer> sfx;

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getFire() {
        return fire;
    }

    public int getMaxFire() {
        return maxFire;
    }

    public void setFiring() {
//        if (fire != 0) {
        this.firing = true;
//        }

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setScratching() {
        this.scratching = true;
    }

    public void setGliding(boolean gliding) {
        this.gliding = gliding;
    }

    public void setFirePower(boolean firePower) {
        this.firePower = firePower;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isFirePower() {
        return firePower;
    }

    private void getNextPosition() {

        // Movement
        if (left) {
            dx -= moveSpeed;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else if (right) {
            dx += moveSpeed;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= stopSpeed;
                if (dx < 0) {
                    dx = 0;
                }
            } else if (dx < 0) {
                dx += stopSpeed;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        // No puede moverse mientras ataca, solo en el aire
        if ((currentAction == SCRATCHING || currentAction == FIREBALL)
                && !(jumping || falling)) {
            dx = 0;
        }

        // jumping
        if (jumping && !falling) {
//            sfx.get("jump").play();
            dy = jumpStart;
            falling = true;
        }

        // falling
        if (falling) {

            if (dy > 0 && gliding) {
                dy += gravity * 0.1;
            } else {
                dy += gravity;
            }

            if (dy > 0) {
                jumping = false;
            }
            if (dy < 0 && !jumping) {
                dy += stopJumpSpeed;
            }

            if (dy > maxGravity) {
                dy = maxGravity;
            }

        }
    }

    public void update() {

        time += 1;
//       System.out.println(time);
        // update position
        getNextPosition();
        checkTileMapCollision();
        setPosition(tempX, tempY);
       System.out.println(x + "   " + y);

        // Mirar si se ha detenido el ataque.
        if (currentAction == SCRATCHING) {
            if (animation.hasPlayedOnce()) {
                scratching = false;
            }
        }
        if (currentAction == FIREBALL) {
            if (animation.hasPlayedOnce()) {
                firing = false;
            }
        }

        // fireball attack
        // fire += 1;
        if (fire > maxFire) {
            fire = maxFire;
        }
        if (firing && currentAction != FIREBALL) {
            if (fire > fireCost || fire == fireCost) {
                fire -= fireCost;
                FireBall fb = new FireBall(tileMap, facingRight);
                fb.setPosition(x, y);
                fireBalls.add(fb);
            }
        }

        // update fireballs
        for (int i = 0; i < fireBalls.size(); i++) {
            fireBalls.get(i).update();
            if (fireBalls.get(i).shouldRemove()) {
                fireBalls.remove(i);
                i--;
            }
        }

        // check done flinching
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed > 1000) {
                flinching = false;
            }
        }

        // set animation
        if (scratching) {
            if (currentAction != SCRATCHING) {
                sfx.get("hit").play();
                currentAction = SCRATCHING;
                animation.setFrames(sprites.get(SCRATCHING));
                animation.setDelay(50);
                width = 60;
            }
        } else if (firing) {
            if (currentAction != FIREBALL) {
                sfx.get("fireball").play();
                currentAction = FIREBALL;
                animation.setFrames(sprites.get(FIREBALL));
                animation.setDelay(100);
                width = 30;
            }
        } else if (dy > 0) {
            if (gliding) {
                if (currentAction != GLIDING) {
                    currentAction = GLIDING;
                    animation.setFrames(sprites.get(GLIDING));
                    animation.setDelay(100);
                    width = 30;
                }
            } else if (currentAction != FALLING) {
                currentAction = FALLING;
                animation.setFrames(sprites.get(FALLING));
                animation.setDelay(100);
                width = 30;
            }
        } else if (dy < 0) {
            if (currentAction != JUMPING) {
                currentAction = JUMPING;
                sfx.get("playerjump").play();
                animation.setFrames(sprites.get(JUMPING));
                animation.setDelay(-1);//No se necesita animacion
                width = 30;
            }
        } else if (left || right) {
            if (currentAction != WALKING) {
                currentAction = WALKING;
                animation.setFrames(sprites.get(WALKING));
                animation.setDelay(40);
                width = 30;
            }
        } else {
            if (currentAction != IDLE) {
                currentAction = IDLE;
                animation.setFrames(sprites.get(IDLE));
                animation.setDelay(400);
                width = 30;
            }
        }

        animation.update();

        // set direction
        if (currentAction != SCRATCHING && currentAction != FIREBALL) {
            if (right) {
                facingRight = true;
            }
            if (left) {
                facingRight = false;
            }
        }

    }

    public void draw(Graphics2D g) {

        setMapPosition();

        // draw fireballs
        for (int i = 0; i < fireBalls.size(); i++) {
            fireBalls.get(i).draw(g);
        }

        // draw player
        if (flinching) {
            long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
            if (elapsed / 100 % 2 == 0) {
                return;
            }
        }
        super.draw(g);
    }

    public void hit(int damage) {
        if (flinching) {
            return;
        }
        health -= damage;
        if (health < 0) {
            health = 0;
        }
        if (health == 0) {
            dead = true;
        }
        flinching = true;
        flinchTimer = System.nanoTime();
    }

    public void hitCoins(int points) {
        score += points;
    }

    public void hitPowerUP() {
        firePower = true;
        fire += 500;
    }
   

    public void checkAttack(ArrayList<Enemy> enemies, ArrayList<Coin> coins, ArrayList<PowerUp> Pu, ArrayList<Boss> b) {

        // loop through enemies
        for (int i = 0; i < enemies.size(); i++) {

            Enemy e = enemies.get(i);

            // scratch attack
            if (scratching) {
                if (facingRight) {
                    //Mira si el enemigo está en frente de nosotros
                    if (e.getX() > x && e.getX() < x + scratchRange && e.getY() > y - height / 2 && e.getY() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                } else {
                    if (e.getX() < x && e.getX() > x - scratchRange && e.getY() > y - height / 2 && e.getY() < y + height / 2) {
                        e.hit(scratchDamage);
                    }
                }
            }

            // fireballs
            for (int j = 0; j < fireBalls.size(); j++) {
                if (fireBalls.get(j).intersects(e)) {
                    e.hit(fireBallDamage);
                    fireBalls.get(j).setHit();
                    break;
                }
            }

            // check enemy collision
            if (intersects(e)) {
                hit(e.getDamage());
            }
            
            

        }
       
        //BOSS
        for (int i = 0; i < b.size(); i++) {
            Boss bo= b.get(i);
              // scratch attack
            if (scratching) {
                if (facingRight) {
                    //Mira si el enemigo está en frente de nosotros
                    if (bo.getX() > x && bo.getX() < x + scratchRange && bo.getY() > y - height / 2 && bo.getY() < y + height / 2) {
                        bo.hit(scratchDamage);
                    }
                } else {
                    if (bo.getX() < x && bo.getX() > x - scratchRange && bo.getY() > y - height / 2 && bo.getY() < y + height / 2) {
                        bo.hit(scratchDamage);
                    }
                }
            }

            // fireballs
            for (int j = 0; j < fireBalls.size(); j++) {
                if (fireBalls.get(j).intersects(bo)) {
                    bo.hit(1);
                    fireBalls.get(j).setHit();
                    break;
                }
            }
            
            if (intersects(b.get(i))) {
                hit(b.get(i).getDamage());
            }
        }
        
        
        for (int i = 0; i < coins.size(); i++) {
            Coin p = coins.get(i);
            //check coins
            if (intersects(p)) {
                hitCoins(5);
                sfx.get("coin").play();
                p.dead = true;
            }
        }
        for (int i = 0; i < Pu.size(); i++) {
            PowerUp p = Pu.get(i);
            //check powerUP
            if (intersects(p)) {
                hitPowerUP();
                sfx.get("powerup").play();
                p.dead = true;
            }
        }

    }

    public String getTimeToString() {
        int minutes = (int) (time / 3600);
        int seconds = (int) ((time % 3600) / 60);
        return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
    }

    public int getTimer() {
        int minutes = (int) (time / 3600);
        int seconds = (int) ((time % 3600) / 60);
        return seconds < 10 ? minutes + seconds : minutes + seconds;
    }

    public void increaseScore(int score) {
        this.score += score;

    }
//    

    public int CalculateScore() {
        int Score;
        return Score = (getTimer() / getTimer() % 10) + (score) + ((fireBalls.size()) * 2) + ((health) * 5);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
    

}
