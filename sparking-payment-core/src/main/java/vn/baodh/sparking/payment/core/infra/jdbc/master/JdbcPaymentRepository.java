package vn.baodh.sparking.payment.core.infra.jdbc.master;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.payment.core.domain.model.HistoryModel;
import vn.baodh.sparking.payment.core.domain.repository.PaymentRepository;

@Slf4j
@Repository
public class JdbcPaymentRepository implements PaymentRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final String USER_TABLE = "user";

  public JdbcPaymentRepository(
      @Qualifier("masterNamedJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<HistoryModel> getHistoriesByPhoneAndType(String phone, String type, String prevId)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String json = "{\"place\":\"Nhà xe Trường Đại học Bách Khoa Tp.HCM\",\"price\":\"-50.000đ\"}";
    Map<String, String> extraInfo = mapper.readValue(json, Map.class);
    List<HistoryModel> historyModels = new ArrayList<>();
    HistoryModel historyModel = new HistoryModel()
        .setHistoryId("1")
        .setType("parking")
        .setTitle("Mua vé tháng")
        .setExtraInfo(extraInfo)
        .setTime(String.valueOf(Timestamp.from(Instant.now())));
    if (prevId != null && Objects.equals(prevId, "")) {
      historyModels.add(historyModel);
    }
    return historyModels;
  }
}
