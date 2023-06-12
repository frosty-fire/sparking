package vn.baodh.sparking.parking.core.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.MonthCardEntity;

@Data
@Accessors(chain = true)
public class MonthCardModel {

  private String monthCardId;
  private String locationId;
  private String useUserId;
  private String sourceUserId;
  private String price;
  private String number;

  public MonthCardEntity toEntity() {
    return new MonthCardEntity()
        .setMonthCardId(this.getMonthCardId())
        .setLocationId(this.getLocationId())
        .setUseUserId(this.getUseUserId())
        .setSourceUserId(this.getSourceUserId())
        .setPrice(this.getPrice())
        .setNumber(this.getNumber());
  }
}
