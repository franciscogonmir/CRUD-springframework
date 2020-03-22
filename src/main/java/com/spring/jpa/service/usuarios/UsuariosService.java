package com.spring.jpa.service.usuarios;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.jpa.dao.users.IUsuarioDao;
import com.spring.jpa.models.entity.rol.Rol;
import com.spring.jpa.models.entity.usuarios.Usuarios;

@Service
public class UsuariosService implements UserDetailsService {

	@Autowired
	private IUsuarioDao userDao;
	
	private Logger logger = LoggerFactory.getLogger(UsuariosService.class);
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuarios user = userDao.findByUsername(username);
		
		if(user == null) {
			logger.error("El usuario con login '"+username+"' no existe en el sistema");
			throw new UsernameNotFoundException("El usuario con login '"+username+"' no existe en el sistema");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (Rol rol : user.getRoles()) {
			logger.info("role: ".concat(rol.getAuthority()));
			authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}
		
		if(authorities.isEmpty()) {
			logger.error("El usuario con login '"+username+"' no tiene roles asignados");
			throw new UsernameNotFoundException("El usuario con login '"+username+"' no tiene roles asignados");
		}
		
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

}
