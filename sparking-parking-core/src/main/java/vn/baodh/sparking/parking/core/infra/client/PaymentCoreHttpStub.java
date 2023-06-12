package vn.baodh.sparking.parking.core.infra.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCoreHttpStub {

  public void pay(PayModel model) {
    try {
      log.info("[ParkingCoreHttpStub] start submit Qr to Parking Core");

      var jsonReq = new ObjectMapper().writeValueAsString(model);
      MediaType JSON = MediaType.parse("application/json; charset=utf-8");
      RequestBody formBody = RequestBody.create(JSON, jsonReq);

      String BASE_URL = "https://sparking.ngrok.app";
      Request request = new Request.Builder()
          .url(BASE_URL + "/pyc/payment")
          .post(formBody)
          .build();

      OkHttpClient client = new OkHttpClient();
      Call call = client.newCall(request);
      Response response = call.execute();
      log.info("[PaymentCoreHttpStub] pay, request {}, response {}", request, response);
    } catch (Exception e) {
      log.error("[PaymentCoreHttpStub] call pay >exception<", e);
    }
  }
}
