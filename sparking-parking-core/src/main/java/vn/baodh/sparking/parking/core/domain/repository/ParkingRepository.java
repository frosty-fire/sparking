package vn.baodh.sparking.parking.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.parking.core.domain.model.VehicleDetailModel;
import vn.baodh.sparking.parking.core.domain.model.VehicleModel;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.ParkingEntity;

public interface ParkingRepository {

  boolean create(ParkingEntity entity) throws Exception;

  void updateExit(ParkingEntity entity) throws Exception;

  List<VehicleModel> getVehiclesByPhone(String phone) throws Exception;

  List<VehicleDetailModel> getVehicleById(String vehicleId) throws Exception;
}
