package vn.baodh.sparking.merchant.core.infra.jdbc.master;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.merchant.core.domain.model.DurationModel;
import vn.baodh.sparking.merchant.core.domain.model.LocationModel;
import vn.baodh.sparking.merchant.core.domain.model.UserModel;
import vn.baodh.sparking.merchant.core.domain.model.VehicleDetailModel;
import vn.baodh.sparking.merchant.core.domain.model.VehicleModel;
import vn.baodh.sparking.merchant.core.domain.repository.ParkingRepository;
import vn.baodh.sparking.merchant.core.domain.repository.UserRepository;
import vn.baodh.sparking.merchant.core.infra.jdbc.entity.ParkingEntity;
import vn.baodh.sparking.merchant.core.infra.jdbc.entity.UserEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcParkingRepository implements ParkingRepository {

  @Qualifier("masterNamedJdbcTemplate")
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final UserRepository userRepository;
  private final String PARKING_TABLE = "parking";

  @Override
  public boolean create(ParkingEntity entity) throws Exception {
    var prep = """
        insert into %s (parking_id, user_id, location_id, license_plate, entry_time, exit_time, status, fee, created_at, updated_at)
        values (:parking_id, :user_id, :location_id, :license_plate, now(3), now(3), 'entry', 4, now(3), now(3));
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, PARKING_TABLE);
    params.addValue("parking_id", entity.getParkingId());
    params.addValue("user_id", entity.getUserId());
    params.addValue("location_id", entity.getLocationId());
    params.addValue("license_plate", entity.getLicensePlate());
    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (DataIntegrityViolationException exception) {
      throw new DuplicateKeyException("duplicated parking_id: " + exception);
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public List<VehicleModel> getVehiclesByPhone(String phone) throws Exception {
    var userId = userRepository.getUserIdByPhone(phone);
    log.info("user: {}", userId);
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
//    VehicleModel vehicleModel1 = new VehicleModel()
//        .setVehicleId("1")
//        .setLicensePlate("78-D1 427.24");
//    VehicleModel vehicleModel2 = new VehicleModel()
//        .setVehicleId("2")
//        .setLicensePlate("78-D2 427.24");
//    VehicleModel vehicleModel3 = new VehicleModel()
//        .setVehicleId("3")
//        .setLicensePlate("78-D3 427.24");
//    vehicleModels.add(vehicleModel1);
//    vehicleModels.add(vehicleModel2);
//    vehicleModels.add(vehicleModel3);
//    return vehicleModels;
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
//    List<VehicleDetailModel> vehicleModels = new ArrayList<>();
//    VehicleDetailModel vehicleModel1 = new VehicleDetailModel()
//        .setVehicleId("1")
//        .setLicensePlate("78-D1 427.24")
//        .setLocation(new LocationModel()
//            .setLocationId("123")
//            .setLocationName("GigaMall")
//            .setAddress("Thủ Đức, Tp.Hồ Chí Minh"))
//        .setEntryTime(String.valueOf(Timestamp.from(Instant.now())))
//        .setDuration(new DurationModel()
//            .setHours(23)
//            .setMinutes(59)
//            .setSeconds(50)
//            .setMilliseconds(123))
//        .setFee(100.000);
//    vehicleModels.add(vehicleModel1);
//    return vehicleModels;
  }
}
