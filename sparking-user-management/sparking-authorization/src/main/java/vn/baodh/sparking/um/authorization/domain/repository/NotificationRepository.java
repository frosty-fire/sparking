package vn.baodh.sparking.um.authorization.domain.repository;

import java.util.List;
import vn.baodh.sparking.um.authorization.domain.model.NotificationModel;
import vn.baodh.sparking.um.authorization.infra.jdbc.entity.NotificationEntity;

public interface NotificationRepository {

  boolean create(NotificationEntity entity) throws Exception;

  List<NotificationModel> getNotificationByPhone(String phone, String type) throws Exception;
}
