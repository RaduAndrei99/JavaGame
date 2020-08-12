package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.Items.RectangleCollisionDetector;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class BigZombie extends Enemy {

    public static int DEFAULT_WIDTH = 32*4;
    public static int DEFAULT_HEIGHT = 36*4;
    public static int DEFAULT_SPEED = 1;

    public BigZombie(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.bigZombie1;
        image[1] = Assets.bigZombie2;
        image[2] = Assets.bigZombie3;
        image[3] = Assets.bigZombie4;

        this.life = 300;
        this.damage = 1;
    }

    @Override
    public void Update() {
        if(!isDead) {
            int end = (int) ((x + width / 2) / Tile.TILE_WIDTH) + (int) (y / Tile.TILE_HEIGHT) * 32;
            int start = (int) ((Hero.GetInstance().GetX() / Tile.TILE_WIDTH) + (int) (Hero.GetInstance().GetY() / Tile.TILE_HEIGHT) * 32);
            List<Integer> path = refLink.GetGame().getPathFinder().GetPath(start, end);

            if (path != null && path.size() > 0)
                goToTile(path.remove(0));

            UpdateBoundsRectangle();

            if ( RectangleCollisionDetector.checkCollision(this.normalBounds,Hero.GetInstance().getWeaponBounds()) && Hero.GetInstance().getWeapon().isInAttackMode() && !Hero.GetInstance().getWeapon().damageAlreadyGiven()) {
                this.life -= Hero.GetInstance().getWeapon().getSwordDamage();
                Hero.GetInstance().getWeapon().signalDamage();
                this.blood.resetAnimation();
                this.getHit();
            }

            if (life < 0) {
                isDead = true;
                Sound.playSound(Sound.death_big_zombie);
            }

            if ( RectangleCollisionDetector.checkCollision(this.normalBounds,Hero.GetInstance().getNormalBounds())){
                Hero.GetInstance().getDamage(damage);
            }

        }
    }

    @Override
    public void Draw(Graphics g) {
        //g.setColor(Color.GREEN);
        if(!isDead) {
            g.drawImage(image[nextPos()], (int) x + width_offset, (int) y, position * width, height, null);
            //g.drawRect(normalBounds.x, normalBounds.y + collision_offset_y, normalBounds.width, normalBounds.height - collision_offset_y);
        }
        blood.Draw(g,(int)x-80,(int)y-50,300,300);

    }

    public void goToTile(int tileNumber){
        int start = (int) ((x+width/2)/Tile.TILE_WIDTH) +(int) (y/Tile.TILE_HEIGHT)*32;

        xSpeed = 0;
        ySpeed =0;



        if(tileNumber == start + 1)
            this.xSpeed = DEFAULT_SPEED;

        if(tileNumber == start - 1)
            this.xSpeed = -DEFAULT_SPEED;



        if(tileNumber == start - 32)
            this.ySpeed = -DEFAULT_SPEED;

        if(tileNumber == start + 32)
            this.ySpeed = DEFAULT_SPEED;



        if(tileNumber == start - 32 -1) {
            this.ySpeed = -DEFAULT_SPEED;
            this.xSpeed = -DEFAULT_SPEED;
        }

        if(tileNumber == start - 32 + 1 ){
            this.ySpeed = -DEFAULT_SPEED;
            this.xSpeed = DEFAULT_SPEED;
        }


        if(tileNumber == start + 32 -1) {
            this.ySpeed = DEFAULT_SPEED;
            this.xSpeed = -DEFAULT_SPEED;
        }

        if(tileNumber == start + 32 + 1 ){
            this.ySpeed = +DEFAULT_SPEED;
            this.xSpeed = +DEFAULT_SPEED;
        }

        this.Move();
    }
}
