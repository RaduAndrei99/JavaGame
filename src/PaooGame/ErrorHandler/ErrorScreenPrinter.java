package PaooGame.ErrorHandler;

import java.awt.*;
import java.util.Stack;

public class ErrorScreenPrinter {
    protected static Stack<String> errorStack;

    protected static int errors_in_stack = 0;
    protected static int MAX_FRAMES = 500;
    protected static int MAX_ERRORS_IN_STACK = 100;
    protected int wait_frames = 0;

    public ErrorScreenPrinter(){
        errorStack = new Stack<>();
    }

    public void Draw(Graphics g) {
        if(wait_frames < MAX_FRAMES) {
            if (!errorStack.isEmpty()) {
                g.setColor(Color.GREEN);
                g.drawString(errorStack.peek(), 50, Toolkit.getDefaultToolkit().getScreenSize().height - 50);
            }
            wait_frames++;
        }
        else{
            wait_frames = 0;
            if(!errorStack.isEmpty()){
                errorStack.pop();
            }
        }

    }

    public static void addErrorMessage(String str){
        if(errors_in_stack < MAX_ERRORS_IN_STACK)
            errorStack.push(str);
    }

}
