package Controllers;

public class OverwritingException extends InvalidChoiceException {

    public OverwritingException(String type) {
        super(type);
    }

    @Override
    public String getMessage() {
        return "That " + type + " already exists.";
    }

    public void printErrorMessage() {
        System.out.println(getMessage());
    }

}
