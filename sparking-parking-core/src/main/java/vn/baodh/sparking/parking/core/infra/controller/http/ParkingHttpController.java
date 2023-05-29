package vn.baodh.sparking.parking.core.infra.controller.http;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.baodh.sparking.parking.core.app.mapping.FlowMapping;
import vn.baodh.sparking.parking.core.app.service.FlowHandler;
import vn.baodh.sparking.parking.core.app.service.socket.QrStatusEventUpdater;
import vn.baodh.sparking.parking.core.domain.enumeration.FlowEnum;
import vn.baodh.sparking.parking.core.domain.enumeration.StatusEnum;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequest;
import vn.baodh.sparking.parking.core.domain.model.base.BaseRequestInfo;
import vn.baodh.sparking.parking.core.domain.model.base.BaseResponse;
import vn.baodh.sparking.parking.core.domain.model.payload.StatusPayLoad;

@Slf4j
@RestController
@RequestMapping("/prc")
@RequiredArgsConstructor
public class ParkingHttpController {

  private final FlowMapping flowMapping;
  private final QrStatusEventUpdater qrStatusEventUpdater;

  @GetMapping("/ping")
  public ResponseEntity<?> ping(HttpServletRequest uri, @RequestParam Map<String, String> params) {
    return ResponseEntity.ok("pong");
  }

  @GetMapping(value = "/{any:^(?!socket\\.io).*$}")
  public ResponseEntity<?> handleGet(
      HttpServletRequest uri,
      @RequestParam Map<String, String> params,
      @PathVariable("any") String any) {
    var startTime = System.currentTimeMillis();
    BaseResponse<?> response = new BaseResponse<>();
    try {
      log.info(
          "[{}] Start handleGet with request: {}, {}",
          this.getClass().getSimpleName(), params, startTime);
      String methodName = getMethodName(uri);
      BaseRequestInfo<String> baseRequestInfo
          = new BaseRequestInfo<>(methodName, params);
      FlowEnum flowEnum = FlowEnum.getFlowEnum(methodName);
      FlowHandler flowHandler = flowMapping.getFlowHandler(flowEnum);
      response = flowHandler.handle(baseRequestInfo);
      log.info(
          "[{}][{}ms] Finish handleGet with request: {}, response: {}",
          this.getClass().getSimpleName(),
          System.currentTimeMillis() - startTime,
          params, response);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[{}][{}ms] handleGet <exception> with request: {}, response: {}",
          this.getClass().getSimpleName(),
          System.currentTimeMillis() - startTime,
          uri, response, exception);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
  }

  @PostMapping(value = "/{any:^(?!socket\\.io).*$}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> handlePost(
      HttpServletRequest uri,
      @RequestBody BaseRequest request,
      @PathVariable("any") String any) {
    var startTime = System.currentTimeMillis();
    BaseResponse<?> response = new BaseResponse<>();
    try {
      log.info(
          "[{}] Start handlePost with request: {}, {}",
          this.getClass().getSimpleName(), request, startTime);
      String methodName = getMethodName(uri);
      methodName = methodName + "/" + request.getMethod();
      BaseRequestInfo<String> baseRequestInfo = new BaseRequestInfo<>(
          request.getMethod(),
          request.getParams());
      FlowEnum flowEnum = FlowEnum.getFlowEnum(methodName);
      FlowHandler flowHandler = flowMapping.getFlowHandler(flowEnum);
      response = flowHandler.handle(baseRequestInfo);
      log.info(
          "[{}][{}ms] Finish handlePost with request: {}, response: {}",
          this.getClass().getSimpleName(),
          System.currentTimeMillis() - startTime,
          request, response);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } catch (Exception exception) {
      response.updateResponse(StatusEnum.EXCEPTION.getStatusCode());
      log.error(
          "[{}][{}ms] handlePost <exception> with request: {}, response: {}, ",
          this.getClass().getSimpleName(),
          System.currentTimeMillis() - startTime,
          request, response, exception);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
  }

  private String getMethodName(HttpServletRequest uri) {
    String url = uri.getRequestURI();
    String[] comps = url.split("/");
    StringBuilder methodName;
    if (comps.length < 3) {
      methodName = new StringBuilder(FlowEnum.UNKNOWN.getFlowName());
    } else {
      methodName = new StringBuilder(comps[2]);
      for (int i = 3; i < comps.length; i++) {
        methodName.append("/").append(comps[i]);
      }
    }
    return methodName.toString();
  }
}
