package vn.baodh.sparking.um.authorization.app.service;

import vn.baodh.sparking.um.authorization.domain.model.BaseRequestInfo;
import vn.baodh.sparking.um.authorization.domain.model.BaseResponse;

public interface FlowHandler {

  BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo);

}