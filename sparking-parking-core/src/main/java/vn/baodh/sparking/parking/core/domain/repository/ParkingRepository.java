package vn.baodh.sparking.parking.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.parking.core.domain.model.ParkingModel;
import vn.baodh.sparking.parking.core.domain.model.VehicleModel;

public interface ParkingRepository {

  ParkingModel create(ParkingModel entity) throws Exception;

  boolean update(ParkingModel entity);

  List<ParkingModel> getParkingById(String id) throws Exception;

  List<VehicleModel> getVehiclesByPhone(String phone) throws Exception;
}
