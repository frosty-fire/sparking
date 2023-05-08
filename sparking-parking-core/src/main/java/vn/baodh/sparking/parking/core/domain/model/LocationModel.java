package vn.baodh.sparking.parking.core.domain.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LocationModel {
  private String locationId;
  private String locationName;
  private String address;
}
