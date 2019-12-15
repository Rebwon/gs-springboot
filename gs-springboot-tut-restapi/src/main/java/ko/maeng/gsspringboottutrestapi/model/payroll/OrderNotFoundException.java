package ko.maeng.gsspringboottutrestapi.model.payroll;

public class OrderNotFoundException extends RuntimeException {
    OrderNotFoundException(Long id) {
        super("Could not find order " + id);
    }
}
