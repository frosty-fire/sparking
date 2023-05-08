package vn.baodh.sparking.parking.core.infra.jdbc.master;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.parking.core.domain.model.DurationModel;
import vn.baodh.sparking.parking.core.domain.model.LocationModel;
import vn.baodh.sparking.parking.core.domain.model.VehicleDetailModel;
import vn.baodh.sparking.parking.core.domain.model.VehicleModel;
import vn.baodh.sparking.parking.core.domain.repository.ParkingRepository;

@Slf4j
@Repository
public class JdbcParkingRepository implements ParkingRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final String PARKING_TABLE = "parking";

  public JdbcParkingRepository(
      @Qualifier("masterNamedJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<VehicleModel> getVehiclesByPhone(String phone) throws Exception {
    List<VehicleModel> vehicleModels = new ArrayList<>();
    VehicleModel vehicleModel1 = new VehicleModel()
        .setVehicleId("1")
        .setLicensePlate("78-D1 427.24");
    VehicleModel vehicleModel2 = new VehicleModel()
        .setVehicleId("2")
        .setLicensePlate("78-D2 427.24");
    VehicleModel vehicleModel3 = new VehicleModel()
        .setVehicleId("3")
        .setLicensePlate("78-D3 427.24");
    vehicleModels.add(vehicleModel1);
    vehicleModels.add(vehicleModel2);
    vehicleModels.add(vehicleModel3);
    return vehicleModels;
  }

  @Override
  public List<VehicleDetailModel> getVehicleById(String vehicleId) throws Exception {
    List<VehicleDetailModel> vehicleModels = new ArrayList<>();
    VehicleDetailModel vehicleModel1 = new VehicleDetailModel()
        .setVehicleId("1")
        .setLicensePlate("78-D1 427.24")
        .setLocation(new LocationModel()
            .setLocationId("123")
            .setLocationName("GigaMall")
            .setAddress("Thủ Đức, Tp.Hồ Chí Minh"))
        .setEntryTime(String.valueOf(Timestamp.from(Instant.now())))
        .setDuration(new DurationModel()
            .setHours(23)
            .setMinutes(59)
            .setSeconds(50)
            .setMilliseconds(123))
        .setFee(100.000);
    vehicleModels.add(vehicleModel1);
    return vehicleModels;
  }
}
