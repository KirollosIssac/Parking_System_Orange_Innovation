package com.example.parking_system_orange_innovation.security;

import com.example.parking_system_orange_innovation.user.Admin;
import com.example.parking_system_orange_innovation.user.AdminRepository;
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

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(ClientRepository clientRepository, AdminRepository adminRepository) {
        this.clientRepository = clientRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findClientByUserName(username);
        Optional<Admin> admin = adminRepository.findAdminByUserName(username);
        if (client.isPresent())
            return new User(client.get().getUserName(), client.get().getPassword(), mapRolesToAuthorities(client.get().getRole()));
        else if (admin.isPresent()) {
            System.out.println("HI");
            return new User(admin.get().getUserName(), admin.get().getPassword(), mapRolesToAuthorities(admin.get().getRole()));
        }
        else
            throw new UsernameNotFoundException("username not found!");
    }

    private List<GrantedAuthority> mapRolesToAuthorities(String role) {
        return List.of(new SimpleGrantedAuthority(role));
    }

}
