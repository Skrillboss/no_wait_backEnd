package com.heredi.nowait.infrastructure.model.user.mapper;
import com.heredi.nowait.domain.user.model.Users;
import com.heredi.nowait.infrastructure.model.user.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "refreshToken", target = "refreshToken"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "nickName", target = "nickName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
            @Mapping(source = "roleEntity", target = "role"),
            @Mapping(source = "paymentInfoEntityList", target = "paymentInfoList"),
            @Mapping(source = "business", target = "business")
    })
    Users toUser(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity toUserEntity(Users user);
}
