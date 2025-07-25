package org.serratec.h2.grupo2.security.autentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/*ONDE É FEITO A AUTENTICAÇÃO DO USUARIO AO SE LOGAR*/
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	
    @Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    String username = authentication.getName();
	    String password = authentication.getCredentials().toString();

	    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	    if (passwordEncoder.matches(password, userDetails.getPassword())) {
	        return new UsernamePasswordAuthenticationToken (
	            userDetails,
	            userDetails.getPassword(),
	            userDetails.getAuthorities());
	    } else { throw new BadCredentialsException("Senha inválida");}
	}
    
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
