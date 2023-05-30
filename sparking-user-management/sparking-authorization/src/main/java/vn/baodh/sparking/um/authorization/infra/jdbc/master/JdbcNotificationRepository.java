package vn.baodh.sparking.um.authorization.infra.jdbc.master;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.um.authorization.domain.model.NotificationModel;
import vn.baodh.sparking.um.authorization.domain.repository.NotificationRepository;
import vn.baodh.sparking.um.authorization.infra.jdbc.entity.NotificationEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcNotificationRepository implements NotificationRepository {

  @Qualifier("masterNamedJdbcTemplate")
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final JdbcUserRepository userRepository;
  private final String NOTIFICATION_TABLE = "notification";

  @Override
  public boolean create(NotificationEntity entity) throws Exception {
    var prep = """
        insert into %s (notification_id, user_id, type, title, subtype, extra_info, created_at, updated_at)
        value (:notification_id, :user_id, :type, :title, :subtype, :extra_info, now(3), now(3))
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, NOTIFICATION_TABLE);
    params.addValue("notification_id", entity.getNotificationId());
    params.addValue("user_id", entity.getUserId());
    params.addValue("type", entity.getType());
    params.addValue("title", entity.getTitle());
    params.addValue("subtype", entity.getSubType());
    params.addValue("extra_info", entity.getExtraInfo());
    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (DataIntegrityViolationException exception) {
      throw new DuplicateKeyException("duplicated notification_id: " + exception);
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public List<NotificationModel> getNotificationByPhone(String phone, String type)
      throws Exception {
    var userId = userRepository.getUserIdByPhone(phone);
    log.info("{}.{}", userId, type);
    var prep = "";
    if (Objects.equals(type, "ALL")) {
      prep = "select * from %s where user_id = :user_id";
    } else {
      prep = "select * from %s where user_id = :user_id and type = :type";
    }
    var sql = String.format(prep, NOTIFICATION_TABLE);
    var params = new MapSqlParameterSource();
    params.addValue("user_id", userId);
    params.addValue("type", type);
    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> {
        var entity = new NotificationEntity()
            .setNotificationId(rs.getString("notification_id"))
            .setUserId(rs.getString("user_id"))
            .setType(rs.getString("type"))
            .setTitle(rs.getString("title"))
            .setSubType(rs.getString("subtype"))
            .setExtraInfo(rs.getString("extra_info"))
            .setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()))
            .setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime()));
        return entity.toNotificationModel();
      });
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public boolean delete(String id) throws Exception {
    var prep = """
        delete from %s where notification_id = :notification_id
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, NOTIFICATION_TABLE);
    params.addValue("notification_id", id);
    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }
}
