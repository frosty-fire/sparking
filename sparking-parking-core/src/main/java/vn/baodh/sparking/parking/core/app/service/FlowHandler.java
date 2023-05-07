package vn.baodh.sparking.parking.core.app.service;

import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;

public interface FlowHandler {

  BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo);

}
