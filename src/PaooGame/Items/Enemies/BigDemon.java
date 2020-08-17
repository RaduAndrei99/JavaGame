package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;
import PaooGame.Items.*;
import PaooGame.Maps.Map;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class BigDemon extends Enemy {

    public static int DEFAULT_WIDTH = 32*4;
    public static int DEFAULT_HEIGHT = 36*4;
    public static int DEFAULT_SPEED = 2;


    List<Integer> path;

    public BigDemon(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.bigDemon1;
        image[1] = Assets.bigDemon2;
        image[2] = Assets.bigDemon3;
        image[3] = Assets.bigDemon4;

        this.life = 200;
        this.damage = 2;

        collision_offset_y = 20;
    }


    @Override
    public void Update() {
        if(!isDead) {
            for(Enemy other : this.refLink.GetMap().getEnemies()){
                if(RectangleCollisionDetector.checkCollision(other.getNormalBounds(), this.normalBounds) && other != this){
                    if(this.x <= other.GetX()) {
                        this.x -= 5;
                    }
                    else {
                        this.x += 5;
                    }
                    if(this.y <= other.GetY()) {
                        this.y -= 5;
                    }
                    else {
                        this.y += 5;
                    }
                }
            }
            UpdateBoundsRectangle();


            int start = (int) ((x + width / 2) / Tile.TILE_WIDTH) + (int) ((y + height / 2)  / Tile.TILE_HEIGHT) *  refLink.GetMap().getWidth();
            int end =(int)(Hero.GetInstance().GetX() + Hero.GetInstance().GetWidth()/2)/Tile.TILE_HEIGHT +  (int)((Hero.GetInstance().GetY() + Hero.GetInstance().GetHeight()/2)/Tile.TILE_HEIGHT)* refLink.GetMap().getWidth();
            path = refLink.GetGame().getPathFinder().GetPath(end, start);


            if((path != null && path.size() > 0))
                goToTile(path.remove(0));

            if ( RectangleCollisionDetector.checkCollision(this.normalBounds,Hero.GetInstance().getWeaponBounds()) && Hero.GetInstance().getWeapon().isInAttackMode() && !Hero.GetInstance().getWeapon().damageAlreadyGiven()) {
                this.life -= Hero.GetInstance().getWeapon().getSwordDamage();
                Hero.GetInstance().getWeapon().signalDamage();
                this.blood.resetAnimation();
                this.getHit();
            }

            if (life < 0) {
                isDead = true;
                Sound.playSound(Sound.death_big_demon);
            }

        }
        else
            refLink.GetMap().removeEnemy(this);
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.GREEN);
        if (!isDead) {
            g.drawImage(image[nextPos()], (int) (x + width_offset - refLink.GetGame().getCamera().getXOffset()), (int)( y -  refLink.GetGame().getCamera().getYOffset()), position * width, height, null);
            g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset()), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset()) + collision_offset_y, normalBounds.width, normalBounds.height - collision_offset_y);
        }
        blood.Draw(g, (int) (x -  refLink.GetGame().getCamera().getXOffset() - 80), (int) (y - refLink.GetGame().getCamera().getYOffset() - 50), 300, 300);


        //this.colorTiles(g);//pentru a vedea drumul parcurs
    }

    public void goToTile(int tileNumber){
        int start = (int) ((x + width / 2) / Tile.TILE_WIDTH) + (int) ((y + height / 2)  / Tile.TILE_HEIGHT) * refLink.GetMap().getWidth();

        xSpeed = 0;
        ySpeed =0;


        if(tileNumber == start + 1)
            this.xSpeed = DEFAULT_SPEED;

        if(tileNumber == start - 1)
            this.xSpeed = -DEFAULT_SPEED;

        if(tileNumber == start - refLink.GetMap().getWidth())
            this.ySpeed = -DEFAULT_SPEED;

        if(tileNumber == start +  refLink.GetMap().getWidth())
            this.ySpeed = DEFAULT_SPEED;



        if(tileNumber == start - refLink.GetMap().getWidth() -1) {
            this.ySpeed = -DEFAULT_SPEED;
            this.xSpeed = -DEFAULT_SPEED;
        }

        if(tileNumber == start -  refLink.GetMap().getWidth() + 1 ){
            this.ySpeed = -DEFAULT_SPEED;
            this.xSpeed = DEFAULT_SPEED;
        }


        if(tileNumber == start + refLink.GetMap().getWidth() -1) {
            this.ySpeed = DEFAULT_SPEED;
            this.xSpeed = -DEFAULT_SPEED;
        }

        if(tileNumber == start +  refLink.GetMap().getWidth() + 1 ){
            this.ySpeed = +DEFAULT_SPEED;
            this.xSpeed = +DEFAULT_SPEED;
        }


        this.Move();
    }

    void colorTiles(Graphics g){
        g.setColor(Color.RED);
         if(this.path!=null) {
             for (int tile : this.path) {
                 int c = tile / 32;
                 int r = tile % 32;
                 g.drawRect((int)(r * 48 - refLink.GetGame().getCamera().getXOffset()) , (int)(c * 48 - refLink.GetGame().getCamera().getYOffset()), 48, 48);
             }
         }
    }

}
