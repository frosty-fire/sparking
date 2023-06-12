package vn.baodh.sparking.parking.core.infra.jdbc.master;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.parking.core.domain.repository.MonthCardRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.MonthCardEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcMonthCardRepository implements MonthCardRepository {

  @Qualifier("masterNamedJdbcTemplate")
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final JdbcUserRepository userRepository;
  private final String MONTH_CARD_TABLE = "month_card";

  @Override
  public boolean create(MonthCardEntity entity) throws Exception {
    var prep = """
        insert into %s (month_card_id, location_id, use_user_id, source_user_id, price, number, extra_info, created_at, updated_at)
        value (:month_card_id, :location_id, :use_user_id, :source_user_id, :price, :number, :extra_info, now(3), now(3))
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, MONTH_CARD_TABLE);
    params.addValue("month_card_id", entity.getMonthCardId());
    params.addValue("location_id", entity.getLocationId());
    params.addValue("use_user_id", entity.getUseUserId());
    params.addValue("source_user_id", entity.getSourceUserId());
    params.addValue("price", entity.getPrice());
    params.addValue("number", entity.getNumber());
    params.addValue("extra_info", entity.getExtraInfo());

    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (DataIntegrityViolationException exception) {
      throw new DuplicateKeyException("duplicated month_card_id: " + exception);
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public boolean update(MonthCardEntity entity) throws Exception {
    var prep = """
        update %s
        set use_user_id = :use_user_id, number = number + :addition
        where use_user_id = :source_use_id and location_id = location_id
        and now(3) < created_at + interval number month;;
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, MONTH_CARD_TABLE);
    params.addValue("source_use_id", entity.getSourceUserId());
    params.addValue("location_id", entity.getLocationId());
    params.addValue("use_user_id", entity.getUseUserId());
    params.addValue("addition", entity.getNumber());

    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public List<MonthCardEntity> getMonthCardByPhone(String phone) throws Exception {
    var userId = userRepository.getUserIdByPhone(phone);
    var prep = "select * from %s where use_user_id = :use_user_id and now(3) < created_at + interval number month;";
    var sql = String.format(prep, MONTH_CARD_TABLE);
    var params = new MapSqlParameterSource();
    params.addValue("use_user_id", userId);
    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> new MonthCardEntity()
          .setMonthCardId(rs.getString("month_card_id"))
          .setLocationId(rs.getString("location_id"))
          .setUseUserId(rs.getString("use_user_id"))
          .setSourceUserId(rs.getString("source_user_id"))
          .setPrice(rs.getString("price"))
          .setNumber(rs.getString("number"))
          .setExtraInfo(rs.getString("extra_info"))
          .setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()))
          .setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime())));
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public List<MonthCardEntity> getMonthCardByUserIdAndLocation(String userId, String locationId) {
    var prep = "select * from %s where use_user_id = :use_user_id and location_id = :location_id and now(3) < created_at + interval number month;";
    var sql = String.format(prep, MONTH_CARD_TABLE);
    var params = new MapSqlParameterSource();
    params.addValue("use_user_id", userId);
    params.addValue("location_id", locationId);

    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> new MonthCardEntity()
          .setMonthCardId(rs.getString("month_card_id"))
          .setLocationId(rs.getString("location_id"))
          .setUseUserId(rs.getString("use_user_id"))
          .setSourceUserId(rs.getString("source_user_id"))
          .setPrice(rs.getString("price"))
          .setNumber(rs.getString("number"))
          .setExtraInfo(rs.getString("extra_info"))
          .setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()))
          .setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime())));
    } catch (Exception exception) {
      log.error(String.valueOf(new Exception("database exception: " + exception)));
    }
    return null;
  }

}
