package ko.maeng.hateoasexam;

import ko.maeng.hateoasexam.basic.EmployeeResourceAssembler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(EmployeeController.class)
//@Import({EmployeeResourceAssembler.class})
class EmployeeControllerTests {

	@Autowired private MockMvc mockMvc;

	@MockBean private EmployeeRepository repository;

	@Disabled
	@Test
	void getShouldFetchAHalDocument() throws Exception {
//		given(repository.findAll()).willReturn(
//				Arrays.asList(new Employee(1L, "Frodo", "Baggins", "ring bearer"),
//						new Employee(2L, "Bilbo", "Baggins", "burglar")));

		mockMvc.perform(get("/employees").accept(MediaTypes.HAL_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
				.andExpect(jsonPath("$._embedded.employees[0].id", is(1)))
				.andExpect(jsonPath("$._embedded.employees[0].firstName", is("Frodo")))
				.andExpect(jsonPath("$._embedded.employees[0].lastName", is("Baggins")))
				.andExpect(jsonPath("$._embedded.employees[0].role", is("ring bearer")))
				.andExpect(jsonPath("$._embedded.employees[0]._links.self.href", is("http://localhost/employees/1")))
				.andExpect(jsonPath("$._embedded.employees[0]._links.employees.href", is("http://localhost/employees")))
				.andExpect(jsonPath("$._embedded.employees[1].id", is(2)))
				.andExpect(jsonPath("$._embedded.employees[1].firstName", is("Bilbo")))
				.andExpect(jsonPath("$._embedded.employees[1].lastName", is("Baggins")))
				.andExpect(jsonPath("$._embedded.employees[1].role", is("burglar")))
				.andExpect(jsonPath("$._embedded.employees[1]._links.self.href", is("http://localhost/employees/2")))
				.andExpect(jsonPath("$._embedded.employees[1]._links.employees.href", is("http://localhost/employees")))
				.andExpect(jsonPath("$._links.self.href", is("http://localhost/employees")))
				.andReturn();
	}
}
