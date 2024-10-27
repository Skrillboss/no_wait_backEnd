package com.heredi.nowait.application.model.role.dto.mapper;

import com.heredi.nowait.application.model.role.dto.RoleDTO;
import com.heredi.nowait.domain.role.model.Authority;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDTO toRoleDTO(Authority authority){
        if(authority == null){
            return null;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(authority.getRole().name());
        return roleDTO;
    }

    public Authority toRole(RoleDTO roleRequestDTO){
        if(roleRequestDTO == null){
            return null;
        }

        Authority authority = new Authority();
        authority.setRole(Authority.RoleUser.valueOf(roleRequestDTO.getName().toUpperCase()));

        return authority;
    }
}
