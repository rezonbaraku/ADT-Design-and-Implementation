public class FriendshipAlreadyExistsException extends Exception {
    public FriendshipAlreadyExistsException(){
        super();
    }
    public FriendshipAlreadyExistsException(String message){
        super(message);
    }
}