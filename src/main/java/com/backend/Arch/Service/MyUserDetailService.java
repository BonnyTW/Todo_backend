package com.backend.Arch.Service;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.Arch.Controller.UserPrincipal;
import com.backend.Arch.Model.Users;
import com.backend.Arch.Reppo.UserReppo;

@Service
public class MyUserDetailService implements UserDetailsService{
	
	@Autowired
	UserReppo userReppo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userReppo.findByUsername(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("user not Found!!!");
		}
		
		return new UserPrincipal(user);
		
	}

	

}
