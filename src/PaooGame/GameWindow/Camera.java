package PaooGame.GameWindow;

import PaooGame.RefLinks;
import PaooGame.Items.Character;
import java.awt.*;

public class Camera {
    protected int x, y;
    protected int width, height;
    RefLinks rl;
    float xOffset,yOffset;

    public Camera(RefLinks refs,int x, int y,int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rl = refs;
        xOffset = 0;
        yOffset = 0;
    }


    public float getX(){
        return this.x;
    }


    public float getY(){
        return this.y;
    }

    public void move(float xAmount, float yAmount){
        this.xOffset += xAmount;
        this.yOffset += yAmount;
    }

    public void centerCameraOnEntity(Character c){
        //xOffset = (float) (c.GetX() - this.rl.GetGame().GetWidth()/2.0 + c.GetWidth()/2.0);
        //yOffset = (float) (c.GetY() - this.rl.GetGame().GetHeight()/2.0 + c.GetHeight()/2.0);

        if(c.GetX() < this.x + xOffset) {
            xOffset = c.GetX() - this.x;
        }

        if(c.GetX() + c.GetWidth() > this.x + xOffset + this.width )
            xOffset = (c.GetX() + c.GetWidth()) - (this.x+ this.width);

        if(c.GetY() < this.y + yOffset)
            yOffset = c.GetY() - this.y;

        if(c.GetY() + c.GetHeight() > this.y + yOffset+ this.height )
            yOffset = (c.GetY() + c.GetHeight()) - (this.y + this.height);

    }


    public void draw(Graphics g){
       // g.setColor(Color.CYAN);
        //g.drawRect(this.x,this.y ,this.width,this.height);
    }

    public float getXOffset(){
        return this.xOffset;
    }

    public float getYOffset(){
        return this.yOffset;
    }

}
