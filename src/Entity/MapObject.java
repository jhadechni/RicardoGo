/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Main.GamePanel;
import TileMap.Tile;
import TileMap.TileMap;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Jaime Sierra
 */
public abstract class MapObject {

    //Tile stuff
    protected TileMap tileMap;
    protected int tileSize;
    protected double xmap, ymap;

    //Posición y vector
    protected double x, y, dx, dy;

    //dimensions
    protected int height, width;

    //Manejo de Colisiones
    //Bouding Box
    protected int cwidth, cheight;

    //Entorno
    protected int currRow, currCol;
    protected double destX, destY, tempX, tempY;
    protected boolean topLeft, topRight, bottomLeft, bottomRight;

    // Animaciones
    protected Animation animation;
    protected int currentAction, previousAction;
    protected boolean facingRight;

    // Movimientos
    protected boolean left, right, up, down, jumping, falling;

    // Fisicas
    protected double moveSpeed, maxSpeed, stopSpeed, gravity, maxGravity, jumpStart, stopJumpSpeed;

    /**
     * Constructor de la Clase
     *
     * @param tileMap - Variable tipo TileMap
     */
    public MapObject(TileMap tileMap) {
        this.tileMap = tileMap;
        this.tileSize = tileMap.getTileSize();
    }

    /**
     * }
     *
     * @param o - Variable tipo MapObject
     * @return- Calcula los intersectos
     */
    public boolean intersects(MapObject o) {
        Rectangle r1 = getRectangle();
        Rectangle r2 = o.getRectangle();
        return r1.intersects(r2);
    }

    /**
     *
     * @return - Obtiene el rectángulo de las Colisiones.
     */
    public Rectangle getRectangle() {
        return new Rectangle((int) x - cwidth, (int) y - cheight, cwidth, cheight);
    }

    /**
     * Calcula las esquinas.
     *
     * @param x- Representa la distancia en x
     * @param y -Representa la distancia en y
     */
    public void calculateCorners(double x, double y) {

        int leftTile = (int) (x - cwidth / 2) / tileSize;
        int rightTile = (int) (x + cwidth / 2 - 1) / tileSize;
        int topTile = (int) (y - cheight / 2) / tileSize;
        int bottomTile = (int) (y + cheight / 2 - 1) / tileSize;

        //Corners
        int tl = tileMap.getType(topTile, leftTile);
        int tr = tileMap.getType(topTile, rightTile);
        int bl = tileMap.getType(bottomTile, leftTile);
        int br = tileMap.getType(bottomTile, rightTile);

        topLeft = tl == Tile.BLOCKED;
        topRight = tr == Tile.BLOCKED;
        bottomLeft = bl == Tile.BLOCKED;
        bottomRight = br == Tile.BLOCKED;

    }

    //Colisiones 
    /**
     * Mira si hay colisiones con el Mapa
     */
    public void checkTileMapCollision() {
    
        currCol = (int) x / tileSize;
        currRow = (int) y / tileSize;

        destX = x + dx;
        destY = y + dy;

        tempX = x;
        tempY = y;

        //Colisiones en Y
        calculateCorners(x, destY);

        if (dy < 0) {
            if (topLeft || topRight) {
                dy = 0;
                tempY = currRow * tileSize + cheight / 2;
                
            } else {
                tempY += dy;
               
            }
        }
        //Controla que el jugador no este en estado falling por siempre, si no hasta que pise el suelo.
        if (dy > 0) {
            if (bottomLeft || bottomRight) {
                dy = 0;
                falling = false;
                tempY = (currRow + 1) * tileSize - cheight / 2;
                
            } else {
                tempY += dy;
                
            }
        }

        //Colisiones en X.
        calculateCorners(destX, y);
        if (dx < 0) {
            if (topLeft || bottomLeft) {
                dx = 0;
                tempX = currCol * tileSize + cwidth / 2;
            } else {
                tempX += dx;
            }
        }
        if (dx > 0) {
            if (topRight || bottomRight) {
                dx = 0;
                tempX = (currCol + 1) * tileSize - cwidth / 2;
            } else {
                tempX += dx;
            }
        }

        if (!falling) {
            calculateCorners(x, destY + 1);
            if (!bottomLeft && !bottomRight) {
                falling = true;
            }
        }

    }
/**
 * 
 * @return 
 */
    public int getX() {
        return (int) x;
    }
/**
 * 
 * @return 
 */
    public int getY() {
        return (int)y;
    }
/**
 * 
 * @return 
 */
    public int getHeight() {
        return height;
    }
/**
 * 
 * @return 
 */
    public int getWidth() {
        return width;
    }
/**
 * 
 * @return 
 */
    public int getCwidth() {
        return cwidth;
    }
/**
 * 
 * @return 
 */
    public int getCheight() {
        return cheight;
    }

    //Regular position
    /**
     * 
     * @param x
     * @param y 
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
/**
 * 
 * @param dx
 * @param dy 
 */
    public void setVector(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    //Map Position
    /**
     * 
     */
    public void setMapPosition() {
        xmap = tileMap.getX();
        ymap = tileMap.getY();
    }
/**
 * 
 * @param left 
 */
    public void setLeft(boolean left) {
        this.left = left;
    }
/**
 * 
 * @param right 
 */
    public void setRight(boolean right) {
        this.right = right;
    }
/**
 * 
 * @param up 
 */
    public void setUp(boolean up) {
        this.up = up;
    }
/**
 * 
 * @param down 
 */
    public void setDown(boolean down) {
        this.down = down;
    }
/**
 * 
 * @param jumping 
 */
    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
/**
 * 
 * @return 
 */
    public boolean notOnScreen() {
        return x + xmap + width < 0 || x + xmap - width > GamePanel.WIDTH || y + ymap + height < 0 || y + ymap - height > GamePanel.HEIGHT;
    }
/**
 * 
 * @param g 
 */
    public void draw(Graphics2D g) {
        if (facingRight) {
            g.drawImage(animation.getImage(), (int) (x + xmap - width / 2), (int) (y + ymap - height / 2), null);
        } else {
            g.drawImage(animation.getImage(), (int) (x + xmap - width / 2 + width), (int) (y + ymap - height / 2), -width, height, null);
        }
    }

}
