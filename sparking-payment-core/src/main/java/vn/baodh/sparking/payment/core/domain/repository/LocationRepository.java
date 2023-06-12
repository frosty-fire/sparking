package vn.baodh.sparking.payment.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.payment.core.domain.model.LocationDetailModel;

public interface LocationRepository {

  List<LocationDetailModel> getLocationById(String locationId) throws Exception;

}
