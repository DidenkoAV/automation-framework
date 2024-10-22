package core.exception.openai;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}
