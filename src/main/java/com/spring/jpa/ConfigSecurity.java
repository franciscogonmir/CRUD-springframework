package com.spring.jpa;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.spring.jpa.auth.handler.LoginSuccesHandler;
import com.spring.jpa.service.usuarios.UsuariosService;
@EnableGlobalMethodSecurity(securedEnabled=true)//habilitar la configuracion para dar seguridad con anotaciones
@Configuration
public class ConfigSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccesHandler successHandler;
	
	/*@Autowired
	private DataSource dataSource;*/
	
	@Autowired
	private UsuariosService userService;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	//sobreescibimos el metodo configure para configurar los accesos de forma correcta
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//configuramos los accesos a las rutas ya sea por roles o permitiendole a todos
		http.authorizeRequests().antMatchers("/","/css/**","/js/**","/images/**","/listar**","/locale","/api/clientes/**").permitAll()
		/*.antMatchers("/ver/**").hasAnyRole("USER")
		.antMatchers("/upload/**").hasAnyRole("USER")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")*/
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.successHandler(successHandler)//definimos nuestro manejado en caso de exito
			.loginPage("/login").permitAll()//definimos la pagina de login y permitimos a todos
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");//Añadimos la ruta a nuestra pagina de error
		;
		
		}

	//Creamos un bean en el contecto de spring .DEuelve un objeto paswword codificada en Bcrypt
	/*@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
		
		/* autenticacion jpa */
		build.userDetailsService(userService).passwordEncoder(passwordEncoder);
		
		/*Autenticacion con jdbc*/
		/*build.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("SELECT USERNAME,PASSWORD,ENABLED FROM USERS WHERE USERNAME = ?")
		.authoritiesByUsernameQuery("SELECT A.USER_ID,A.AUTHORITY FROM AUTHORITIES A INNER JOIN USERS U ON A.USER_ID = U.ID WHERE U.USERNAME = ?");*/
		
		
		
		
		/*Authenticacion en memoria*/
		//llama a la fucion de arriba para obtener el password codificado con el algritmo bcrypt
		/*PasswordEncoder encoder = this.passwordEncoder;
		//construlle el constructor de usuarios para spring security
		UserBuilder users = User.builder().passwordEncoder((password) -> encoder.encode(password));
		//crea los usuarios para la autenticacion en memoria
		build.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN","USER"))
		.withUser(users.username("fran").password("12345").roles("USER"));*/
	}
}
