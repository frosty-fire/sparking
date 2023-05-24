package vn.baodh.sparking.um.authorization.app.service;

import vn.baodh.sparking.um.authorization.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseResponse;

public interface FlowHandler {

  BaseResponse<?> handle(BaseRequestInfo<?> baseRequestInfo);

}
