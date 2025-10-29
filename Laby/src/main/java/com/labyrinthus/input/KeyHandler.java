package com.labyrinthus.input;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandler{
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public boolean savePressed = false;
    public boolean loadPressed = false;

    public boolean pausePressed = false;

    public void keyPressed(KeyEvent e) {
        KeyCode code = e.getCode();
        if(code == KeyCode.W){
            upPressed = true;
        }
        if(code == KeyCode.A){
            leftPressed = true;
        }
        if(code == KeyCode.S){
            downPressed = true;
        }
        if(code == KeyCode.D){
            rightPressed = true;
        }

        if (code == KeyCode.P){
            savePressed = true;
        }


        if (code == KeyCode.ESCAPE){
            pausePressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        KeyCode code = e.getCode();
        if(code == KeyCode.W){
            upPressed = false;
        }
        if(code == KeyCode.A){
            leftPressed = false;
        }
        if(code == KeyCode.S){
            downPressed = false;
        }
        if(code == KeyCode.D){
            rightPressed = false;
        }

//        if (code == KeyCode.P){
//            savePressed = false;
//        }


        if (code == KeyCode.ESCAPE){
            pausePressed = false;
        }
    }

    public boolean anyPressed(){
        return upPressed || downPressed || leftPressed || rightPressed;
    }
}
