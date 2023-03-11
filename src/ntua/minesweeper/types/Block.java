package ntua.minesweeper.types;

import javafx.scene.control.Button;

class Block extends Button{
    private boolean flagged;

    public Block(int gridLength){
        super();
        if(gridLength == 9){
            this.setPrefSize(60, 60);
        }
        else{
            this.setPrefSize(50, 50);
        }
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
