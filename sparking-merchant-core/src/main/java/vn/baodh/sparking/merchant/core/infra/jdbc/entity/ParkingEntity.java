package vn.baodh.sparking.merchant.core.infra.jdbc.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import vn.baodh.sparking.merchant.core.domain.model.DurationModel;
import vn.baodh.sparking.merchant.core.domain.model.LocationModel;
import vn.baodh.sparking.merchant.core.domain.model.VehicleDetailModel;
import vn.baodh.sparking.merchant.core.domain.model.VehicleModel;

@Slf4j
@Data
@Accessors(chain = true)
public class ParkingEntity {

  @Id
  private String parkingId;
  private String userId;
  private String locationId;
  private String licensePlate;
  private String entryTime;
  private String exitTime;
  private String status;
  private String fee;
  private String createdAt;
  private String updatedAt;

  public VehicleModel toVehicleModel() {
    return new VehicleModel()
        .setVehicleId(this.getParkingId())
        .setLicensePlate(this.getLicensePlate());
  }

  public VehicleDetailModel toVehicleDetailModel() {
//    var diff = Timestamp.from(Instant.now()).getTime() - entry.getTime();
//    log.info(String.valueOf(Timestamp.from(Instant.ofEpochMilli(diff))));
    return new VehicleDetailModel()
        .setVehicleId(this.getParkingId())
        .setLicensePlate(this.getLicensePlate())
        .setLocation(new LocationModel()
            .setLocationId("123")
            .setLocationName("GigaMall")
            .setAddress("Thủ Đức, Tp.Hồ Chí Minh"))
        .setEntryTime(this.getEntryTime())
        .setDuration(new DurationModel()
            .setHours(23)
            .setMinutes(59)
            .setSeconds(50)
            .setMilliseconds(123))
        .setFee(100.000);
  }
}
