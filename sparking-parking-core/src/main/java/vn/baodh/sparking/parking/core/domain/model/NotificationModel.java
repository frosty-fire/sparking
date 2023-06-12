package vn.baodh.sparking.parking.core.domain.model;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NotificationModel {

  private String notificationId;
  private String userId;
  private String type;
  private String title;
  private ExtraInfo extraInfo;
  private String createdAt;

  @Data
  @Accessors(chain = true)
  public static class ExtraInfo {

    private boolean isHaveApi;
    private List<ApiModel> apiList;
    private String description;
  }

  @Data
  @Accessors(chain = true)
  public static class ApiModel {

    private String api;
    private String title;
  }
}
