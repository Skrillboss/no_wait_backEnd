package com.heredi.nowait.application.model.role.dto.mapper;

import com.heredi.nowait.application.model.role.dto.RoleDTO;
import com.heredi.nowait.domain.role.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleDTO toRoleDTO(Role role){
        if(role == null){
            return null;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    public Role toRole(RoleDTO roleRequestDTO){
        if(roleRequestDTO == null){
            return null;
        }

        Role role = new Role();
        role.setName(roleRequestDTO.getName());

        return role;
    }
}
