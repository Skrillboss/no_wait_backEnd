package com.heredi.nowait.application.role.dto.mapper;

import com.heredi.nowait.application.role.dto.RoleDTO;
import com.heredi.nowait.domain.model.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    private RoleDTO toRoleDTO(Role role){
        if(role == null){
            return null;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    private Role toRole(RoleDTO roleRequestDTO){
        if(roleRequestDTO == null){
            return null;
        }

        Role role = new Role();
        role.setName(roleRequestDTO.getName());

        return role;
    }

    public List<Role> toRoleList(List<RoleDTO> roleRequestDTOList){
        if(roleRequestDTOList == null){
            return null;
        }

        return roleRequestDTOList.stream()
                .map(this::toRole)
                .collect(Collectors.toList());
    }

    public List<RoleDTO> toRoleDTOList(List<Role> roleList){
        if(roleList == null){
            return null;
        }

        return roleList.stream()
                .map(this::toRoleDTO)
                .collect(Collectors.toList());
    }
}
