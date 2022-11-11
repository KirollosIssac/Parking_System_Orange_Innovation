package com.example.parking_system_orange_innovation.security;

import com.example.parking_system_orange_innovation.user.Client;
import com.example.parking_system_orange_innovation.user.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final ClientRepository clientRepository;

    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = clientRepository.findClientByUserName(username).orElseThrow(
                 () -> new UsernameNotFoundException("username not found!"));
        return new User(client.getUserName(), client.getPassword(), mapRolesToAuthorities("CLIENT"));
    }

    private List<GrantedAuthority> mapRolesToAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority(role));
    }

}
