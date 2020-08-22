package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Enemies.Enemy;
import PaooGame.Items.Potions.LifePotion;
import PaooGame.Items.Potions.Potion;
import PaooGame.Items.Potions.SpeedPotion;
import PaooGame.Items.Traps.HoleTrap;
import PaooGame.Items.Traps.SpikeTrap;
import PaooGame.Items.Traps.Trap;
import PaooGame.Observer.GameObserver;
import PaooGame.Particles.BloodParticle;
import PaooGame.Particles.Fireball;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.UI.Inventory;
import PaooGame.UI.InventoryCell;
import PaooGame.UI.LifeBar;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import java.awt.*;


public class Hero extends Character///SINGLETON
{
    protected final int DEFAULT_LIFE = 5;
    protected final int MAX_LIFE = 10;

    protected final int DEFAULT_BOUNDS_WIDTH = 40;
    protected final int DEFAULT_BOUNDS_HEIGHT = 60;

    private final List<GameObserver> observers;

    private static Hero instance = null;

    private Item heldItem;

    private BloodParticle b = new BloodParticle();

    LifeBar lifeBar;

    protected boolean actionPerformed;

    protected Inventory inventory;

    protected boolean drawOpacity = false;

    long oldTime = System.currentTimeMillis()/1000;

    protected final long DEFAULT_NO_DAMAGE_TIME_IN_SECONDS = (long) 1.5;

    protected boolean speedPotionDrunk = false;

    protected long speedTimer;

    private Hero(RefLinks refLink, float x, float y) {
        super(refLink, x, y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);

        image = new BufferedImage[4];

        image[0] = Assets.hero1;
        image[1] = Assets.hero2;
        image[2] = Assets.hero3;
        image[3] = Assets.hero4;
        currentPos = 0;

        observers = new ArrayList<>();
        this.life = DEFAULT_LIFE;

        lifeBar = new LifeBar(20, 20, 40, 40, life);

        actionPerformed = false;

        collision_offset_y = 40;
        collision_offset_x = 10;

        speed = DEFAULT_SPEED;

        this.normalBounds = new Rectangle((int)x , (int)y , DEFAULT_BOUNDS_WIDTH,DEFAULT_BOUNDS_HEIGHT);

    }


    public static Hero GetInstance() {
        if (instance == null) {
            instance = new Hero(null, 600, 500);
        }
        return instance;
    }

    public void init() {
        inventory = new Inventory(refLink, (float) (refLink.GetGame().GetWidth() / 2.0 - (Inventory.NO_OF_SLOTS * InventoryCell.CELL_WIDTH) / 2.0), 950);
        inventory.putItemInInventory(heldItem);
    }


    @Override
    public void Update() {
        if (life <= 0) {
            refLink.GetGame().getPlayState().setHeroDead(true);
        } else {
            GetInput();
            UpdateBoundsRectangle();
            Move();

            if(speedPotionDrunk) {
                long currentTime = System.currentTimeMillis() / 1000;
                if(currentTime - speedTimer >= SpeedPotion.getSpeedDurationTime()) {
                    speedPotionDrunk = false;
                    speed -= SpeedPotion.getBonusSpeed();
                }
            }


            refLink.GetGame().getCamera().centerCameraOnEntity(this);


            if ((isMovingDown || isMovingUp || isMovingRight || isMovingLeft )&& refLink.GetMap().getRoom().isContainingEnemies())
                for (GameObserver o : observers)
                    o.update();


            if (heldItem != null) {
                heldItem.SetX((int) (x + 15));
                heldItem.SetY((int) (y + 30));
                heldItem.Update();
            }
        }
        long currentTime = System.currentTimeMillis() / 1000;
        if (damaged){
            if (currentTime - oldTime > DEFAULT_NO_DAMAGE_TIME_IN_SECONDS) {
                damaged = false;
            }
        }

        //verific daca iau damage
        if(!damaged) {
            for (Enemy enemy : refLink.GetMap().getRoom().getEnemies())
                if (RectangleCollisionDetector.checkCollision(this.getNormalBounds(), enemy.getNormalBounds())) {
                    this.life -= enemy.getDamage();
                    this.damaged = true;
                    drawOpacity = true;
                    oldTime = System.currentTimeMillis() / 1000;
                }

            for (int i = 0; i < refLink.GetMap().getRoom().getItemList().size(); ++i) {
                if (refLink.GetMap().getRoom().getItemList().get(i) instanceof Fireball) {
                    Fireball tempFireball = (Fireball)refLink.GetMap().getRoom().getItemList().get(i);
                    if (RectangleCollisionDetector.checkCollision(this.getNormalBounds(), tempFireball.getNormalBounds())) {
                        this.life -= Fireball.DAMAGE;
                        this.damaged = true;
                        drawOpacity = true;
                        refLink.GetMap().getRoom().removeItem(tempFireball);
                        Sound.playSound(Sound.fireBallExplosion);
                        oldTime = System.currentTimeMillis() / 1000;
                    }
                }
            }
        }

        for (Item trap : refLink.GetMap().getRoom().getItemList() ) {
            if(trap instanceof Trap) {
                Trap tempTrap = (Trap) trap;
                if (RectangleCollisionDetector.checkCollision(trap.getNormalBounds(), this.getNormalBounds()))
                    tempTrap.activate();

                if (RectangleCollisionDetector.checkCollision(trap.getNormalBounds(), this.getNormalBounds()) && tempTrap.isGivingDamage() && tempTrap instanceof SpikeTrap && !damaged) {
                    this.life -= tempTrap.getDamage();
                    this.damaged = true;
                    drawOpacity = true;
                    oldTime = System.currentTimeMillis() / 1000;

                }

                if (RectangleCollisionDetector.checkCollision(tempTrap.getNormalBounds(), this.getNormalBounds()) && tempTrap instanceof HoleTrap && !damaged) {
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
            ySpeed = -speed;
            isMovingUp = true;
        }

        if (refLink.GetKeyManager().down) {
            ySpeed = speed;
            isMovingDown = true;
        }

        if (refLink.GetKeyManager().left) {
            xSpeed = -speed;
            isMovingLeft = true;

            position = -1;
            x_mirror_offset = this.width;
        }

        if (refLink.GetKeyManager().right) {
            xSpeed = speed;
            isMovingRight = true;

            position = 1;
            x_mirror_offset = 0;
        }

        if (refLink.GetKeyManager().f) {
            if(this.heldItem instanceof LifePotion && life < DEFAULT_LIFE) {
                Potion tempPotion = (Potion) heldItem;
                tempPotion.drinkThisPotion();
                inventory.removeSelectedItem();
                this.heldItem = null;
            }

            if(this.heldItem instanceof SpeedPotion && !speedPotionDrunk) {
                Potion tempPotion = (Potion) heldItem;
                tempPotion.drinkThisPotion();
                inventory.removeSelectedItem();
                this.heldItem = null;
                speedPotionDrunk = true;

                speedTimer = System.currentTimeMillis()/1000;
            }

        }

        if (refLink.GetKeyManager().e) {
            if (!actionPerformed)
                for (Item chest : refLink.GetMap().getRoom().getItemList()) {
                    if (chest instanceof Chest) {
                        Chest tempChest = (Chest)chest;
                        if (RectangleCollisionDetector.checkCollision(this.getNormalBounds(), chest.getNormalBounds()) && !tempChest.isChestOpened()) {
                            tempChest.openChest();
                            actionPerformed = true;
                            break;
                        }
                    }
                }

            if(!actionPerformed)
                for(Item item : refLink.GetMap().getDiscardedItems()) {
                    if (item != null && RectangleCollisionDetector.checkCollision(this.getNormalBounds(), item.getDroppedBounds())) {
                        if(this.inventory.isInventoryFull()){
                            this.inventory.putItemInInventory(item);
                            this.setHeldItem();
                            refLink.GetMap().removeItemFromDiscarded(item);
                            break;
                        }
                    }
                }

        } else {
            actionPerformed = false;
        }

        if (refLink.GetKeyManager().q) {
            inventory.discardSelectedItem();
            this.heldItem = null;
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

        g.drawImage(image[next], (int) (x - refLink.GetGame().getCamera().getXOffset() + x_mirror_offset), (int) (y - refLink.GetGame().getCamera().getYOffset()), position * width, height, null);

        g.setColor(Color.RED);
        if (heldItem != null)
            heldItem.Draw(g);

        g.setColor(Color.green);
        g.drawRect((int) (normalBounds.x - refLink.GetGame().getCamera().getXOffset() + collision_offset_x ), (int) (normalBounds.y - refLink.GetGame().getCamera().getYOffset() + collision_offset_y ), normalBounds.width , normalBounds.height );

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
        if (heldItem != null)
            return this.heldItem.normalBounds;
        else return new Rectangle(0, 0, 0, 0);
    }

    public Item getHeldItem() {
        return this.heldItem;
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

    public void setHeldItem() {
        this.heldItem = inventory.getSelectedSlot();
    }

    public void reset() {
        instance = null;
    }

    public void updateObservers(){
        for (GameObserver o : observers)
            o.update();
    }

    public void heal(int amount){
        if(amount > 0 && amount + life <= MAX_LIFE)
            life += amount;
    }

    public void takeSpeedBurst(float amount){
        speed += amount;
    }

}
