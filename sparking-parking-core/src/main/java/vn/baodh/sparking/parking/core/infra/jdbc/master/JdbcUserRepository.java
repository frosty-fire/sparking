package vn.baodh.sparking.parking.core.infra.jdbc.master;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.parking.core.domain.model.UserModel;
import vn.baodh.sparking.parking.core.domain.repository.UserRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.UserEntity;

@Repository
public class JdbcUserRepository implements UserRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final String USER_TABLE = "user";

  public JdbcUserRepository(
      @Qualifier("masterNamedJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public boolean create(UserModel userModel) throws Exception {
    var prep = """
        insert into %s (user_id, phone, pin, full_name, gender, birthday, email, cic, image_url, app_config, created_at, updated_at)
        values (:user_id, :phone, :pin, :full_name, :gender, :birthday, :email, :cic, :image_url, :app_config, now(3), now(3))
        """;
    var params = new MapSqlParameterSource();
    var entity = UserEntity.from(userModel);
    var sql = String.format(prep, USER_TABLE);
    params.addValue("user_id", entity.getUserId());
    params.addValue("phone", entity.getPhone());
    params.addValue("pin", entity.getPin());
    params.addValue("full_name", entity.getFullName());
    params.addValue("gender", entity.getGender());
    params.addValue("birthday", entity.getBirthday());
    params.addValue("email", entity.getEmail());
    params.addValue("cic", entity.getCic());
    params.addValue("image_url", entity.getImageUrl());
    params.addValue("app_config", entity.getAppConfig());
    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (DataIntegrityViolationException exception) {
      throw new DuplicateKeyException("duplicated user_id: " + exception);
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public boolean update(UserModel entity) {
    return false;
  }

  @Override
  public List<UserModel> getUserByPhone(String phone) throws Exception {
    var prep = "select * from %s where phone = :phone";
    var sql = String.format(prep, USER_TABLE);
    var params = new MapSqlParameterSource();
    params.addValue("phone", phone);
    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> {
        var entity = new UserEntity()
            .setUserId(rs.getString("user_id"))
            .setPhone(rs.getString("phone"))
            .setPin(rs.getString("pin"))
            .setFullName(rs.getString("full_name"))
            .setGender(rs.getString("gender"))
            .setBirthday(rs.getString("birthday"))
            .setEmail(rs.getString("email"))
            .setCic(rs.getString("cic"))
            .setImageUrl(rs.getString(("image_url")))
            .setAppConfig((rs.getString("app_config")))
            .setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()))
            .setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime()));
        return entity.toUserModel();
      });
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }
}
