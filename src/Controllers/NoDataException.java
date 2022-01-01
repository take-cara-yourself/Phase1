package Controllers;

public class NoDataException extends InvalidChoiceException {

    public NoDataException(String type) {
        super(type);
    }

    @Override
    public String getMessage() {
        return "No " + type + "s exist yet!";
    }

    public void printErrorMessage() {
        System.out.println(getMessage());
    }
}
