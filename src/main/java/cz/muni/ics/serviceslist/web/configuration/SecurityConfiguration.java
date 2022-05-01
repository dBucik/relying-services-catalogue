package cz.muni.ics.serviceslist.web.configuration;

import static cz.muni.ics.serviceslist.web.GuiConstants.PATH_HOME;
import static cz.muni.ics.serviceslist.web.GuiConstants.PATH_LOGIN_ERROR;

import cz.muni.ics.serviceslist.web.AuthSuccessHandler;
import cz.muni.ics.serviceslist.web.GuiConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_" + ADMIN);
    public static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_" + USER);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(GuiConstants.PATH_ADMIN + "/**").hasRole(ADMIN)
                .antMatchers(GuiConstants.PATH_LOGIN).authenticated()
                .antMatchers(PATH_HOME +"**").permitAll()
            .and()
            .oauth2Login(o -> {
                o.failureHandler(failureHandler());
                o.successHandler(successHandler());
            })
            .logout(l -> l.logoutSuccessUrl(PATH_HOME).permitAll());
    }
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
        return new AuthSuccessHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler(PATH_LOGIN_ERROR);
    }

}
