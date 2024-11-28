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
            @Mapping(source = "refreshUUID", target = "refreshUUID"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "nickName", target = "nickName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
            @Mapping(source = "authorityEntity", target = "authority"),
            @Mapping(source = "paymentInfoEntityList", target = "paymentInfoList"),
            @Mapping(source = "business", target = "business")
    })
    Users toUser(UserEntity userEntity);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "refreshUUID", target = "refreshUUID"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "nickName", target = "nickName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "phoneNumber", target = "phoneNumber"),
            @Mapping(source = "authority", target = "authorityEntity"),
            @Mapping(source = "business", target = "business")
    })
    UserEntity toUserEntity(Users user);
}
