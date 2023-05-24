package vn.baodh.sparking.payment.core.app.service;


import vn.baodh.sparking.payment.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.payment.core.domain.model.base.BaseResponse;

public interface FlowHandler {

  BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo);

}
