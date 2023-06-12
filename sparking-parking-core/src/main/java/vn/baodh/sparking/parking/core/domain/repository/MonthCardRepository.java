package vn.baodh.sparking.parking.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.MonthCardEntity;

public interface MonthCardRepository {

  boolean create(MonthCardEntity locationEntity) throws Exception;

  boolean update(MonthCardEntity locationEntity) throws Exception;

  List<MonthCardEntity> getMonthCardByPhone(String phone) throws Exception;

  List<MonthCardEntity> getMonthCardByUserIdAndLocation(String userId, String locationId);

}
