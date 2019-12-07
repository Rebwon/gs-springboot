package ko.maeng.gsspringbootvalidforminput;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
class ValidInputApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("Form값에 이름이 없는 경우")
	@Test
	void checkPersonInfoWhenNameMissingNameThenFailure() throws Exception {
		MockHttpServletRequestBuilder createPerson = post("/")
						.param("age", "20");
		mockMvc.perform(createPerson)
				.andExpect(model().hasErrors());
	}

	@DisplayName("Form값에 이름의 길이가 짧은 경우")
	@Test
	public void checkPersonInfoWhenNameTooShortThenFailure() throws Exception {
		MockHttpServletRequestBuilder createPerson = post("/")
				.param("name", "R")
				.param("age", "20");

		mockMvc.perform(createPerson)
				.andExpect(model().hasErrors());
	}

	@DisplayName("Form값에 나이가 없는 경우")
	@Test
	public void checkPersonInfoWhenAgeMissingThenFailure() throws Exception {
		MockHttpServletRequestBuilder createPerson = post("/")
				.param("name", "Rob");

		mockMvc.perform(createPerson)
				.andExpect(model().hasErrors());
	}

	@DisplayName("Form값에 나이가 너무 어린 경우")
	@Test
	public void checkPersonInfoWhenAgeTooYoungThenFailure() throws Exception {
		MockHttpServletRequestBuilder createPerson = post("/")
				.param("age", "1")
				.param("name", "Rob");

		mockMvc.perform(createPerson)
				.andExpect(model().hasErrors());
	}

	@DisplayName("Form값에 맞는 이름과 나이가 있는 경우")
	@Test
	public void checkPersonInfoWhenValidRequestThenSuccess() throws Exception {
		MockHttpServletRequestBuilder createPerson = post("/")
				.param("name", "Rob")
				.param("age", "20");

		mockMvc.perform(createPerson)
				.andExpect(model().hasNoErrors());
	}
}
