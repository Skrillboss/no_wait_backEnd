package com.heredi.nowait.application.role.dto.mapper;

import com.heredi.nowait.application.role.dto.RoleDTO;
import com.heredi.nowait.domain.role.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
