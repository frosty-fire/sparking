package vn.baodh.sparking.um.authorization.infra.controller.http;

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
import vn.baodh.sparking.um.authorization.app.mapping.FlowMapping;
import vn.baodh.sparking.um.authorization.app.security.JwtTokenChecker;
import vn.baodh.sparking.um.authorization.app.service.FlowHandler;
import vn.baodh.sparking.um.authorization.domain.enumeration.FlowEnum;
import vn.baodh.sparking.um.authorization.domain.enumeration.StatusEnum;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseRequest;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.um.authorization.domain.model.base.BaseResponse;

@Slf4j
@RestController
@RequestMapping("/um")
@RequiredArgsConstructor
public class UserHttpController {

  private final FlowMapping flowMapping;
  private final JwtTokenChecker jwtTokenChecker;

  @PostMapping(value = "/user/get-friends", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> api1(
      HttpServletRequest uri,
      @RequestBody BaseRequest request) {
    BaseResponse<?> response = new BaseResponse<>();
    try {
      log.info("get");
      String phone = jwtTokenChecker.checkAndGetPhone(uri);
      if (phone != null) {

      }
    } catch (Exception ignored) {
      log.info("error");
    }
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/user/**")
  public ResponseEntity<?> handleGetAuthentication(
      HttpServletRequest uri,
      @RequestParam Map<String, String> params
  ) {
    BaseResponse<?> response = new BaseResponse<>();
    try {
      String methodName = getMethodName(uri);
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
          uri, response, exception);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
  }


  @PostMapping(value = "/user/**", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> handlePostAuthentication(
      HttpServletRequest uri,
      @RequestBody BaseRequest request) {
    BaseResponse<?> response = new BaseResponse<>();
    try {
      String methodName = getMethodName(uri);
      methodName = methodName + "/" + request.getMethod();
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

  private String getMethodName(HttpServletRequest uri) {
    String url = uri.getRequestURI();
    String[] comps = url.split("/");
    StringBuilder methodName;
    if (comps.length < 4) {
      methodName = new StringBuilder(FlowEnum.UNKNOWN.getFlowName());
    } else {
      methodName = new StringBuilder(comps[3]);
      for (int i = 4; i < comps.length; i++) {
        methodName.append("/").append(comps[i]);
      }
    }
    return methodName.toString();
  }
}
