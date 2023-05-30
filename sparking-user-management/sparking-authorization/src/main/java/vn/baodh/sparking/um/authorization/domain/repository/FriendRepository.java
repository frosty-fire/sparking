package vn.baodh.sparking.um.authorization.domain.repository;

import java.util.List;
import vn.baodh.sparking.um.authorization.infra.jdbc.entity.FriendEntity;

public interface FriendRepository {

  boolean create(FriendEntity entity) throws Exception;

  boolean update(FriendEntity entity) throws Exception;

  List<String> getFriends(String userId) throws Exception;
}
