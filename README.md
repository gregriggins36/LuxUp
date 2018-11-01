# LuxUp
API with Ktor and Mobile App consuming web app resources

See full tutorial https://cloud.google.com/community/tutorials/kotlin-ktor-app-engine-java8

## How to get started
- Create a Google Cloud project https://console.cloud.google.com
- Install the Google Cloud SDK https://cloud.google.com/sdk/docs/quickstart-macos
- Run `./gradlew appengineDeploy` from webapp
- Run `gcloud app browse` to open the web app
- Run `curl --header "Content-Type: application/json" --request POST --data '{"name":"Bag","image":"https://upload.wikimedia.org/wikipedia/commons/d/d5/Laptop_bags_luxury_diManolo_%2812_of_15%29.jpg","price":9999.0}' https://your-google-cloud-project.appspot.com/articles`
Make sure to replace `your-google-cloud-project.appspot.com` with the project created in step #1
- Update the API endpoint in the Android project at https://github.com/gregriggins36/LuxUp/blob/master/mobile/app/src/main/java/com/frate/luxup/Constants.kt#L3, with the project created in step #1
