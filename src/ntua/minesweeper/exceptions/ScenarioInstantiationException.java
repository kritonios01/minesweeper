package ntua.minesweeper.exceptions;

//when InvalidDescriptionException or InvalidDescriptionExcpetion is thrown,
//they are caught and this runtime excpetion is thrown so that it can be caught from the
//loadScenarioController class and the appropriate error message is shown
public class ScenarioInstantiationException extends RuntimeException{
    private final String cause;

    public ScenarioInstantiationException(String cause) {
        super();
        this.cause = cause;
    }

    public String getError(){
        return this.cause;
    }
}
