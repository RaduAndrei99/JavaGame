package PaooGame.Items.Enemies;

import PaooGame.Items.Character;
import PaooGame.Items.Hero;
import PaooGame.Items.RectangleCollisionDetector;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;
import java.util.List;


import java.awt.*;

public abstract class  Enemy extends Character {
    protected final int BASE_DAMAGE = 10;

    protected int damage;
    public Enemy(RefLinks refLink, float x, float y, int width, int height) {
        super(refLink, x, y, width, height);

        this.damage = BASE_DAMAGE;
    }

    protected void getHit(){
        Sound.playSound(Sound.stab);
    }

    public int  getDamage(){
        return this.damage;
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.GREEN);
        if (!isDead) {
            //g.drawImage(image[next], (int) (x - refLink.GetGame().getCamera().getXOffset() + x_mirror_offset), (int) (y - refLink.GetGame().getCamera().getYOffset()), position * width, height, null);

            g.drawImage(image[nextPos()], (int) (x  - refLink.GetGame().getCamera().getXOffset() + x_mirror_offset), (int)( y -  refLink.GetGame().getCamera().getYOffset()), position * width, height, null);
            //g.drawRect((int) (getNormalBounds().x - refLink.GetGame().getCamera().getXOffset()), (int) (getNormalBounds().y - refLink.GetGame().getCamera().getYOffset() ) , getNormalBounds().width , getNormalBounds().height );
        }
        blood.Draw(g, (int) (x - refLink.GetGame().getCamera().getXOffset()), (int) (y - refLink.GetGame().getCamera().getYOffset()), width, height);
    }


    @Override
    public void Update() {
        if(!isDead) {
            for(Enemy other : this.refLink.GetMap().getRoom().getEnemies()){
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

            this.Move();


            int start = (int) ((x + width / 2) / Tile.TILE_WIDTH) + (int) ((y + height / 2)  / Tile.TILE_HEIGHT) *  refLink.GetMap().getWidth();
            int end =(int)(Hero.GetInstance().GetX() + Hero.GetInstance().GetWidth()/2)/Tile.TILE_HEIGHT +  (int)((Hero.GetInstance().GetY() + Hero.GetInstance().GetHeight()/2)/Tile.TILE_HEIGHT)* refLink.GetMap().getWidth();
            List<Integer> path = refLink.GetGame().getPathFinder().GetPath(end, start);
            //System.out.println(path);
            //System.out.println(start + " " + end);

            if((path != null && path.size() > 0))
                goToTile(path.remove(0));
            else {
                if (start != end) {
                    goToTile(end);
                }
            }

            if(Hero.GetInstance().getHeldItem() instanceof Weapon) {
                Weapon weapon = (Weapon)Hero.GetInstance().getHeldItem();
                if (RectangleCollisionDetector.checkCollision(getNormalBounds(), Hero.GetInstance().getWeaponBounds()) && weapon.isInAttackMode() && !weapon.damageAlreadyGiven()) {
                    this.life -=weapon.getSwordDamage();
                    weapon.signalDamage();
                    this.blood.resetAnimation();
                    this.getHit();
                }
            }


            if (life < 0) {
                isDead = true;
                Sound.playSound(Sound.death_big_demon);
            }

        }
        else
            refLink.GetMap().getRoom().removeEnemy(this);
    }


    public void goToTile(int tileNumber){
        int start = (int) ((x + width / 2) / Tile.TILE_WIDTH) + (int) ((y + height / 2)  / Tile.TILE_HEIGHT) * refLink.GetMap().getWidth();

        xSpeed = 0;
        ySpeed = 0;


        if(Hero.GetInstance().getNormalBounds().x + Hero.GetInstance().getNormalBounds().width < getNormalBounds().x){
            position = -1;
            x_mirror_offset = width;
        }else {
            position = 1;
            x_mirror_offset = 0;
        }


        if(tileNumber == start + 1)
            this.xSpeed = speed;

        if(tileNumber == start - 1)
            this.xSpeed = -speed;

        if(tileNumber == start - refLink.GetMap().getWidth())
            this.ySpeed = -speed;

        if(tileNumber == start +  refLink.GetMap().getWidth()) {
            this.ySpeed = speed;
        }


        if(tileNumber == start - refLink.GetMap().getWidth() - 1) {
            this.ySpeed = -speed;
            this.xSpeed = -speed;
        }

        if(tileNumber == start -  refLink.GetMap().getWidth() + 1 ){
            this.ySpeed = -speed;
            this.xSpeed = speed;
        }


        if(tileNumber == start + refLink.GetMap().getWidth() - 1) {
            this.ySpeed = speed;
            this.xSpeed = -speed;
        }

        if(tileNumber == start +  refLink.GetMap().getWidth() + 1 ){
            this.ySpeed = +speed;
            this.xSpeed = +speed;
        }

    }
}
