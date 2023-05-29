package vn.baodh.sparking.parking.core.infra.jdbc.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import vn.baodh.sparking.parking.core.domain.model.NotificationModel;
import vn.baodh.sparking.parking.core.domain.model.NotificationModel.ExtraInfo;

@Slf4j
@Data
@Accessors(chain = true)
public class NotificationEntity {

  @Id
  private String notificationId;
  private String userId;
  private String type;
  private String title;
  private String subType;
  private String extraInfo;
  private String createdAt;
  private String updatedAt;

  public NotificationModel toNotificationModel() {
    try {
      return new NotificationModel()
          .setNotificationId(this.getNotificationId())
          .setUserId(this.getUserId())
          .setType(this.getType())
          .setTitle(this.getTitle())
          .setExtraInfo(new ObjectMapper().readValue(this.getExtraInfo(), ExtraInfo.class))
          .setCreatedAt(this.getCreatedAt());
    } catch (Exception e) {
      log.error("json_mapper error, extraInfo", e);
    }
    return null;
  }

}
