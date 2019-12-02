package ko.maeng.gsspringbootsecurityweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // "/", "/home"을 제외한 request는 보호된다는 의미
        // formLogin()할 페이지를 "/login"으로 설정
        http
           .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
           .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
           .logout()
                .permitAll();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        // withDefaultpasswordEncoder() 메서드는 사용되지 않는 메서드지만
        // 이 예제 안에서는 데모용으로 쓰는 메서드이기 때문에 실제 프로덕션 레벨에서는
        // PasswordEncoder를 @Bean으로 등록해서 패스워드를 인코딩해야한다.
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")           // login폼에서 사용할 아이디
                .password("password")       // login폼에서 사용할 패스워드
                .roles("USER")              // roles를 USER로 설정
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
