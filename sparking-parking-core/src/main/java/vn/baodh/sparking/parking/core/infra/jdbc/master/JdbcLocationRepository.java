package vn.baodh.sparking.parking.core.infra.jdbc.master;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.parking.core.domain.model.LocationDetailModel;
import vn.baodh.sparking.parking.core.domain.model.LocationModel;
import vn.baodh.sparking.parking.core.domain.repository.LocationRepository;
import vn.baodh.sparking.parking.core.domain.repository.UserRepository;
import vn.baodh.sparking.parking.core.infra.jdbc.entity.LocationEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcLocationRepository implements LocationRepository {

  @Qualifier("masterNamedJdbcTemplate")
  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final UserRepository userRepository;
  private final String LOCATION_TABLE = "location";

  @Override
  public boolean create(LocationEntity entity) throws Exception {
    var prep = """
        insert into %s (location_id, location_name, address, max_slot, current_slot, location_code, location_password, phone, email, start_time, end_time, weekday, extra_info, image_url, map_url, created_at, updated_at)
        value (:location_id, :location_name, :address, :max_slot, :current_slot, :location_code, :location_password, :phone, :email, :start_time, :end_time, :weekday, :extra_info, :image_url, :map_url, now(3), now(3))
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, LOCATION_TABLE);
    params.addValue("location_id", entity.getLocationId());
    params.addValue("location_name", entity.getLocationName());
    params.addValue("address", entity.getAddress());
    params.addValue("max_slot", entity.getMaxSlot());
    params.addValue("current_slot", entity.getCurrentSlot());
    params.addValue("location_code", entity.getLocationCode());
    params.addValue("location_password", entity.getLocationPassword());
    params.addValue("phone", entity.getPhone());
    params.addValue("email", entity.getEmail());
    params.addValue("start_time", entity.getStartTime());
    params.addValue("end_time", entity.getEndTime());
    params.addValue("weekday", entity.getWeekday());
    params.addValue("extra_info", entity.getExtraInfo());
    params.addValue("image_url", entity.getImageUrl());
    params.addValue("map_url", entity.getMapUrl());

    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (DataIntegrityViolationException exception) {
      throw new DuplicateKeyException("duplicated notification_id: " + exception);
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public boolean update(LocationEntity entity) throws Exception {
    var prep = """
        update %s
        set location_name = :location_name, address = :address, max_slot = :max_slot, current_slot = :current_slot, location_code = :location_code, location_password = :location_password, phone = :phone, email = :email, start_time = :start_time, end_time = :end_time, weekday = :weekday, extra_info = :extra_info, image_url = :image_url, map_url = :map_url, updated_at = now(3)
        where location_id = :location_id;
        """;
    var params = new MapSqlParameterSource();
    var sql = String.format(prep, LOCATION_TABLE);
    params.addValue("location_id", entity.getLocationId());
    params.addValue("location_name", entity.getLocationName());
    params.addValue("address", entity.getAddress());
    params.addValue("max_slot", entity.getMaxSlot());
    params.addValue("current_slot", entity.getCurrentSlot());
    params.addValue("location_code", entity.getLocationCode());
    params.addValue("location_password", entity.getLocationPassword());
    params.addValue("phone", entity.getPhone());
    params.addValue("email", entity.getEmail());
    params.addValue("start_time", entity.getStartTime());
    params.addValue("end_time", entity.getEndTime());
    params.addValue("weekday", entity.getWeekday());
    params.addValue("extra_info", entity.getExtraInfo());
    params.addValue("image_url", entity.getImageUrl());
    params.addValue("map_url", entity.getMapUrl());

    try {
      return jdbcTemplate.update(sql, params) != 0;
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public List<LocationModel> getLocations()
      throws Exception {
    var prep = "select * from %s";
    var sql = String.format(prep, LOCATION_TABLE);
    var params = new MapSqlParameterSource();
    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> {
        var entity = new LocationEntity()
            .setLocationId(rs.getString("location_id"))
            .setLocationName(rs.getString("location_name"))
            .setAddress(rs.getString("address"))
            .setMaxSlot(rs.getString("max_slot"))
            .setCurrentSlot(rs.getString("current_slot"))
            .setLocationCode(rs.getString("location_code"))
            .setLocationPassword(rs.getString("location_password"))
            .setPhone(rs.getString("phone"))
            .setEmail(rs.getString("email"))
            .setStartTime(rs.getString("start_time"))
            .setEndTime(rs.getString("end_time"))
            .setWeekday(rs.getString("weekday"))
            .setExtraInfo(rs.getString("extra_info"))
            .setImageUrl(rs.getString("image_url"))
            .setMapUrl(rs.getString("map_url"))
            .setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()))
            .setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime()));
        return entity.toLocationModel();
      });
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }

  @Override
  public List<LocationDetailModel> getLocationById(String locationId) throws Exception {
    var prep = "select * from %s where location_id = :location_id";
    var sql = String.format(prep, LOCATION_TABLE);
    var params = new MapSqlParameterSource();
    params.addValue("location_id", locationId);
    try {
      return jdbcTemplate.query(sql, params, (rs, i) -> {
        var entity = new LocationEntity()
            .setLocationId(rs.getString("location_id"))
            .setLocationName(rs.getString("location_name"))
            .setAddress(rs.getString("address"))
            .setMaxSlot(rs.getString("max_slot"))
            .setCurrentSlot(rs.getString("current_slot"))
            .setLocationCode(rs.getString("location_code"))
            .setLocationPassword(rs.getString("location_password"))
            .setPhone(rs.getString("phone"))
            .setEmail(rs.getString("email"))
            .setStartTime(rs.getString("start_time"))
            .setEndTime(rs.getString("end_time"))
            .setWeekday(rs.getString("weekday"))
            .setExtraInfo(rs.getString("extra_info"))
            .setImageUrl(rs.getString("image_url"))
            .setMapUrl(rs.getString("map_url"))
            .setCreatedAt(String.valueOf(rs.getTimestamp("created_at").toLocalDateTime()))
            .setUpdatedAt(String.valueOf(rs.getTimestamp("updated_at").toLocalDateTime()));
        return entity.toLocationDetailModel();
      });
    } catch (Exception exception) {
      throw new Exception("database exception: " + exception);
    }
  }
}
