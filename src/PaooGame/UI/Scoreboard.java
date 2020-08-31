package PaooGame.UI;

import PaooGame.Graphics.Assets;
import PaooGame.RefLinks;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Stack;;

public class Scoreboard extends UIElement {
    protected static final int DEFAULT_WIDTH = 50;
    protected static final int DEFAULT_HEIGHT = 50;

    BufferedImage []numbers;

    protected static int score = 0;
    public Scoreboard(RefLinks refs, float x, float y) {
        super(refs, x, y);

        numbers = new BufferedImage[10];

        numbers[0] = Assets.number0;
        numbers[1] = Assets.number1;
        numbers[2] = Assets.number2;
        numbers[3] = Assets.number3;
        numbers[4] = Assets.number4;
        numbers[5] = Assets.number5;
        numbers[6] = Assets.number6;
        numbers[7] = Assets.number7;
        numbers[8] = Assets.number8;
        numbers[9] = Assets.number9;
    }

    @Override
    public void Draw(Graphics g) {
        int tempScore = score;

        Stack<Integer> stack = new Stack<>();
        while(tempScore / 10 != 0){
            stack.push(tempScore % 10);
            tempScore = tempScore / 10;
        }
        stack.push(tempScore);

        int i=0;
        while(!stack.isEmpty()){
            g.drawImage(numbers[stack.pop()],(int)x + i++*DEFAULT_WIDTH,(int)y,DEFAULT_WIDTH,DEFAULT_HEIGHT,null);
        }
    }

    @Override
    public void Update() {
        throw new NotImplementedException();
    }

    public static void updateScore(int amount){
        score += amount;
    }
    public static int getCurrentScore(){
        return score;
    }

    public static void setScore(int value){
        score = value;
    }

    public static void resetScore(){
        score = 0;
    }
}
