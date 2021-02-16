package br.com.empresa.mvc.mudi;

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
		
		http
			.authorizeRequests()
				.anyRequest().authenticated()
			.and()
//			.httpBasic();
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll() //todo mundo é permitido de acessar a pagina de login
			);	
		
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("gabriel")
				.password("gabriel")
				.roles("ADM")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}

/* 
 * Toda a configuração do Spring Security ficou centralizada nesta única classe 
 * e alguns recursos foram usados para que esta configuração funcionasse. 
 * 
 * A primeira é as suas dependências: nada compilaria se não tivéssemos adicionados 
 * a dependência do módulo de segurança no pom.xml: spring-boot-starter-security.
 * 
 * Com isso, nos foi disponibilizado todas as classes necessárias para configurar 
 * o módulo de segurança: utilizamos um adapter (WebSecurityConfigurerAdapter), 
 * que vem com várias configurações default. 
 * Apenas duas destas configurações nós precisamos reimplementar: configure(HttpSecurity) 
 * e configure(AuthenticationManagerBuilder auth). 
 * 
 * Utilizamos uma anotação (@EnableWebSecurity) que disponibiliza todas as dependências 
 * do Spring Security que precisamos para configurá-lo conforme a necessidade do nosso projeto; 
 * além disso, ganhamos alguns builders, que nos permitiu criar usuários e configurar a autorização.
 * 
 * */
