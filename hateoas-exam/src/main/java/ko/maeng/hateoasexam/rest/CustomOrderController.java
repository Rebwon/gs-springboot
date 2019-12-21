package ko.maeng.hateoasexam.rest;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static ko.maeng.hateoasexam.rest.OrderStatus.valid;

@BasePathAwareController
public class CustomOrderController {
    private final OrderRepository repository;

    public CustomOrderController(OrderRepository repository){
        this.repository = repository;
    }

    @PostMapping("/orders/{id}/pay")
    ResponseEntity<?> pay(@PathVariable Long id) {
        Order order = this.repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if(valid(order.getOrderStatus(), OrderStatus.PAID_FOR)) {
            order.setOrderStatus(OrderStatus.PAID_FOR);
            return ResponseEntity.ok(repository.save(order));
        }

        return ResponseEntity.badRequest()
                .body("Transitioning from " + order.getOrderStatus() + " to " + OrderStatus.PAID_FOR + " is not valid.");
    }

    @PostMapping("/orders/{id}/cancel")
    ResponseEntity<?> cancel(@PathVariable Long id) {

        Order order = this.repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if (valid(order.getOrderStatus(), OrderStatus.CANCELLED)) {

            order.setOrderStatus(OrderStatus.CANCELLED);
            return ResponseEntity.ok(repository.save(order));
        }

        return ResponseEntity.badRequest()
                .body("Transitioning from " + order.getOrderStatus() + " to " + OrderStatus.CANCELLED + " is not valid.");
    }

    @PostMapping("/orders/{id}/fulfill")
    ResponseEntity<?> fulfill(@PathVariable Long id) {

        Order order = this.repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        if (valid(order.getOrderStatus(), OrderStatus.FULFILLED)) {

            order.setOrderStatus(OrderStatus.FULFILLED);
            return ResponseEntity.ok(repository.save(order));
        }

        return ResponseEntity.badRequest()
                .body("Transitioning from " + order.getOrderStatus() + " to " + OrderStatus.FULFILLED + " is not valid.");
    }
}
