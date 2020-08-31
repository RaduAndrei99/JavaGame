package PaooGame.UI;

import PaooGame.Input.GameMouseListener;
import PaooGame.Items.Hero;
import PaooGame.Items.Item;
import PaooGame.Items.ItemFactory;
import PaooGame.RefLinks;

import java.util.List;


import java.awt.*;

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

        int notches = GameMouseListener.notches;

        if (notches < 0) {
            Hero.GetInstance().getInventory().decrementSelectedSlot();
            Hero.GetInstance().setHeldItem();
            GameMouseListener.resetNotches();

        } else {
            if(notches > 0) {
                Hero.GetInstance().getInventory().incrementSelectedSlot();
                Hero.GetInstance().setHeldItem();
                GameMouseListener.resetNotches();
            }
        }

    }

    public void selectSlot(int slot){
        inventory_slots[selected_slot].setCellNormal();
        inventory_slots[slot].setCellSelected();
        selected_slot = slot;
    }

    public boolean isInventoryFull(){
        return stored_items != NO_OF_SLOTS;
    }

    public void putItemInInventory(Item item){
        if(isInventoryFull() && item != null)
            for(InventoryCell cell : inventory_slots) {
                if (!cell.isSlotOccupied()) {
                    item.pickItem();
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
                temp.dropItem();
                refs.GetMap().getRoom().addEntity(temp);

                stored_items--;
            }
        }
    }

    public void removeSelectedItem() {
        this.inventory_slots[selected_slot].removeStoredItem();
        stored_items --;
    }

    public InventoryCell[] getInventorySlots(){
        return this.inventory_slots;
    }

    public void loadInventory(int []items){
            if(items.length >= NO_OF_SLOTS){
                resetInventory();
                selected_slot = 0;
                for(int i=0;i<NO_OF_SLOTS;++i){
                    if(items[i]!=-1){
                        this.putItemInInventory(ItemFactory.getItem(refs,items[i],(int)x + i*InventoryCell.CELL_WIDTH,(int)y));
                    }
                }
            }

            Hero.GetInstance().setHeldItem();
    }

    public void resetInventory() {
        for (InventoryCell inventory_slot : inventory_slots)
            inventory_slot.removeStoredItem();

        stored_items = 0;
        selected_slot = 0;
    }

    public void dropAllItems(){
        for(InventoryCell cell : this.inventory_slots){
            if(cell.getItem() != null) {
                refs.GetMap().getRoom().addEntity(cell.getItem());
                cell.getItem().dropItem();

                stored_items--;

            }
        }
    }

}