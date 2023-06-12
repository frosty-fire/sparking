package vn.baodh.sparking.parking.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.parking.core.domain.model.UserModel;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.UserEntity;

public interface UserRepository {

  boolean create(UserModel entity) throws Exception;

  boolean update(UserModel entity);

  List<UserModel> getUserByPhone(String phone) throws Exception;

  List<UserEntity> getUserById(String userId, boolean isSecure) throws Exception;

  String getUserIdByPhone(String phone) throws Exception;
}
