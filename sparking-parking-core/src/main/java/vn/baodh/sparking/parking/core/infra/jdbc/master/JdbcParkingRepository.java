package vn.baodh.sparking.parking.core.infra.jdbc.master;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.parking.core.domain.model.ParkingModel;
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
  public ParkingModel create(ParkingModel entity) throws Exception {
    return null;
  }

  @Override
  public boolean update(ParkingModel entity) {
    return false;
  }

  @Override
  public List<ParkingModel> getParkingById(String id) throws Exception {
    return null;
  }

  @Override
  public List<VehicleModel> getVehiclesByPhone(String phone) throws Exception {
    log.info(phone);
    List<VehicleModel> vehicleModels = new ArrayList<>();
    VehicleModel vehicleModel1 = new VehicleModel()
        .setVehicleId("1")
        .setLicensePlate("78-D1 427.24");
    VehicleModel vehicleModel2 = new VehicleModel()
        .setVehicleId("2")
        .setLicensePlate("78-D2 427.24");
    vehicleModels.add(vehicleModel1);
    vehicleModels.add(vehicleModel2);
    return vehicleModels;
  }
}
