package vn.baodh.sparking.parking.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.parking.core.domain.model.LocationDetailModel;
import vn.baodh.sparking.parking.core.domain.model.LocationModel;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.LocationEntity;

public interface LocationRepository {
  boolean create(LocationEntity locationEntity) throws Exception;

  boolean update(LocationEntity locationEntity) throws Exception;

  List<LocationModel> getLocations() throws Exception;

  List<LocationDetailModel> getLocationById(String locationId) throws Exception;

}
