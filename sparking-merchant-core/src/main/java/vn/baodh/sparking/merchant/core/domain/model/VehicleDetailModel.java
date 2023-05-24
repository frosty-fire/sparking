package vn.baodh.sparking.merchant.core.domain.model;

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
  private Double fee;
}
