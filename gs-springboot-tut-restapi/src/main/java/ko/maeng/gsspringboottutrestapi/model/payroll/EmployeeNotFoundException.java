package ko.maeng.gsspringboottutrestapi.model.payroll;

public class EmployeeNotFoundException extends RuntimeException {
    EmployeeNotFoundException(Long id) {
        super("Could Not find Employee " + id);
    }
}
