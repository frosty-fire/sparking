package vn.baodh.sparking.parking.core.infra.jdbc.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.sql.In;
import vn.baodh.sparking.parking.core.domain.model.DurationModel;
import vn.baodh.sparking.parking.core.domain.model.LocationModel;
import vn.baodh.sparking.parking.core.domain.model.VehicleDetailModel;
import vn.baodh.sparking.parking.core.domain.model.VehicleModel;

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

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime dateTime = LocalDateTime.parse(this.getEntryTime(), formatter);
    long diff = Timestamp.from(Instant.now()).getTime() - Timestamp.valueOf(dateTime).getTime();
    long totalSecs = diff / 1000;
    int hours = (int) (totalSecs / 3600);
    int minutes = (int) ((totalSecs % 3600) / 60);
    int seconds = (int) (totalSecs % 60);
    int milliseconds = (int) (diff % 1000);

    return new VehicleDetailModel()
        .setVehicleId(this.getParkingId())
        .setLicensePlate(this.getLicensePlate())
        .setLocation(new LocationModel()
            .setLocationId("123")
            .setLocationName("GigaMall")
            .setAddress("Thủ Đức, Tp.Hồ Chí Minh"))
        .setEntryTime(this.getEntryTime())
        .setDuration(new DurationModel()
            .setHours(hours)
            .setMinutes(minutes)
            .setSeconds(seconds)
            .setMilliseconds(milliseconds))
        .setFee((hours + Math.ceil((minutes + Math.ceil((double) seconds / 60)) / 60))*4);
  }
}
