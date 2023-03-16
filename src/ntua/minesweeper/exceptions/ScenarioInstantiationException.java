package ntua.minesweeper.exceptions;

public class ScenarioInstantiationException extends RuntimeException{
    private String cause;

    public ScenarioInstantiationException(String cause) {
        super();
        this.cause = cause;
    }

    public String getError(){
        return this.cause;
    }
}
