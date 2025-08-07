package com.EChowk.EChowk.Service;

import com.EChowk.EChowk.Entity.User;
import com.EChowk.EChowk.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user =  userRepo.findByEmail(email).orElseThrow(() -> {
           log.warn("Login Failed : No user found with Email : {}",email);
          return new UsernameNotFoundException("User Not Found");
       });
       return new UserDetailsImpl(user);
    }

}
