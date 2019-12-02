package ko.maeng.gsspringbootsecurityweb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@DisplayName("로그인 성공")
	@Test
	public void loginWithValidUserThenAuthenticated() throws Exception{
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
				.user("user")
				.password("password");
		mockMvc.perform(login)
				.andExpect(authenticated().withUsername("user"));
	}

	@DisplayName("로그인 실패")
	@Test
	public void loginWithInvalidUserThenUnauthenticated() throws Exception{
		SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
				.user("invalid")
				.password("invalidpassword");
		mockMvc.perform(login)
				.andExpect(unauthenticated());
	}

	@DisplayName("Root 요청 응답 확인")
	@Test
	public void accessUnsecureResourceThenOk() throws Exception{
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());
	}

	@DisplayName("hello로 get 요청 시 로그인 폼으로 리다이렉트")
	@Test
	public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrlPattern("**/login"));
	}

	@DisplayName("MockUser 설정 후 hello로 get 요청 시 응답 확인")
	@Test
	@WithMockUser
	public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
		mockMvc.perform(get("/hello"))
				.andExpect(status().isOk());
	}

}
