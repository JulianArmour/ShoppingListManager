package armour.julian.shoppinglistmanager.config;

import armour.julian.shoppinglistmanager.security.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AppUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // H2 configs
        http
            .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/").authenticated()
                .and()
            .formLogin();
        http
            .csrf().disable();
        http
            .headers().frameOptions().disable();

    }

//    @Bean
//    public DaoAuthenticationProvider authProvider() {
//        val provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
