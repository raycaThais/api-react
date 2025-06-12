package org.serratec.h2.grupo2.security.userDetails;

import org.serratec.h2.grupo2.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ContaUserService implements UserDetailsService{
	
	@Autowired
	private ContaRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
	    ContaUserDetails contaLogada = new ContaUserDetails(repository.findByEmail(email)
	    	.orElseThrow(() -> new UsernameNotFoundException("E-mail não cadastrado.")));
	    if (contaLogada.isEnabled()) {return contaLogada;} 
	    else {throw new UsernameNotFoundException("E-mail não confirmado.");}
	}

}
