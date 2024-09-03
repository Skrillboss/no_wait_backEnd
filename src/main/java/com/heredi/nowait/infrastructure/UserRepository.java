package com.heredi.nowait.infrastructure;

import com.heredi.nowait.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Puedes agregar métodos de consulta personalizados aquí si es necesario
}
