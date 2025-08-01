package java_web.quanlithuctap.mapper;

import java_web.quanlithuctap.dto.UserResponse;
import java_web.quanlithuctap.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(User user);
}
