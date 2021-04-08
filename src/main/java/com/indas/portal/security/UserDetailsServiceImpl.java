package com.indas.portal.security;

import com.indas.portal.security.dto.UserDto;
import com.indas.portal.security.encoder.Md5;
import com.indas.portal.security.entities.User;
import com.indas.portal.security.repositories.UserRepository;
import com.indas.portal.service.Maper;
import com.indas.portal.service.dto.ChangeUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private Md5 md5;

    @Autowired
    private Maper maper;

    public boolean changePassword(ChangeUserData userData) {
        String login = authenticationFacade.getAuthentication().getName();
        User user = userRepository.findByLogin(login);
        if (user != null &&
                md5.hesh(userData.oldPassword).toUpperCase()
                        .equals(user.getPassword().toUpperCase())) {
            user.setPassword(md5.hesh(userData.newPassword).toUpperCase());
            userRepository.save(user);
            return true;
        } else {
            return false;
            // надо чет вернуть (Может ошибку?)
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

//        List<String> s = partRepository.findAllPartIds();

        User user = userRepository.findByLogin(username); //our own User model class
        if (user != null) {
//            String password = user.getPassword();

            Collection<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
                    .collect(Collectors.toList());
            //Let's populate user roles

            //Now let's create Spring Security User object
            org.springframework.security.core.userdetails.User securityUser = null;

            return new com.indas.portal.security.spring.override.User(
                    user.getFio(),
                    user.getLogin(),
                    user.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    authorities
            );
        } else {
            throw new UsernameNotFoundException("User " + username + " Not Found!!!");
        }
    }

    public UserDto save(UserDto userDto) {
        User user = maper.map(userDto);
        if (user.getPassword() != null && !user.getPassword().isEmpty()){
            user.setPassword(md5.hesh(user.getPassword()).toUpperCase());
        }
        return maper.map(userRepository.save(user));
    }

    public List<UserDto> getAll() {
        return maper.mapUsers(userRepository.findAll());
    }
}
