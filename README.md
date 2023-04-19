# image_object_detection_rest_service
Image Object Detection REST Service

Build/Run:

./gradlew clean build bootRun

Swagger UI:

http://localhost:8080/swagger-ui/index.html

API Specification:

-GET http://localhost:8080/images 
Returns HTTP 200 OK with a JSON response containing all image metadata.

-GET http://localhost:8080/images?objects="dog,cat" 
Returns a HTTP 200 OK with a JSON response body containing only images that have the detected objects specified in the query  parameter. 

-GET http://localhost:8080/images/{imageId} 
Returns HTTP 200 OK with a JSON response containing image metadata for the specified image. 

-POST http://localhost:8080/images 
Send a JSON request body including an image file or URL, an optional label for the image, and an optional field to enable object  detection. 
Returns a HTTP 200 OK with a JSON response body including the image data, its label (generate one if the user did not provide it), its  identifier provided by the persistent data store, and any objects detected (if object detection was enabled). 
