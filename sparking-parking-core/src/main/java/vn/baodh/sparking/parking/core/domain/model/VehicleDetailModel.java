package vn.baodh.sparking.parking.core.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VehicleDetailModel {
  private String vehicleId;
  private String licensePlate;
  private String entryTime;
  private LocationModel location;
  private DurationModel duration;
}
