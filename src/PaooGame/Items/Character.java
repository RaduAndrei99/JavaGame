package PaooGame.Items;

import PaooGame.Maps.Map;
import PaooGame.Particles.BloodParticle;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Character extends Item
{
    public static final int DEFAULT_LIFE            = 2;
    public static final float DEFAULT_SPEED         = 5.0f;
    public static final int DEFAULT_CREATURE_WIDTH  = 16*4;
    public static final int DEFAULT_CREATURE_HEIGHT = 28*4;
    public static final int DEFAULT_ATTACK_SPEED = 10;

    protected boolean isMovingRight;
    protected boolean isMovingLeft;
    protected boolean isMovingUp;
    protected boolean isMovingDown;

    protected boolean isDead = false;

    protected boolean damaged = false;

    protected int life;
    protected float xSpeed;
    protected float ySpeed;
    protected float xMove;
    protected float yMove;

    protected int wait = 0;

    protected int position = 1;

    protected int currentPos;

    protected int width_offset;
    protected int height_offset;

    protected BufferedImage[] image;

    protected Rectangle move;

    protected BloodParticle blood;

    public Character(RefLinks refLink, float x, float y, int width, int height)
    {
        super(refLink, x,y, width, height);
        this.move = new Rectangle((int)x,(int)y,0,0);
        life    = DEFAULT_LIFE;
        xSpeed   = 0;
        ySpeed  = 0;
        isMovingLeft = false;
        isMovingDown = false;
        isMovingUp = false;
        isMovingRight = false;

        blood = new BloodParticle();
    }

    public void Move(){
        move.x = (int) (normalBounds.x + xSpeed);
        move.y = (int) (normalBounds.y + ySpeed);

        if(xSpeed <= 0) {//merg la stanga
            if (Map.isSolid(Map.tiles[(int) ((normalBounds.y + collision_offset_y)  / Tile.TILE_HEIGHT)][(int) (normalBounds.x / Tile.TILE_HEIGHT)])  || Map.isSolid(Map.tiles[(int) (((normalBounds.y + collision_offset_y) + 0.9 * (height  - collision_offset_y)) / Tile.TILE_HEIGHT)][(int) (move.x / Tile.TILE_HEIGHT)])) {
                move.x =(int) (move.x - xSpeed);
                xSpeed = 0;
            }
        }
        else{//merg la dreapta
            if (Map.isSolid(Map.tiles[(int) ((normalBounds.y + collision_offset_y)  / Tile.TILE_HEIGHT)][(int) ((move.x + width) / Tile.TILE_HEIGHT)])|| Map.isSolid(Map.tiles[(int) (((normalBounds.y + collision_offset_y) + (height - collision_offset_y)*0.9) / Tile.TILE_HEIGHT)][(int) ((move.x + width) / Tile.TILE_HEIGHT)]) ) {
                move.x = (int)(move.x - xSpeed);
                xSpeed = 0;
            }
        }


        if(ySpeed <= 0){//merg sus
            if(Map.isSolid(Map.tiles[(int) ((move.y + collision_offset_y) /Tile.TILE_HEIGHT)][(int) (move.x/Tile.TILE_HEIGHT)])  || Map.isSolid(Map.tiles[(int) ((move.y + collision_offset_y)/Tile.TILE_HEIGHT)][(int) ((move.x + width*0.9)/Tile.TILE_HEIGHT)])){
                move.y = (int) (move.y - ySpeed);
                ySpeed = 0;
            }
        }
        else{//merg jos
            if(Map.isSolid(Map.tiles[(int) (((move.y + collision_offset_y) + (height - collision_offset_y))/Tile.TILE_HEIGHT)][(int) (move.x/Tile.TILE_HEIGHT)]) || Map.isSolid(Map.tiles[(int) (((move.y + collision_offset_y) + (height - collision_offset_y))/Tile.TILE_HEIGHT)][(int) ((move.x + 0.9 * width)/Tile.TILE_HEIGHT)])){
                move.y = (int) (move.y - ySpeed);
                ySpeed = 0;
            }
        }

        //isMoving = x != xMove || y != yMove;

        x = move.x;
        y = move.y;
    }


    protected int nextPos(){
        wait++;
        if(wait > 5) {
            wait = 0;
            if (currentPos < image.length - 1)
                return currentPos++;
            else
                return currentPos = 0;
        }
        return  currentPos;
    }

}

