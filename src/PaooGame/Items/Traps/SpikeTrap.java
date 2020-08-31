package PaooGame.Items.Traps;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import PaooGame.Sound.Sound;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpikeTrap extends Trap{
    public static int DEFAULT_WIDTH = Tile.TILE_WIDTH;
    public static int DEFAULT_HEIGHT = Tile.TILE_HEIGHT;

    int wait = 0;
    protected BufferedImage []sprites;

    private int current_sprite_index;

    private boolean isActivated = false;
    private boolean isGivingDamage = false;

    public SpikeTrap(RefLinks refLink, float x, float y) {
        super(refLink, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        sprites = new BufferedImage[7];

        sprites[0] = Assets.spikeAsset1;
        sprites[1] = Assets.spikeAsset2;
        sprites[2] = Assets.spikeAsset3;
        sprites[3] = Assets.spikeAsset4;
        sprites[4] = Assets.spikeAsset3;
        sprites[5] = Assets.spikeAsset2;
        sprites[6] = Assets.spikeAsset1;

        current_sprite_index = 0;
    }

    @Override
    public void Update() {
        setIsGivingDamage();


        if(current_sprite_index == sprites.length - 1)
            isActivated = false;
    }

    @Override
    public void Draw(Graphics g) {
        g.drawImage(sprites[setNextSprite()],(int)(x - refLink.GetGame().getCamera().getXOffset()),(int)(y - refLink.GetGame().getCamera().getYOffset()),width,height,null);
    }

    public void activate(){
        isActivated = true;
    }

    @Override
    public boolean isGivingDamage() {
        return isGivingDamage;
    }

    protected int setNextSprite(){
        if(isActivated) {
            wait++;
            if (wait > 10) {
                wait = 0;

                if(current_sprite_index == 1)
                    Sound.playSound(Sound.spike_trap);
                if (current_sprite_index < sprites.length - 1)
                    return current_sprite_index++;
                else
                    return current_sprite_index = 0;
            }
            return current_sprite_index;
        }
        else
            return 0;
    }

    protected void setIsGivingDamage(){
        if(current_sprite_index >=2 && current_sprite_index <= 4)
            isGivingDamage = true;
        else
            isGivingDamage = false;
    }

}
