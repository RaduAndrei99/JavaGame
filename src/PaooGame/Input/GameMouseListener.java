package PaooGame.Input;

import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.States.MenuState;
import PaooGame.States.State;
import PaooGame.UI.Menu.MainMenu;

import java.awt.*;
import java.awt.event.*;

public class GameMouseListener implements MouseListener, MouseWheelListener {

    static public boolean isLeftMousePressed = false;
    static public boolean isRightMousePressed = false;

    static public boolean isRightMouseClicked = false;
    static public boolean isLeftMouseClicked = false;
    static public boolean isLeftMouseReleased = true;
    static public boolean isRightMouseReleased = true;



    public static RefLinks refs;

    public GameMouseListener(RefLinks refLinks){
        this.refs = refLinks;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        switch(mouseEvent.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                isLeftMousePressed = true;
                isLeftMouseReleased = false;
                // System.out.println("That's the LEFT button");
                break;
            }
            case InputEvent.BUTTON2_MASK: {
                //System.out.println("That's the MIDDLE button");
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                isRightMousePressed = true;
                isRightMouseReleased = false;
               // System.out.println("That's the RIGHT button");
                break;
            }
        }
    }


    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        isLeftMousePressed = false;
        isRightMousePressed = false;
        isLeftMouseClicked = false;
        isRightMouseClicked = false;
        isLeftMouseReleased = true;
        isRightMouseReleased = true;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) { ;
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        isLeftMousePressed = false;
        isLeftMouseClicked = false;
    }

    public void Update()
    {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {

        int notches = mouseWheelEvent.getWheelRotation();
        if (notches < 0) {
            Hero.GetInstance().getInventory().decrementSelectedSlot();
        } else {
            if(notches > 0) {
                Hero.GetInstance().getInventory().incrementSelectedSlot();
            }
        }

        Hero.GetInstance().setWeapon();

    }

    public static Point getMouseCoordinates(){
        if (State.GetState() instanceof MenuState)
            return new Point((MouseInfo.getPointerInfo().getLocation().x - GameMouseListener.refs.GetGame().GetGameWindow().GetWndFrame().getX()),
                    MouseInfo.getPointerInfo().getLocation().y - GameMouseListener.refs.GetGame().GetGameWindow().GetWndFrame().getY() );
        else
            return new Point((MouseInfo.getPointerInfo().getLocation().x - GameMouseListener.refs.GetGame().GetGameWindow().GetWndFrame().getX() + (int)refs.GetGame().getCamera().getXOffset()),
                MouseInfo.getPointerInfo().getLocation().y - GameMouseListener.refs.GetGame().GetGameWindow().GetWndFrame().getY()  + (int) refs.GetGame().getCamera().getYOffset());
    }

    public void mouseClicked(MouseEvent e) {
        switch(e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                isLeftMouseClicked = true;
                break;
            }
            case InputEvent.BUTTON2_MASK: {
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                isRightMouseClicked = true;
                break;
            }
        }
    }
}