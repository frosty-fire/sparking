package vn.baodh.sparking.merchant.core.infra.client;

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
import vn.baodh.sparking.merchant.core.domain.model.ScanModel;
import vn.baodh.sparking.merchant.core.domain.model.SubmitQrRequest;
import vn.baodh.sparking.merchant.core.domain.model.SubmitQrRequest.Params;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParkingCoreHttpStub {

  public void submitQrParking(ScanModel scanModel) {
    try {
      log.info("[ParkingCoreHttpStub] start submit Qr to Parking Core");

      var body = new SubmitQrRequest().setParams(
          new Params().setQrToken(scanModel.getQrToken())
              .setLicensePlate(scanModel.getLicensePlate())
      );
      var jsonReq = new ObjectMapper().writeValueAsString(body);
      MediaType JSON = MediaType.parse("application/json; charset=utf-8");
      RequestBody formBody = RequestBody.create(jsonReq, JSON);

      String BASE_URL = "https://sparking.ngrok.app";
      Request request = new Request.Builder()
          .url(BASE_URL + "/prc/parking")
          .post(formBody)
          .build();

      OkHttpClient client = new OkHttpClient();
      Call call = client.newCall(request);
      Response response = call.execute();
      log.info("[ParkingCoreHttpStub] submit qr, request {}, response {}", request, response);
    } catch (Exception e) {
      log.error("[ParkingCoreHttpStub] call submit Qr to Parking Core >exception<", e);
    }
  }
}
