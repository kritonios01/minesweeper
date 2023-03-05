package ntua.minesweeper.types;

import javafx.scene.control.Button;

class Block extends Button{
    private boolean flagged;

    public Block(){
        super();
        this.setPrefSize(50, 50);
        this.flagged = false;
    }

    public boolean getflag(){
        return flagged;
    }

    public void setflag(){
        this.flagged = true;
    }

    public void unsetflag(){
        this.flagged = false;
    }
}
