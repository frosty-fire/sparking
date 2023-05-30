package vn.baodh.sparking.parking.core.infra.jdbc.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import vn.baodh.sparking.parking.core.domain.model.LocationDetailModel;
import vn.baodh.sparking.parking.core.domain.model.LocationDetailModel.ExtraInfo;
import vn.baodh.sparking.parking.core.domain.model.LocationModel;

@Slf4j
@Data
@Accessors(chain = true)
public class MonthCardEntity {

  @Id
  private String monthCardId;
  private String locationId;
  private String useUserId;
  private String sourceUserId;
  private String price;
  private String extraInfo;
  private String createdAt;
  private String updatedAt;

//  public LocationModel toLocationModel() {
//    try {
//      return new LocationModel()
//          .setLocationId(this.getLocationId())
//          .setMapUrl(this.getMapUrl());
//    } catch (Exception e) {
//      log.error("json_mapper error, extraInfo", e);
//    }
//    return null;
//  }

}
