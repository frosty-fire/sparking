package vn.baodh.sparking.payment.core.infra.jdbc.master;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.payment.core.domain.model.HistoryModel;
import vn.baodh.sparking.payment.core.domain.model.HistoryModel.ExtraInfo;
import vn.baodh.sparking.payment.core.domain.repository.PaymentRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcPaymentRepository implements PaymentRepository {

  @Qualifier("masterNamedJdbcTemplate")
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final String HISTORY_TABLE = "history";
  private final JdbcUserRepository userRepository;

  @Override
  public boolean create(HistoryModel entity) throws Exception {
    var prep = """
        insert into %s (history_id, user_id, type, title, extra_info, created_at, updated_at)
        value (:history_id, :user_id, :type, :title, :extra_info, now(3), now(3))
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, HISTORY_TABLE);
    params.addValue("history_id", entity.getHistoryId());
    params.addValue("user_id", entity.getUserId());
    params.addValue("type", entity.getType());
    params.addValue("title", entity.getTitle());
    params.addValue("extra_info", new ObjectMapper().writeValueAsString(entity.getExtraInfo()));
    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (DataIntegrityViolationException exception) {
      throw new DuplicateKeyException("duplicated history_id: " + exception);
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  @SneakyThrows
  public List<HistoryModel> getHistoriesByPhoneAndType(String phone, String type, String prevId) {
    var userId = userRepository.getUserIdByPhone(phone);
    var prep = "select * from %s where user_id = :user_id and type = :type";
    if (type == null || Objects.equals(type, "")) {
      prep = "select * from %s where user_id = :user_id";
    }
    var sql = String.format(prep, HISTORY_TABLE);
    var params = new MapSqlParameterSource();
    params.addValue("user_id", userId);
    params.addValue("type", type);
    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> {
        try {
          return new HistoryModel()
              .setHistoryId(rs.getString("history_id"))
              .setUserId(rs.getString("user_id"))
              .setType(rs.getString("type"))
              .setTitle(rs.getString("title"))
              .setExtraInfo(
                  new ObjectMapper().readValue(rs.getString("extra_info"), ExtraInfo.class))
              .setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()))
              .setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime()));
        } catch (JsonProcessingException ignored) {
        }
        return null;
      });
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }
}
