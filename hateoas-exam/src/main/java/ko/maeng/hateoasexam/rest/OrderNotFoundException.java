package ko.maeng.hateoasexam.rest;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order " + id + " not found!");
    }
}
