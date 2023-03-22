package vn.baodh.sparking.parking.core.app.service;

import vn.baodh.sparking.parking.core.domain.model.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.BaseResponse;

public interface FlowHandler {

  BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo);

}