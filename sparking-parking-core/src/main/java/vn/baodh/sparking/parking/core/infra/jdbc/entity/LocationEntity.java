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
public class LocationEntity {

  @Id
  private String locationId;
  private String locationName;
  private String address;
  private String maxSlot;
  private String currentSlot;
  private String locationCode;
  private String locationPassword;
  private String phone;
  private String email;
  private String startTime;
  private String endTime;
  private String weekday;
  private String extraInfo;
  private String imageUrl;
  private String mapUrl;
  private String createdAt;
  private String updatedAt;

  public LocationModel toLocationModel() {
    try {
      return new LocationModel()
          .setLocationId(this.getLocationId())
          .setLocationName(this.getLocationName())
          .setImageUrl(this.getImageUrl())
          .setAddress(this.getAddress())
          .setMapUrl(this.getMapUrl());
    } catch (Exception e) {
      log.error("json_mapper error, extraInfo", e);
    }
    return null;
  }

  public LocationDetailModel toLocationDetailModel() {
    try {
      var extraInfo = new ObjectMapper().readValue(this.getExtraInfo(), ExtraInfo.class);
      return new LocationDetailModel()
          .setLocationId(this.getLocationId())
          .setLocationName(this.getLocationName())
          .setImageUrl(this.getImageUrl())
          .setAddress(this.getAddress())
          .setMapUrl(this.getMapUrl())
          .setTimeStart(this.getStartTime())
          .setTimeEnd(this.getEndTime())
          .setCurrentSlot(this.getCurrentSlot())
          .setMaxSlot(this.getMaxSlot())
          .setDescription(extraInfo.getDescription())
          .setPriceTicket(extraInfo.getPriceTicket())
          .setMonthTicket(extraInfo.getMonthTicket());
    } catch (Exception e) {
      log.error("json_mapper error, extraInfo", e);
    }
    return null;

  }
}
