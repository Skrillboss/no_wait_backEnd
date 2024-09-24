package com.heredi.nowait.application.role.dto.mapper;

import com.heredi.nowait.application.role.dto.in.RoleRequestDTO;
import com.heredi.nowait.domain.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    private Role toRole(RoleRequestDTO roleRequestDTO){
        if(roleRequestDTO == null){
            return null;
        }

        Role role = new Role();
        role.setName(roleRequestDTO.getName());

        return role;
    }

    public List<Role> toRoleList(List<RoleRequestDTO> roleRequestDTOList){
        if(roleRequestDTOList == null){
            return null;
        }

        return roleRequestDTOList.stream()
                .map(this::toRole)
                .collect(Collectors.toList());
    }
}
