package ntua.minesweeper.types;

import javafx.scene.control.Button;

//A block is instantiated according to the grid's length and of course a block has
//a flag attribute which tells if it is flagged
class Block extends Button{
    private boolean flagged;

    public Block(int gridLength){
        super();
        if(gridLength == 9){
            this.setPrefSize(70, 70);
        }
        else{
            this.setPrefSize(40, 40);
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
