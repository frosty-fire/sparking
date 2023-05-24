package vn.baodh.sparking.parking.core.infra.jdbc.master;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.parking.core.domain.model.DurationModel;
import vn.baodh.sparking.parking.core.domain.model.LocationModel;
import vn.baodh.sparking.parking.core.domain.model.VehicleDetailModel;
import vn.baodh.sparking.parking.core.domain.model.VehicleModel;
import vn.baodh.sparking.parking.core.domain.repository.ParkingRepository;
import vn.baodh.sparking.parking.core.domain.repository.UserRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.ParkingEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcParkingRepository implements ParkingRepository {

  @Qualifier("masterNamedJdbcTemplate")
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final UserRepository userRepository;
  private final String PARKING_TABLE = "parking";

  @Override
  public List<VehicleModel> getVehiclesByPhone(String phone) throws Exception {
    var userId = userRepository.getUserIdByPhone(phone);
    var prep = "select * from %s where user_id = :user_id";
    var sql = String.format(prep, PARKING_TABLE);
    var params = new MapSqlParameterSource();
    params.addValue("user_id", userId);
    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> {
        var entity = new ParkingEntity()
            .setParkingId(rs.getString("parking_id"))
            .setUserId(rs.getString("user_id"))
            .setLocationId(rs.getString("location_id"))
            .setLicensePlate(rs.getString("license_plate"))
            .setEntryTime(rs.getTimestamp("entry_time") != null ? String.valueOf(
                rs.getTimestamp("entry_time").toLocalDateTime()) : null)
            .setExitTime(rs.getTimestamp("exit_time") != null ? String.valueOf(
                rs.getTimestamp("exit_time").toLocalDateTime()) : null)
            .setStatus(rs.getString("status"))
            .setFee(rs.getBigDecimal("fee") != null ?
                rs.getBigDecimal("fee").toString() : "0.000")
            .setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()))
            .setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime()));
        return entity.toVehicleModel();
      });
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public List<VehicleDetailModel> getVehicleById(String vehicleId) throws Exception {
    var prep = "select * from %s where parking_id = :parking_id";
    var sql = String.format(prep, PARKING_TABLE);
    var params = new MapSqlParameterSource();
    params.addValue("parking_id", vehicleId);
    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> {
        var entity = new ParkingEntity()
            .setParkingId(rs.getString("parking_id"))
            .setUserId(rs.getString("user_id"))
            .setLocationId(rs.getString("location_id"))
            .setLicensePlate(rs.getString("license_plate"))
            .setEntryTime(rs.getTimestamp("entry_time") != null ? String.valueOf(
                rs.getTimestamp("entry_time").toLocalDateTime()) : null)
            .setExitTime(rs.getTimestamp("exit_time") != null ? String.valueOf(
                rs.getTimestamp("exit_time").toLocalDateTime()) : null)
            .setStatus(rs.getString("status"))
            .setFee(rs.getBigDecimal("fee") != null ?
                rs.getBigDecimal("fee").toString() : "0.000")
            .setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()))
            .setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime()));
        return entity.toVehicleDetailModel();
      });
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }
}
