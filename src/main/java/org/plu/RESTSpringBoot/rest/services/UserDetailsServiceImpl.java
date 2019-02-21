package org.plu.RESTSpringBoot.rest.services;

import org.plu.RESTSpringBoot.entities.Korisnik;
import org.plu.RESTSpringBoot.repository.ApplicationUserRepository;
import org.plu.RESTSpringBoot.entities.ApplicationUser;
import org.plu.RESTSpringBoot.repository.KorisnikRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private KorisnikRepository applicationUserRepository;

    public UserDetailsServiceImpl(KorisnikRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Korisnik applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}