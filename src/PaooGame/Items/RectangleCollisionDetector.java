package PaooGame.Items;

import PaooGame.GameWindow.Camera;

import java.awt.*;

public class RectangleCollisionDetector {
    public static boolean checkCollision(Rectangle rect1, Rectangle rect2){
        return rect1.x  < rect2.x + rect2.width &&
                rect1.x + rect1.width > rect2.x &&
                rect1.y < rect2.y + rect2.height &&
                rect1.y + rect1.height > rect2.y;
    }
}
