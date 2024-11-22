package com.heredi.nowait.infrastructure.config.security;

import com.heredi.nowait.infrastructure.model.user.jpa.UserJPARepository;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJPARepository userRepository;

    public CustomUserDetailsService(UserJPARepository userJPARepository) {
        this.userRepository = userJPARepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nickName: " + nickName));

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + userEntity.getAuthorityEntity().getRole().name()),
                new SimpleGrantedAuthority("STATUS_" + userEntity.getAuthorityEntity().getStatus().name())
        );

        return new User(
                userEntity.getNickName(),
                userEntity.getPassword(),
                authorities
        );
    }
}

