package vn.baodh.sparking.parking.core.domain.repository;

import vn.baodh.sparking.parking.core.infra.jdbc.entity.MonthCardEntity;

public interface MonthCardRepository {

  boolean create(MonthCardEntity locationEntity) throws Exception;

  boolean update(MonthCardEntity locationEntity) throws Exception;

}
