package vn.baodh.sparking.parking.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.parking.core.domain.model.LocationDetailModel;
import vn.baodh.sparking.parking.core.domain.model.LocationModel;

public interface LocationRepository {

  List<LocationModel> getLocations() throws Exception;

  List<LocationDetailModel> getLocationById(String locationId) throws Exception;

}
