package vn.baodh.sparking.parking.core.infra.jdbc.master;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.baodh.sparking.parking.core.domain.model.VoucherModel;
import vn.baodh.sparking.parking.core.domain.repository.PromotionRepository;

@Slf4j
@Repository
public class JdbcPromotionRepository implements PromotionRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final String PROMOTION_TABLE = "promotion";

  public JdbcPromotionRepository(
      @Qualifier("masterNamedJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


  @Override
  public List<VoucherModel> getVouchers(int limit) throws Exception {
    List<VoucherModel> voucherModels = new ArrayList<>();
    VoucherModel voucherModel1 = new VoucherModel()
        .setVoucherId("1")
        .setTitle("[App] Một lần gửi xe miễn phí tại bất kỳ bãi xe đã tích hợp.")
        .setImageUrl(
            "https://firebasestorage.googleapis.com/v0/b/sparking-app.appspot.com/o/voucher%2Ffree-parking.jpeg?alt=media&token=0d73c2af-6b33-4192-8a3c-13503b7c69d1");
    VoucherModel voucherModel2 = new VoucherModel()
        .setVoucherId("2")
        .setTitle("[Eastpoint Mall] Khuyến mãi đố xe vào cuối tuần.")
        .setImageUrl(
            "https://firebasestorage.googleapis.com/v0/b/sparking-app.appspot.com/o/voucher%2Feastpoint_mall.jpeg?alt=media&token=62dcd6a6-e374-42f8-bbbb-4deacb0de456");
    VoucherModel voucherModel3 = new VoucherModel()
        .setVoucherId("3")
        .setTitle("[Hype Parking] Miễn phí đỗ xe trong 8 giờ liên tiếp.")
        .setImageUrl(
            "https://firebasestorage.googleapis.com/v0/b/sparking-app.appspot.com/o/voucher%2Fhype_parking.webp?alt=media&token=84a81be4-8df8-41e0-9f16-450b38c10b5e");
    voucherModels.add(voucherModel1);
    voucherModels.add(voucherModel2);
    voucherModels.add(voucherModel3);
    return voucherModels;
  }

}
