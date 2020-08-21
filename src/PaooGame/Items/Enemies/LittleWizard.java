package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Enemies.Enemy;
import PaooGame.Items.Hero;
import PaooGame.Items.Item;
import PaooGame.Items.RectangleCollisionDetector;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.Particles.Fireball;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class LittleWizard extends Enemy {

    public static int DEFAULT_WIDTH = 16*4;
    public static int DEFAULT_HEIGHT = 16*5;
    public static int DEFAULT_SPEED = 1;

    public static int DEFAULT_BOUNDS_WIDTH = 40;
    public static int DEFAULT_BOUNDS_HEIGHT = 50;

    protected long attackSpeed = 1;
    protected long lastFireballTime;

    Fireball fireball;

    public LittleWizard(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.littleWizard1;
        image[1] = Assets.littleWizard2;
        image[2] = Assets.littleWizard3;
        image[3] = Assets.littleWizard4;

        this.life = 150;
        this.damage = 1;

        collision_offset_y = 20;
        collision_offset_x = 10;

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

            long currentTime = System.currentTimeMillis()/1000;
            if(currentTime - lastFireballTime > attackSpeed){
                throwFireball();
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
                Sound.playSound(Sound.death_big_demon);
            }

        }
        else
            refLink.GetMap().getRoom().removeEnemy(this);
    }

    public void throwFireball(){
        refLink.GetMap().getRoom().addEntity(new Fireball(refLink,getNormalBounds().x,getNormalBounds().y));
    }

}
