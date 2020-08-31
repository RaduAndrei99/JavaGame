package PaooGame.Items;

import javafx.scene.shape.Circle;

import java.awt.*;

public class CollisionDetector {
    public static boolean checkRectanglesCollision(Rectangle rect1, Rectangle rect2){
        return rect1.x  < rect2.x + rect2.width &&
                rect1.x + rect1.width > rect2.x &&
                rect1.y < rect2.y + rect2.height &&
                rect1.y + rect1.height > rect2.y;
    }

    public static boolean checkIfSecondRectangleContainsFirst(Rectangle first, Rectangle second){
        return first.width <= second.width && first.height <= second.height &&
                first.x >= second.x &&
                first.x + first.width <= second.x + second.width &&
                first.y >= second.y &&
                first.y + first.height <= second.y + second.height;
    }

    public static boolean checkIfCircleContainsRectangle(Circle circle, Rectangle rectangle){
        double x1 = circle.getCenterX();
        double y1 = circle.getCenterY();

        double x2 = rectangle.x + rectangle.width/2.0;
        double y2 = rectangle.y + rectangle.height/2.0;

        return Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1))) < circle.getRadius();
    }
}
