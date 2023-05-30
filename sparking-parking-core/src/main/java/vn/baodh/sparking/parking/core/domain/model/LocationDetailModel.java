package vn.baodh.sparking.parking.core.domain.model;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;
import vn.baodh.sparking.parking.core.domain.model.NotificationModel.ApiModel;

@Data
@Accessors(chain = true)
public class LocationDetailModel {

  private String locationId;
  private String locationName;
  private String imageUrl;
  private String address;
  private String mapUrl;
  private String timeStart;
  private String timeEnd;
  private String currentSlot;
  private String maxSlot;
  private String description;
  private List<PriceTicket> priceTicket;
  private String monthTicket;

  @Data
  @Accessors(chain = true)
  public static class PriceTicket {

    private String day;
    private String startEndIn;
    private String startPrice;
    private String afterPrice;
  }

  @Data
  @Accessors(chain = true)
  public static class ExtraInfo {

    private String description;
    private List<PriceTicket> priceTicket;
    private String monthTicket;
  }
}

