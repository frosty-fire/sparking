package vn.baodh.sparking.parking.core.infra.jdbc.master;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
