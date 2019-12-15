package ko.maeng.gsspringboottutrestapi.model.payroll;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
