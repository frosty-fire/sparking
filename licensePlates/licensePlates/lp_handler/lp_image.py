from PIL import Image
import cv2
import torch
import math
import licensePlates.lp_handler.function.utils_rotate as utils_rotate
from IPython.display import display
import os
import time
import argparse
import licensePlates.lp_handler.function.helper as helper
import base64
import sys
import numpy as np


# ap = argparse.ArgumentParser()
# ap.add_argument('-i', '--image', required=True, help='path to input image')
# args = ap.parse_args()
def convert_license_plate(b64_string):
    try:
        yolo_LP_detect = torch.hub.load('licensePlates/lp_handler/yolov5', 'custom', path='licensePlates/lp_handler/model/LP_detector.pt', force_reload=True, source='local')
        yolo_license_plate = torch.hub.load('licensePlates/lp_handler/yolov5', 'custom', path='licensePlates/lp_handler/model/LP_ocr.pt', force_reload=True, source='local')
        yolo_license_plate.conf = 0.60

        # img = cv2.imread(args.image)

        # with open(args.image, "rb") as img_file:
        #     b64_string = base64.b64encode(img_file.read())
        b64_bytes = b64_string.encode()
        imgz = base64.b64decode(b64_bytes)
        npimg = np.frombuffer(imgz, dtype=np.uint8)
        img = cv2.imdecode(npimg, 1)

        plates = yolo_LP_detect(img, size=640)
        list_plates = plates.pandas().xyxy[0].values.tolist()
        list_read_plates = set()
        if len(list_plates) == 0:
            lp = helper.read_plate(yolo_license_plate, img)
            if lp != "unknown":
                # cv2.putText(img, lp, (7, 70), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (36,255,12), 2)
                list_read_plates.add(lp)
        else:
            for plate in list_plates:
                flag = 0
                x = int(plate[0])  # xmin
                y = int(plate[1])  # ymin
                w = int(plate[2] - plate[0])  # xmax - xmin
                h = int(plate[3] - plate[1])  # ymax - ymin
                crop_img = img[y:y + h, x:x + w]
                # cv2.rectangle(img, (int(plate[0]),int(plate[1])), (int(plate[2]),int(plate[3])), color = (0,0,225), thickness = 2)
                # cv2.imwrite("crop.jpg", crop_img)
                # rc_image = cv2.imread("crop.jpg")
                lp = ""
                for cc in range(0, 2):
                    for ct in range(0, 2):
                        lp = helper.read_plate(yolo_license_plate, utils_rotate.deskew(crop_img, cc, ct))
                        if lp != "unknown":
                            list_read_plates.add(lp)
                            # cv2.putText(img, lp, (int(plate[0]), int(plate[1]-10)), cv2.FONT_HERSHEY_SIMPLEX, 0.9, (36,255,12), 2)
                            flag = 1
                            break
                    if flag == 1:
                        break
        return list_read_plates.pop() if len(list_read_plates) > 0 else None
    except Exception as e:
        print("Error: ", e)
        return None
