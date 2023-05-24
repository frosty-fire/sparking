package vn.baodh.sparking.merchant.core.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class VehicleModel {
  private String vehicleId;
  private String licensePlate;
}
