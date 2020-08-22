package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.Items.RectangleCollisionDetector;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.Particles.BlueSpell;
import PaooGame.Particles.Fireball;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class OldWizard extends Enemy {

    public static int DEFAULT_WIDTH = 16*5;
    public static int DEFAULT_HEIGHT = 16*5;
    public static int DEFAULT_SPEED = 1;

    public static int DEFAULT_BOUNDS_WIDTH = 40;
    public static int DEFAULT_BOUNDS_HEIGHT = 50;

    protected long attackSpeed = 2;
    protected long lastFireballTime;

    public OldWizard(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.oldWizard1;
        image[1] = Assets.oldWizard2;
        image[2] = Assets.oldWizard3;
        image[3] = Assets.oldWizard4;

        this.life = 120;
        this.damage = 1;

        collision_offset_y = 20;
        collision_offset_x = 25;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
        this.speed = DEFAULT_SPEED;
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
            Move();

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

            if(Hero.GetInstance().getNormalBounds().x + Hero.GetInstance().getNormalBounds().width < getNormalBounds().x){
                position = -1;
                x_mirror_offset = width;
            }else {
                position = 1;
                x_mirror_offset = 0;
            }

            long currentTime = System.currentTimeMillis()/1000;
            if(currentTime - lastFireballTime > attackSpeed){
                throwSpell();
                Sound.playSound(Sound.blueSpell);
                lastFireballTime = System.currentTimeMillis()/1000;
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
                Sound.playSound(Sound.death_little_wizard);
            }

        }
        else
            refLink.GetMap().getRoom().removeEnemy(this);
    }

    @Override
    public void Move(){
        move.x = (int) (normalBounds.x + xSpeed);
        move.y = (int) (normalBounds.y + ySpeed);
/*
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
        }*/

        //isMoving = x != xMove || y != yMove;

        x = move.x;
        y = move.y;
    }

    public void throwSpell(){
        if(Hero.GetInstance().getNormalBounds().x + Hero.GetInstance().getNormalBounds().width < getNormalBounds().x){
            refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y));
            return;
        }

        if(Hero.GetInstance().getNormalBounds().x  > getNormalBounds().x + getNormalBounds().width){
            refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y));
            return;
        }

        if(Hero.GetInstance().getNormalBounds().y + Hero.GetInstance().getNormalBounds().height < getNormalBounds().y){
            refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y));
            return;
        }

        if(Hero.GetInstance().getNormalBounds().y  > getNormalBounds().y + getNormalBounds().height ){
            refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y));
        }

    }

}
