package vn.baodh.sparking.parking.core.infra.controller.http;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.baodh.sparking.parking.core.app.mapping.FlowMapping;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.domain.enumeration.FlowEnum;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.BaseRequest;
import vn.baodh.sparking.parking.core.domain.model.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.BaseResponse;

@Slf4j
@RestController
@RequestMapping("/um/user")
@RequiredArgsConstructor
public class UserAuthHttpController {

  private final FlowMapping flowMapping;

  @GetMapping("/auth/**")
  public ResponseEntity<?> handleGetAuthentication(
      HttpServletRequest request,
      @RequestParam Map<String, String> params
  ) {
    BaseResponse<?> response = new BaseResponse<>();
    try {
      String url = request.getRequestURI();
      String[] comps = url.split("/");
      StringBuilder methodName;
      if (comps.length < 5) {
        methodName = new StringBuilder(FlowEnum.UNKNOWN.getFlowName());
      } else {
        methodName = new StringBuilder(comps[4]);
        for (int i = 5; i < comps.length; i++) {
          methodName.append("/").append(comps[i]);
        }
      }
      BaseRequestInfo<String> baseRequestInfo
          = new BaseRequestInfo<>(methodName.toString(), params);
      FlowEnum flowEnum = FlowEnum.getFlowEnum(methodName.toString());
      FlowHandler flowHandler = flowMapping.getFlowHandler(flowEnum);
      response = flowHandler.handle(baseRequestInfo);
      log.info(
          "[UserAuthHttpController] Finish handleGetAuthentication with request: {}, response: {}",
          params, response);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[UserAuthHttpController] handleGetAuthentication exception with request: {}, response: {}, ",
          request, response, exception);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
  }


  @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> handlePostAuthentication(@RequestBody BaseRequest request) {
    BaseResponse<?> response = new BaseResponse<>();
    try {
      String methodName = request.getMethod();
      BaseRequestInfo<String> baseRequestInfo = new BaseRequestInfo<>(
          request.getMethod(),
          request.getParams());
      FlowEnum flowEnum = FlowEnum.getFlowEnum(methodName);
      FlowHandler flowHandler = flowMapping.getFlowHandler(flowEnum);
      response = flowHandler.handle(baseRequestInfo);
      log.info(
          "[UserAuthHttpController] Finish handlePostAuthentication with request: {}, response: {}",
          request, response);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[UserAuthHttpController] handlePostAuthentication exception with request: {}, response: {}, ",
          request, response, exception);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
  }
}
