package ntua.minesweeper.types;

import javafx.scene.control.Button;

class Block extends Button{
    private boolean flagged;
    private boolean supermine;

    public Block(int gridLength){
        super();
        if(gridLength == 9){
            this.setPrefSize(70, 70);
        }
        else{
            this.setPrefSize(40, 40);
        }
        this.flagged = false;

        this.supermine = false;
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

    public void setSupermine() {
        this.supermine = true;
    }
}
