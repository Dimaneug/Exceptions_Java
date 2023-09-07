package exceptions;

public class NotEnoughNames extends Exception {
//    short count;
    public NotEnoughNames(String message) {
        super(message);
    }

//    @Override
//    public String getMessage() {
//        String message;
//        switch (count) {
//            1 -> message = "Вы не ввели "
//            default -> throw new IllegalStateException("Unexpected value: " + count);
//        }
//        return super.getMessage();
//    }
}
