package vn.baodh.sparking.parking.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.parking.core.domain.model.NotificationModel;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.NotificationEntity;

public interface NotificationRepository {

  boolean create(NotificationEntity entity) throws Exception;

  List<NotificationModel> getNotificationByPhone(String phone, String type) throws Exception;

  boolean delete(String id) throws Exception;
}
