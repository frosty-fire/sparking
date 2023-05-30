package vn.baodh.sparking.um.authorization.domain.repository;

import java.util.List;
import vn.baodh.sparking.um.authorization.domain.model.FriendModel;
import vn.baodh.sparking.um.authorization.domain.model.UserModel;

public interface UserRepository {

  boolean create(UserModel entity) throws Exception;

  boolean update(UserModel entity) throws Exception;

  List<UserModel> getUserByPhone(String phone, boolean isSecure) throws Exception;

  List<FriendModel> getFriendById(String userId, boolean isSecure) throws Exception;

  List<FriendModel> getUnknownLikePhone(String phone, boolean isSecure) throws Exception;

  String getUserIdByPhone(String phone) throws Exception;
}
