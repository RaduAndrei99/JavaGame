package PaooGame.Maps;

import PaooGame.Items.Chest;
import PaooGame.Items.Enemies.BigDemon;
import PaooGame.Items.Enemies.Enemy;
import PaooGame.Items.Item;
import PaooGame.Items.Weapons.BasicSword;
import PaooGame.Items.Weapons.GoldenSword;
import PaooGame.Items.Weapons.MightySword;
import PaooGame.Maps.Rooms.LevelSpawner;
import PaooGame.RefLinks;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public class Level2 extends Map {
    public Level2(RefLinks refLink) {
        super(refLink);
        enemies.add(new BigDemon(refLink,900,500));
        //enemies.add(new BigDemon(refLink,900,600));
        //enemies.add(new BigDemon(refLink,800,200));
       // enemies.add(new BigDemon(refLink,700,200));
       // enemies.add(new BigDemon(refLink,1000,400));

        Chest temp_chest = new Chest(refLink,1100,3*48,50,50);
        temp_chest.putItem(new BasicSword(refLink,temp_chest.GetX(),temp_chest.GetY() + 10));
        chests.add(temp_chest);

        temp_chest = new Chest(refLink,1200,3*48,50,50);
        temp_chest.putItem(new GoldenSword(refLink,temp_chest.GetX(),temp_chest.GetY() + 10));
        chests.add(temp_chest);

        temp_chest = new Chest(refLink,1300,3*48,50,50);
        temp_chest.putItem(new MightySword(refLink,temp_chest.GetX(),temp_chest.GetY() + 10));
        chests.add(temp_chest);

    }

    @Override
    public void Update() {
        resetSolidTiles();
        for(Enemy enemy : enemies)
            enemy.Update();
    }

    @Override
    public void Draw(Graphics g)
    {
        Tile t;

        int xStart = (int) Math.max(0, refs.GetGame().getCamera().getXOffset()/Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width,(refs.GetGame().getCamera().getXOffset() + refs.GetGame().GetWidth()) / Tile.TILE_WIDTH +1);
        int yStart = (int) Math.max(0,refs.GetGame().getCamera().getYOffset()/Tile.TILE_HEIGHT);
        int yEnd  = (int) Math.min(height,(refs.GetGame().getCamera().getYOffset() + refs.GetGame().GetHeight()) / Tile.TILE_WIDTH + 1);

        ///Se parcurge matricea de dale (codurile aferente) si se deseneaza harta respectiva
        for(int y = yStart; y < yEnd; y++)
        {
            for(int x = xStart; x <xEnd; x++)
            {
                t = GetTile(y,x);
                if(t != null) {
                    t.Draw(g, (int)(x * Tile.TILE_HEIGHT - this.refs.GetGame().getCamera().getXOffset()), (int)(y * Tile.TILE_WIDTH - this.refs.GetGame().getCamera().getYOffset()));
                    if(refs.GetMap().isTileSolid(y,x))
                    {
                        g.setColor(Color.BLUE);

                    }
                    else
                    {
                        g.setColor(Color.GREEN);
                    }

                   // g.drawRect((int)(x * Tile.TILE_HEIGHT - this.refs.GetGame().getCamera().getXOffset()), (int)(y * Tile.TILE_WIDTH - this.refs.GetGame().getCamera().getYOffset()),48,48);
                }
                //g.setColor(Color.GREEN);
                //g.drawString(String.valueOf(y*this.width+x), (int)(x *Tile.TILE_HEIGHT - this.refs.GetGame().getCamera().getXOffset()), (int)((y+1) * Tile.TILE_WIDTH  - this.refs.GetGame().getCamera().getYOffset()));
            }
        }

        for(Item item : discarded_items){
            float angle = 90;

            if(item != null) {
                AffineTransform at = AffineTransform.getTranslateInstance(item.GetX() - refs.GetGame().getCamera().getXOffset(), item.GetY() - refs.GetGame().getCamera().getYOffset() + 50);
                at.rotate(Math.toRadians(angle), (float) this.width / 2, this.height / 2);
                at.scale(3, 3);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage(item.getImage(), at, null);
                g2d.drawRect((int) (item.getNormalBounds().x - refs.GetGame().getCamera().getXOffset()), (int) (item.getNormalBounds().y - refs.GetGame().getCamera().getYOffset()), item.getNormalBounds().width, item.getNormalBounds().height);
            }
        }

        for(Chest chest : chests)
            chest.Draw(g);

        for(Enemy enemy : enemies)
            enemy.Draw(g);
    }

    @Override
    int currentLevelMap(int x, int y) {
        final int[][] map ={
                {19,11,32,11,11,32,11,11,32,11,11,32,11,11,32,11,11,32,11,11,11,11,32,11,11,32,11,11,32,11,11,32,11,11,32,11,11,32,11,21},
                {13,10,33,10,10,33,10,10,33,10,10,33,10,10,33,10,10,33,10,10,10,10,33,10,10,33,10,10,33,10,10,33,10,10,33,10,10,33,10,14},
                {13,2,34,2,2,34,2,2,34,2,2,34,2,2,34,2,2,34,2,2,2,2,34,2,2,34,2,2,34,2,2,34,2,2,34,2,2,34,2,14},
                { 13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14},
                { 13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14},
                {13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14},
                {28,24,24,24,30,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,31,24,24,24,29},
                {19,11,11,11,16,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,18,11,11,11,21},
                {13,10,10,35,10,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,10,35,10,10,14},
                {13,2,2,36,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,36,2,2,14},
                { 13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14},
                { 13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14},
                { 28,24,24,24,24,24,24,24,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,31,24,24,24,24,24,24,29},
                {0,0,0,0,0,0,0,13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,28,24,24,30,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,31,24,24,29,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0,13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14,0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0,13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14,0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0,13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14,0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0,13,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,14,0,0,0,0,0,0,0,0,0,0},
                { 0,0,0,0,0,0,0,0,0,0,28,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,24,29,0,0,0,0,0,0,0,0,0,0}
        };
        return map[x][y];

    }

}
