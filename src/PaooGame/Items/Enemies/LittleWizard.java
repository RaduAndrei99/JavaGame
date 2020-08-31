package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Coin;
import PaooGame.Items.Hero;
import PaooGame.Items.CollisionDetector;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.Particles.Fireball;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class LittleWizard extends Enemy implements Wizard  {

    public static int DEFAULT_WIDTH = 16*4;
    public static int DEFAULT_HEIGHT = 16*5;
    public static int DEFAULT_SPEED = 2;

    public static int DEFAULT_BOUNDS_WIDTH = 40;
    public static int DEFAULT_BOUNDS_HEIGHT = 50;

    protected static int DEFAULT_RANGE = 300;

    protected long attackSpeed = 1;
    protected long lastFireballTime;

    Circle range;

    public LittleWizard(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.littleWizard1;
        image[1] = Assets.littleWizard2;
        image[2] = Assets.littleWizard3;
        image[3] = Assets.littleWizard4;

        this.life = 100;
        this.damage = 1;

        collision_offset_y = 20;
        collision_offset_x = 10;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
        this.speed = DEFAULT_SPEED;

        range = new Circle(getNormalBounds().x + getNormalBounds().width/2.0,getNormalBounds().y + getNormalBounds().height/2.0,DEFAULT_RANGE );

        coins_dropped = 4;

        enemy_id = EnemiesFactory.LITTLE_WIZARD;

    }

    @Override
    public void Update() {
        if(!isDead) {
            for(Enemy other : this.refLink.GetMap().getRoom().getEnemies()){
                if(CollisionDetector.checkRectanglesCollision(other.getNormalBounds(), this.normalBounds) && other != this){
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
            updateRange();
            Move();
/*
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
*/
            if(Hero.GetInstance().getNormalBounds().x + Hero.GetInstance().getNormalBounds().width < getNormalBounds().x){
                position = -1;
                x_mirror_offset = width;
            }else {
                position = 1;
                x_mirror_offset = 0;
            }

            if(CollisionDetector.checkIfCircleContainsRectangle(range,Hero.GetInstance().getNormalBounds())) {
                long currentTime = System.currentTimeMillis() / 1000;
                if (currentTime - lastFireballTime > attackSpeed) {
                    throwSpell();
                    Sound.playSound(Sound.fireBall);
                    lastFireballTime = System.currentTimeMillis() / 1000;
                }
            }
            if(Hero.GetInstance().getHeldItem() instanceof Weapon) {
                Weapon weapon = (Weapon)Hero.GetInstance().getHeldItem();
                if (CollisionDetector.checkRectanglesCollision(getNormalBounds(), Hero.GetInstance().getWeaponBounds()) && weapon.isInAttackMode() && !weapon.damageAlreadyGiven()) {
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
        else {
            for(int i=0;i<coins_dropped;++i)
                refLink.GetMap().getRoom().addEntity(new Coin(refLink,this.x + i*5, this.y));
            refLink.GetMap().getRoom().removeEnemy(this);
        }
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
        refLink.GetMap().getRoom().addEntity(new Fireball(refLink,getNormalBounds().x,getNormalBounds().y, new Point((int)(Hero.GetInstance().getNormalBounds().x + Hero.GetInstance().getNormalBounds().width / 2.0) , (int)(Hero.GetInstance().getNormalBounds().y + Hero.GetInstance().getNormalBounds().height / 2.0 ))));
    }

    public void updateRange(){
        this.range.setCenterX(getNormalBounds().x + getNormalBounds().width/2.0);
        this.range.setCenterY(getNormalBounds().y + getNormalBounds().height/2.0);
    }

}
