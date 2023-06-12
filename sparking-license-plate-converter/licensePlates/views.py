from django.http import HttpResponse, JsonResponse

from rest_framework import status
from rest_framework.generics import ListCreateAPIView

from licensePlates.lp_handler.lp_image import convert_license_plate


class LicensePlateView(ListCreateAPIView):

    def get(self, request, *args, **kwargs):
        return HttpResponse("pong", status=status.HTTP_200_OK)

    def post(self, request, *args, **kwargs):
        method_name = request.data.get("method")
        response = dict()
        data = dict()
        if method_name == "plate-regconizer":
            params = dict(request.data.get("params"))
            print(len(params.get("image")))
            license_plate = convert_license_plate(params.get("image"))
            if license_plate:
                print("Thông tin biển số xe convert: " + license_plate)
                data["license-plate"] = license_plate
                response["returnCode"] = 1
                response["returnMessage"] = "Thành công"
                response["data"] = data
                return JsonResponse(response, status=status.HTTP_200_OK)
            else:
                print("Thông tin biển số xe convert rỗng")
        response["returnCode"] = 0
        response["returnMessage"] = "Hãy thử lại"
        response["data"] = None
        return JsonResponse(response, status=status.HTTP_200_OK)
