package vn.baodh.sparking.payment.core.domain.repository;

import java.util.List;
import vn.baodh.sparking.payment.core.domain.model.HistoryModel;

public interface PaymentRepository {

  boolean create(HistoryModel model) throws Exception;

  List<HistoryModel> getHistoriesByPhoneAndType(String phone, String type, String prevId) throws Exception;

}
