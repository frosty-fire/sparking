package vn.baodh.sparking.um.authorization.infra.jdbc.master;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.um.authorization.domain.repository.FriendRepository;
import vn.baodh.sparking.um.authorization.infra.jdbc.entity.FriendEntity;
import vn.baodh.sparking.um.authorization.infra.jdbc.entity.UserEntity;

@Repository
public class JdbcFriendRepository implements FriendRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final String FRIEND_TABLE = "friend";

  public JdbcFriendRepository(
      @Qualifier("masterNamedJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public boolean create(FriendEntity entity) throws Exception {
    var prep = """
        insert into %s (friend_id, this_user_id, that_user_id, status)
        values (:friend_id, :this_user_id, :that_user_id, :status)
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, FRIEND_TABLE);
    params.addValue("friend_id", entity.getFriendId());
    params.addValue("this_user_id", entity.getThisUserId());
    params.addValue("that_user_id", entity.getThatUserId());
    params.addValue("status", entity.getStatus());
    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (DataIntegrityViolationException exception) {
      throw new DuplicateKeyException("duplicated friend_id: " + exception);
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public boolean update(FriendEntity entity) throws Exception {
    var prep = """
        update %s set status = :status where friend_id = :friend_id
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, FRIEND_TABLE);
    params.addValue("friend_id", entity.getFriendId());
    params.addValue("status", entity.getStatus());
    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public List<String> getFriends(String userId) throws Exception {
    var prep = """
        select that_user_id as user_id from %s where this_user_id = :user_id and status = 'accepted'
        union
        select this_user_id as user_id from %s where that_user_id = :user_id and status = 'accepted'
        """;
    var sql = String.format(prep, FRIEND_TABLE, FRIEND_TABLE);
    var params = new MapSqlParameterSource();
    params.addValue("user_id", userId);
    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> rs.getString("user_id"));
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }
}
