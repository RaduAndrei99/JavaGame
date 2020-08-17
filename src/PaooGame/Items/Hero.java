package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Enemies.Enemy;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.Observer.GameObserver;
import PaooGame.Particles.BloodParticle;
import PaooGame.RefLinks;
import PaooGame.UI.Inventory;
import PaooGame.UI.InventoryCell;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;


public class Hero extends Character///SINGLETON
{
    private final List<GameObserver> observers;

    private static Hero instance = null;

    private Weapon weapon;

    private BloodParticle b = new BloodParticle();

    LifeBar lifeBar;

    protected boolean actionPerformed;

    protected Inventory inventory;

    protected boolean drawOpacity = false;

    long oldTime = System.currentTimeMillis()/1000;

    protected final long DEFAULT_NO_DAMAGE_TIME_IN_SECONDS = (long) 1.5;

    private Hero(RefLinks refLink, float x, float y) {
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.hero1;
        image[1] = Assets.hero2;
        image[2] = Assets.hero3;
        image[3] = Assets.hero4;
        currentPos = 0;

        observers = new ArrayList<>();
        this.life = 6;

        lifeBar = new LifeBar(20, 20, 40, 40, life);

        actionPerformed = false;

        //collision_offset_y = 50;
    }


    public static Hero GetInstance() {
        if (instance == null) {
            instance = new Hero(null, 500, 500);
        }
        return instance;
    }

    public void init() {
        inventory = new Inventory(refLink, (float) (refLink.GetGame().GetWidth() / 2.0 - (Inventory.NO_OF_SLOTS * InventoryCell.CELL_WIDTH) / 2.0), 950);
        inventory.putItemInInventory(weapon);
    }


    @Override
    public void Update() {
        if (life <= 0) {
            refLink.GetGame().getPlayState().setHeroDead(true);
        } else {
            GetInput();
            UpdateBoundsRectangle();
            Move();
            refLink.GetGame().getCamera().centerCameraOnEntity(this);


            if (isMovingDown || isMovingUp || isMovingRight || isMovingLeft)
                for (GameObserver o : observers)
                    o.update();

            if (refLink.GetKeyManager().left) {
                position = -1;
                width_offset = this.width;


            } else {
                if (refLink.GetKeyManager().right) {
                    position = 1;
                    width_offset = 0;
                }
            }

            if (weapon != null) {
                weapon.setX((int) (x + 15));
                weapon.setY((int) (y + 10));

                weapon.Update();
            }
        }
        long currentTime = System.currentTimeMillis() / 1000;
        if (damaged){
            if (currentTime - oldTime > DEFAULT_NO_DAMAGE_TIME_IN_SECONDS) {
                damaged = false;
            }
        }


        for(Enemy enemy : refLink.GetMap().getEnemies())
            if ( RectangleCollisionDetector.checkCollision(this.normalBounds,enemy.getNormalBounds())  && !damaged){
                this.life -= enemy.getDamage();
                this.damaged = true;
                drawOpacity = true;
                oldTime = System.currentTimeMillis() / 1000;
            }

        for (Item trap : refLink.GetMap().getRoom().getItemList() ) {
            if(trap instanceof Trap) {
                Trap tempTrap = (Trap) trap;
                if (RectangleCollisionDetector.checkCollision(trap.getNormalBounds(), this.normalBounds))
                    tempTrap.activate();

                if (RectangleCollisionDetector.checkCollision(trap.getNormalBounds(), this.normalBounds) && tempTrap.isGivingDamage() && tempTrap instanceof SpikeTrap && !damaged) {
                    this.life -= tempTrap.getDamage();
                    this.damaged = true;
                    drawOpacity = true;
                    oldTime = System.currentTimeMillis() / 1000;

                }

                if (RectangleCollisionDetector.checkCollision(tempTrap.getNormalBounds(), this.normalBounds) && tempTrap instanceof HoleTrap && !damaged) {
                    this.life = 0;
                    this.damaged = true;
                    drawOpacity = true;
                    oldTime = System.currentTimeMillis() / 1000;
                }
            }

        }
    }

    private void GetInput() {
        xSpeed = 0;
        ySpeed = 0;
        isMovingLeft = false;
        isMovingDown = false;
        isMovingUp = false;
        isMovingRight = false;

        if (refLink.GetKeyManager().up) {
            ySpeed = -DEFAULT_SPEED;
            isMovingUp = true;
        }

        if (refLink.GetKeyManager().down) {
            ySpeed = DEFAULT_SPEED;
            isMovingDown = true;
        }

        if (refLink.GetKeyManager().left) {
            xSpeed = -DEFAULT_SPEED;
            isMovingLeft = true;
        }

        if (refLink.GetKeyManager().right) {
            xSpeed = DEFAULT_SPEED;
            isMovingRight = true;
        }

        if (refLink.GetKeyManager().e) {
            if (!actionPerformed)
                for (Item chest : refLink.GetMap().getRoom().getItemList()) {
                    if (chest instanceof Chest) {
                        Chest tempChest = (Chest)chest;
                        if (RectangleCollisionDetector.checkCollision(this.normalBounds, chest.getNormalBounds()) && !tempChest.isChestOpened()) {
                            tempChest.openChest();
                            actionPerformed = true;
                            break;
                        }
                    }
                }

            if(!actionPerformed)
                for(Item item : refLink.GetMap().getDiscardedItems()) {
                    if (item != null && RectangleCollisionDetector.checkCollision(this.normalBounds, item.getDroppedBounds())) {
                        this.inventory.putItemInInventory(item);
                        this.setWeapon();
                        refLink.GetMap().removeItemFromDiscarded(item);
                        break;
                    }
                }

        } else {
            actionPerformed = false;
        }

        if (refLink.GetKeyManager().q) {
            inventory.discardSelectedItem();
            this.weapon = null;
        }

        if (refLink.GetKeyManager().esc) {
            refLink.GetGame().SetMenuState();
        }

    }

    @Override
    public void Draw(Graphics g) {

        int next = nextPos();

        BufferedImage tmpImg;
        if(damaged) {
            if(drawOpacity) {
                tmpImg = new BufferedImage(image[next].getWidth(), image[next].getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = (Graphics2D) tmpImg.getGraphics();
                g2d.setComposite(AlphaComposite.SrcOver.derive(0.2f));
                g2d.drawImage(image[next], 0, 0, null);
                image[next] = tmpImg;
                drawOpacity = false;
            }

        }
        else{
            image[0] = Assets.hero1;
            image[1] = Assets.hero2;
            image[2] = Assets.hero3;
            image[3] = Assets.hero4;
        }

        g.drawImage(image[next], (int) (x - refLink.GetGame().getCamera().getXOffset() + width_offset), (int) (y - refLink.GetGame().getCamera().getYOffset()), position * width, height, null);

        g.setColor(Color.RED);
        if (weapon != null)
            weapon.Draw(g);

        g.setColor(Color.green);
        g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset()), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset() + collision_offset_y), normalBounds.width, normalBounds.height - collision_offset_y);

        lifeBar.Draw(g);

        inventory.Draw(g);
    }

    public void SetRefLink(RefLinks r) {
        this.refLink = r;
    }

    public void RegisterObserver(GameObserver o) {
        this.observers.add(o);
    }

    public Rectangle getWeaponBounds() {
        if (weapon != null)
            return this.weapon.normalBounds;
        else return new Rectangle(0, 0, 0, 0);
    }

    public Weapon getWeapon() {
        return this.weapon;
    }

    public void getDamage(float damage) {
        this.life -= damage;
    }

    public int GetCurrentLife() {
        return this.life;
    }

    public void notifyObservers() {
        for (GameObserver o : observers)
            o.update();
    }

    public boolean isHeroMovingRight() {
        return this.isMovingRight;
    }

    public boolean isHeroMovingLeft() {
        return this.isMovingLeft;
    }

    public boolean isHeroMovingUp() {
        return this.isMovingUp;
    }

    public boolean isHeroMovingDown() {
        return this.isMovingDown;
    }

    public RefLinks getRefs() {
        return this.refLink;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setWeapon() {
        this.weapon = (Weapon) inventory.getSelectedSlot();
    }

    public void reset() {
        instance = null;
    }


}
