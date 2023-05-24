package vn.baodh.sparking.merchant.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.merchant.core.domain.model.VoucherModel;

public interface PromotionRepository {

  List<VoucherModel> getVouchers(int limit) throws Exception;

}
