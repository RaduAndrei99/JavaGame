package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;

import PaooGame.Items.Coin;
import PaooGame.Items.CollisionDetector;
import PaooGame.Items.Hero;
import PaooGame.Items.Key;
import PaooGame.Items.Weapons.Excalibur;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Random;

public class BigOrc extends Enemy {

    public static int DEFAULT_SPRITE_WIDTH = 32*4;
    public static int DEFAULT_SPRITE_HEIGHT = 36*4;
    public static int DEFAULT_SPEED = 2;

    public static int DEFAULT_BOUNDS_WIDTH = 60;
    public static int DEFAULT_BOUNDS_HEIGHT = 90;


    public BigOrc(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_SPRITE_WIDTH, DEFAULT_SPRITE_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.bigOrc1;
        image[1] = Assets.bigOrc2;
        image[2] = Assets.bigOrc3;
        image[3] = Assets.bigOrc4;

        this.life = 1000;
        this.damage = 3;

        collision_offset_y = 40;
        collision_offset_x = 40;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
        this.speed = DEFAULT_SPEED;

        moans = new File[2];

        moans[0] = Sound.death_big_orc;
        moans[1] = Sound.orc_sound1;


        enemy_id = EnemiesFactory.BIG_ORC;

        coins_dropped = 10;
    }


    @Override
    public void Update() {
        if (!isDead) {
            for (Enemy other : this.refLink.GetMap().getRoom().getEnemies()) {
                if (CollisionDetector.checkRectanglesCollision(other.getNormalBounds(), this.normalBounds) && other != this) {
                    if (this.x <= other.GetX()) {
                        xSpeed = -5;
                    } else {
                        xSpeed = 5;
                    }
                    if (this.y <= other.GetY()) {
                        ySpeed = -5;
                    } else {
                        ySpeed = 5;
                    }
                }
            }

            UpdateBoundsRectangle();

            this.Move();

            if (moans != null) {
                Random rand = new Random();
                int r = rand.nextInt(500) + 1;
                int soundIndex = rand.nextInt(moans.length);
                if (r == 1) {
                    Sound.playSound(moans[soundIndex]);
                }
            }

            int start = (int) ((x + width / 2) / Tile.TILE_WIDTH) + (int) ((y + height / 2) / Tile.TILE_HEIGHT) * refLink.GetMap().getWidth();
            int end = (int) (Hero.GetInstance().GetX() + Hero.GetInstance().GetWidth() / 2) / Tile.TILE_HEIGHT + (int) ((Hero.GetInstance().GetY() + Hero.GetInstance().GetHeight() / 2) / Tile.TILE_HEIGHT) * refLink.GetMap().getWidth();
            List<Integer> path = refLink.GetGame().getPathFinder().GetPath(end, start);

            if ((path != null && path.size() > 0))
                goToTile(path.remove(0));
            else {
                if (start != end) {
                    goToTile(end);
                }
            }


            if (Hero.GetInstance().getHeldItem() instanceof Weapon) {
                Weapon weapon = (Weapon) Hero.GetInstance().getHeldItem();
                if (CollisionDetector.checkRectanglesCollision(getNormalBounds(), Hero.GetInstance().getWeaponBounds()) && weapon.isInAttackMode() && !weapon.damageAlreadyGiven()) {
                    this.life -= weapon.getSwordDamage();
                    weapon.signalDamage();
                    this.blood.resetAnimation();
                    this.getHit();
                }
            }


            if (life < 0) {
                isDead = true;
                if (moans != null)
                    Sound.playSound(moans[0]);
            }

        } else {
            for (int i = 0; i < coins_dropped; ++i)
                refLink.GetMap().getRoom().addEntity(new Coin(refLink, this.x + i * 5, this.y));
            refLink.GetMap().getRoom().addEntity(new Key(refLink,this.x,this.y));
            refLink.GetMap().getRoom().removeEnemy(this);
        }
    }

}
