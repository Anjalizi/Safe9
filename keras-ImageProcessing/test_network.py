# USAGE
# python test_network.py --model santa_not_santa.model --image examples/15.jpg

# import the necessary packages
from keras.preprocessing.image import img_to_array
from keras.models import load_model
import numpy as np
import argparse
import imutils
import cv2
import copy

# construct the argument parse and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-m", "--model", required=True,
	help="path to trained model model")
ap.add_argument("-i", "--image", required=True,
	help="path to input image")
args = vars(ap.parse_args())

# load the image
image = cv2.imread(args["image"])
orig = copy.copy(image)

# pre-process the image for classification
if image is not None:
	image = cv2.resize(image, (28, 28))
	image = image.astype("float") / 255.0
else:
	print('Image didnt load')

image = img_to_array(image)
image = np.expand_dims(image, axis=0)

# load the trained convolutional neural network
print("[INFO] loading network...")
model = load_model(args["model"])

# classify the input image
(safe, problem) = model.predict(image)[0]

# build the label
label = "problem" if problem > safe else "safe"
proba = problem if problem > safe else safe
label = "{}: {:.2f}%".format(label, proba * 100)

# draw the label on the image
output = imutils.resize(orig, width=400)
cv2.putText(output, label, (10, 25),  cv2.FONT_HERSHEY_SIMPLEX,
	0.7, (0, 255, 0), 2)

# show the output image
cv2.imshow("Output", output)
print("my required percentage: ", label)
cv2.waitKey(0)