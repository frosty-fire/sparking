package vn.baodh.sparking.um.authorization.infra.controller.http;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.baodh.sparking.um.authorization.domain.enumeration.StatusEnum;
import vn.baodh.sparking.um.authorization.domain.model.BaseRequest;
import vn.baodh.sparking.um.authorization.domain.model.BaseResponse;

@RestController
@RequestMapping("/um")
public class DefaultHttpController {
  @GetMapping("/ping")
  public ResponseEntity<?> ping() {
    return ResponseEntity.ok("pong");
  }

  @GetMapping(value = "/**")
  public ResponseEntity<?> handleGetInformation(HttpServletRequest request,
      @RequestParam Map<String, String> params) {
    BaseResponse<?> response = new BaseResponse<>();
    response.updateResponse(StatusEnum.SUCCESS.getStatusCode());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping(value = "/**", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> handlePostInformation(@RequestBody BaseRequest request) {
    BaseResponse<?> response = new BaseResponse<>();
    response.updateResponse(StatusEnum.UNKNOWN.getStatusCode());
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
