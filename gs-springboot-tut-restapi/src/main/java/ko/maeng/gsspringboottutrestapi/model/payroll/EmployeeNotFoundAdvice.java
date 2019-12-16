package ko.maeng.gsspringboottutrestapi.model.payroll;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeNotFoundAdvice {

    @ResponseBody   // JSON 응답 본문에 바로 에러를 전달
    @ExceptionHandler(EmployeeNotFoundException.class)  // 등록한 Exception이 발생하면 실행
    @ResponseStatus(HttpStatus.NOT_FOUND)   // HTTP 404 에러를 던짐.
    String employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return ex.getMessage();
    }
}
