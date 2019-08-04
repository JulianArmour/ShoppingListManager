package armour.julian.shoppinglistmanager.controller.Exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super(username + " already taken.");
    }
}
