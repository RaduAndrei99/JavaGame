package PaooGame.UI;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.Items.Item;
import PaooGame.Items.Weapons.Weapon;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Inventory extends UIElement {
    protected float x, y;

    public static int NO_OF_SLOTS = 5;


    protected InventoryCell []inventory_slots;

    protected int stored_items;

    protected int selected_slot;

    public Inventory(RefLinks refs, float x, float y){
        super(refs,x,y);

        inventory_slots = new InventoryCell[NO_OF_SLOTS];

        for(int i =0; i<inventory_slots.length;++i)
            inventory_slots[i] = new InventoryCell(refs,x + i*InventoryCell.CELL_WIDTH,y);

        stored_items = 0;

        selected_slot = 0;

        selectSlot(selected_slot);
    }


    public void Draw(Graphics g){
        for(InventoryCell cell : inventory_slots)
            cell.Draw(g);
    }

    @Override
    public void Update() {

    }

    public void selectSlot(int slot){
        inventory_slots[selected_slot].setCellDiscarded();
        inventory_slots[slot].setCellSelected();
        selected_slot = slot;
    }

    public boolean isInventoryFull(){
        return stored_items == NO_OF_SLOTS;
    }

    public void putItemInInventory(Item item){
        if(!isInventoryFull() && item != null)
            for(InventoryCell cell : inventory_slots) {
                if (!cell.isSlotOccupied()) {
                    cell.putItemInSlot(item);
                    stored_items++;
                    break;
                }
            }
    }

    public void incrementSelectedSlot(){
        if(selected_slot<4)
            selectSlot( selected_slot + 1);
        else
            selectSlot(0);
    }

    public void decrementSelectedSlot(){
        if(selected_slot == 0)
            selectSlot( NO_OF_SLOTS - 1);
        else
            selectSlot(selected_slot - 1);
    }

    public Item getSelectedSlot(){
        return this.inventory_slots[selected_slot].getItem();
    }

    public void discardSelectedItem(){
        if(stored_items>0) {
            Item temp = this.inventory_slots[selected_slot].removeStoredItem();
            if (temp != null) {
                temp.SetX(Hero.GetInstance().GetX());
                temp.SetY(Hero.GetInstance().GetY());
                refs.GetMap().addDiscardedItem(temp);

                stored_items--;
            }
        }
    }
}
