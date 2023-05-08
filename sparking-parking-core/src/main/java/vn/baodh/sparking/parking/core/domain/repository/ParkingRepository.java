package vn.baodh.sparking.parking.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.parking.core.domain.model.VehicleDetailModel;
import vn.baodh.sparking.parking.core.domain.model.VehicleModel;

public interface ParkingRepository {

  List<VehicleModel> getVehiclesByPhone(String phone) throws Exception;

  List<VehicleDetailModel> getVehicleById(String vehicleId) throws Exception;
}
