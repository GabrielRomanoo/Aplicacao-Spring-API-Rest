package br.com.empresa.mvc.mudi;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.authorizeRequests()
			.antMatchers("/home/**") //pode entrar em /home/qualquercoisa sem precisar de autorizacao
				.permitAll()
			.anyRequest()         // qualquer usuario
				.authenticated()  //precisa estar autenticado	
		.and()
//		.httpBasic();
		.formLogin(form -> form
			.loginPage("/login") //ao fazer a requisição para /login
			.defaultSuccessUrl("/usuario/pedido", true) //requisicao que terá depois do login
			.permitAll() //todo mundo é permitido de acessar a pagina de login
		)
		.logout(logout -> {
			logout.logoutUrl("/logout")
				.logoutSuccessUrl("/home"); //ao fazer logout, irá ser redirecionado para /home
			
		})
		.csrf().disable(); //desabilitando uma configuração de segurança para os pedidos serem inseridos no banco
		
	}
	
	/* Autenticacao com o usuario em banco: */ 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); //criando o encoder
		
//		UserDetails user =
//				 User.builder()
//					.username("maria")
//					.password(encoder.encode("maria"))
//					.roles("ADM")
//					.build();
		
		auth.jdbcAuthentication()
			.dataSource(dataSource) //onde consegue conexoes com o banco
			.passwordEncoder(encoder);
//			.withUser(user);
	}
	
	
	/* Autenticacao com o usuario em memoria: 
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
	} */
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
