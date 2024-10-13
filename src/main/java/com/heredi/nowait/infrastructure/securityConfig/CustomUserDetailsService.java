package com.heredi.nowait.infrastructure.securityConfig;

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
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJPARepository userRepository;

    public CustomUserDetailsService(UserJPARepository userJPARepository) {
        this.userRepository = userJPARepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickName) throws UsernameNotFoundException {
        // Cargar el usuario desde la base de datos usando el repositorio
        UserEntity userEntity = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nickName: " + nickName));

        // Obtener el rol directamente del usuario
        String roleName = userEntity.getRoleEntity().getName();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleName)); // Solo un rol

        // Crear un UserDetails de Spring con los detalles del usuario
        return new User(
                userEntity.getNickName(),
                userEntity.getPassword(),
                authorities
        );
    }
}

