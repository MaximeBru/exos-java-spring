package com.backend_java.demo.security;


import com.backend_java.demo.data.RoleEntity;
import com.backend_java.demo.data.UtilisateurEntity;
import com.backend_java.demo.data.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UtilisateurDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return utilisateurRepository.findOneWithRolesByLoginIgnoreCase(login)
                .map(this::createSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur avec le login " + login + " n'a pas été trouvé."));
    }

    private User createSecurityUser(UtilisateurEntity utilisateurEntity) {
        List<SimpleGrantedAuthority> grantedRoles = utilisateurEntity
                .getRoles()
                .stream()
                .map(RoleEntity::getNom)
                .map(SimpleGrantedAuthority::new)
                .toList();
        return new User(utilisateurEntity.getLogin(), utilisateurEntity.getMotDePasse(), grantedRoles);
    }
}
