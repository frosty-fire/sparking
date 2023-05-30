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
import vn.baodh.sparking.parking.core.domain.model.LocationDetailModel;
import vn.baodh.sparking.parking.core.domain.model.LocationModel;
import vn.baodh.sparking.parking.core.domain.repository.LocationRepository;
import vn.baodh.sparking.parking.core.domain.repository.MonthCardRepository;
import vn.baodh.sparking.parking.core.domain.repository.UserRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.LocationEntity;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.MonthCardEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcMonthCardRepository implements MonthCardRepository {

  @Qualifier("masterNamedJdbcTemplate")
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final UserRepository userRepository;
  private final String MONTH_CARD_TABLE = "month_card";

  @Override
  public boolean create(MonthCardEntity entity) throws Exception {
    var prep = """
        insert into %s (month_card_id, location_id, use_user_id, source_user_id, price, extra_info, created_at, updated_at)
        value (:month_card_id, :location_id, :use_user_id, :source_user_id, :price, :extra_info, now(3), now(3))
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, MONTH_CARD_TABLE);
    params.addValue("month_card_id", entity.getMonthCardId());
    params.addValue("location_id", entity.getLocationId());
    params.addValue("use_user_id", entity.getUseUserId());
    params.addValue("source_user_id", entity.getSourceUserId());
    params.addValue("price", entity.getPrice());
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
        set use_user_id = :use_user_id,
        where month_card_id = :month_card_id;
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, MONTH_CARD_TABLE);
    params.addValue("month_card_id", entity.getLocationId());
    params.addValue("use_user_id", entity.getUseUserId());

    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

}
