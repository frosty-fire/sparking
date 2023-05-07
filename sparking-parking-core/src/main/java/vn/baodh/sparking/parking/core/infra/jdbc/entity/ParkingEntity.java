package vn.baodh.sparking.parking.core.infra.jdbc.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

@Data
@Accessors(chain = true)
public class ParkingEntity {

  @Id
  private String parkingId;
  private String userId;
  private String locationId;
  private String locationName;
  private String licensePlate;
  private String entryTime;
  private String exitTime;
  private String status;
  private String fee;
  private String createdAt;
  private String updatedAt;
}
