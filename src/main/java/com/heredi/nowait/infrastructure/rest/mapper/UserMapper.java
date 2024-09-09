package com.heredi.nowait.infrastructure.rest.mapper;
import com.heredi.nowait.domain.model.Users;
import com.heredi.nowait.infrastructure.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "nickName", target = "nickName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
            @Mapping(source = "paymentInfos", target = "paymentInfos"),
            @Mapping(source = "business", target = "business")
    })
    Users toUser(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity toUserEntity(Users user);
}
