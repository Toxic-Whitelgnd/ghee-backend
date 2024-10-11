package com.tarun.ghee.services;

import com.tarun.ghee.entity.User.UserModel;
import com.tarun.ghee.repositary.auth.AuthRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
    @Autowired
    private AuthRepositary authrepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel oe = authrepo.findByusername(username);
        if(oe != null){
            var springUser = User.withUsername(oe.getUsername())
                    .password(oe.getPassword())
                    .roles(oe.getRoles().toString())
                    .build();

            return springUser;
        }
        return null;
    }
}
