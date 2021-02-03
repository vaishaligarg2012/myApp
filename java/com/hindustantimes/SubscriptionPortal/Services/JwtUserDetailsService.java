package com.hindustantimes.SubscriptionPortal.Services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
  

@Service("userDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if ("htprint".equals(username)) {
			return new User("htprint", "$2y$12$I99.rPoFvyiHlQ4bfi1FzONrWrWcLtpmWCdNcL8GVlesQUjhW1V6W",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("UserNot Found");
		}

	}

  

}



