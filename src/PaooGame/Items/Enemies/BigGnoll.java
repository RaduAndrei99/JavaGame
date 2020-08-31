package PaooGame.Items.Enemies;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Coin;
import PaooGame.Items.CollisionDetector;
import PaooGame.Items.Hero;
import PaooGame.Items.Key;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.Particles.BlueSpell;
import PaooGame.Particles.Fireball;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Random;

public class BigGnoll extends Enemy implements Wizard {

    public static int DEFAULT_WIDTH = 32*4;
    public static int DEFAULT_HEIGHT = 36*4;
    public static int DEFAULT_SPEED = 2;

    public static int DEFAULT_BOUNDS_WIDTH = 60;
    public static int DEFAULT_BOUNDS_HEIGHT = 80;

    protected long attackSpeed = 5;
    protected long lastFireballTime;

    public BigGnoll(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.bigGnoll1;
        image[1] = Assets.bigGnoll2;
        image[2] = Assets.bigGnoll3;
        image[3] = Assets.bigGnoll4;

        this.life = 1000;
        this.damage = 2;

        collision_offset_y = 50;
        collision_offset_x = 40;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);
        this.speed = DEFAULT_SPEED;

        enemy_id = EnemiesFactory.BIG_GNOLL;

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

            long currentTime = System.currentTimeMillis()/1000;
            if(currentTime - lastFireballTime > attackSpeed){
                throwSpell();
                Sound.playSound(Sound.blueSpell);
                lastFireballTime = System.currentTimeMillis()/1000;
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
            refLink.GetMap().getRoom().addEntity(new Key(refLink,x - 5,y));
            refLink.GetMap().getRoom().removeEnemy(this);
        }
    }

    @Override
    public void throwSpell() {
        refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y, new Point((int)(getNormalBounds().x +getNormalBounds().width / 2.0) , (int)(getNormalBounds().y - Toolkit.getDefaultToolkit().getScreenSize().height +getNormalBounds().height / 2.0 ))));
        refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y, new Point((int)(getNormalBounds().x +getNormalBounds().width / 2.0) , (int)(getNormalBounds().y + Toolkit.getDefaultToolkit().getScreenSize().height +getNormalBounds().height / 2.0 ))));

        refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y, new Point((int)(getNormalBounds().x + Toolkit.getDefaultToolkit().getScreenSize().width + getNormalBounds().width / 2.0) , (int)(getNormalBounds().y + getNormalBounds().height / 2.0 ))));
        refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y, new Point((int)(getNormalBounds().x - Toolkit.getDefaultToolkit().getScreenSize().width + getNormalBounds().width / 2.0) , (int)(getNormalBounds().y + getNormalBounds().height / 2.0 ))));

        refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y, new Point(getNormalBounds().x + 10*getNormalBounds().width , 0 )));
        refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y, new Point(getNormalBounds().x - 10*getNormalBounds().width , 0 )));

        refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y, new Point(getNormalBounds().x + 10*getNormalBounds().width,getNormalBounds().y + 10*getNormalBounds().height)));
        refLink.GetMap().getRoom().addEntity(new BlueSpell(refLink,getNormalBounds().x,getNormalBounds().y, new Point(getNormalBounds().x - 10*getNormalBounds().width,getNormalBounds().y + 10*getNormalBounds().height)));

    }
}
