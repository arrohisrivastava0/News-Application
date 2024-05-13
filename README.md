# News Application
A Java based Android application which fetches real time news data from a news api.
<p>
  <img src="https://github.com/arrohisrivastava0/News-Application/blob/master/images/newsApp_ss1.jpg" width="300">
  <img src="https://github.com/arrohisrivastava0/News-Application/blob/master/images/newsApp_ss2.jpg" width="300">
  <img src="https://github.com/arrohisrivastava0/News-Application/blob/master/images/newsApp_ss3.jpg" width="300">
</p>

## Installation
+ Clone this repository to your local machine using 'git clone'.
+ Open the project in Android Studio.
+ Build and run the application on an Android device or emulator.

## Project setup 

### Libraries Used
- [Retrofit](https://square.github.io/retrofit/) - for handling REST API calls
- [GsonConverterFactory](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - for converting JSON data to Java objects and vice versa

### Android Manifest
+ Add the following permissions in your AndroidManifest.xml file.
```xml
    <uses-permission android:name="android.permission.INTERNET" />
```
+ Make sure to add the following actvities.
```xml
        <activity
            android:name=".ExplorePageActivity"
            android:exported="false" />
        <activity
            android:name=".NewsDetailActivity"
            android:exported="false" />
```
### Import drawables
+ You can add icons and fonts in the drawable file as per your choice.
+ Or you can work with the [icons](https://github.com/arrohisrivastava0/News-Application/tree/master/app/src/main/res/drawable) and [fonts](https://github.com/arrohisrivastava0/News-Application/tree/master/app/src/main/res/font) given in this repository.

### Main Activity UI
Refer to the XML file for the UI given [here](https://github.com/arrohisrivastava0/News-Application/blob/master/app/src/main/res/layout/activity_main.xml)

## News API
We are using [News Api](https://newsapi.org/) for this project.

### Steps
+ You need to [register](https://newsapi.org/register) to get your API key and start using API right away!
+ Refer to the documentations for the detailed steps [here](https://newsapi.org/docs)

### Endpoints
This News API has 2 main endpoints:
+ [Everything](https://newsapi.org/docs/endpoints/everything) ```/v2/everything``` – search every article published by over 150,000 different sources large and small in the last 5 years. This endpoint is ideal for news analysis and article discovery.
+ [Top headlines](https://newsapi.org/docs/endpoints/top-headlines) ```/v2/top-headlines``` – returns breaking news headlines for countries, categories, and singular publishers. This is perfect for use with news tickers or anywhere you want to use live up-to-date news headlines.

There is also a minor endpoint that can be used to retrieve a small subset of the publishers we can scan:
+ [Sources](https://newsapi.org/docs/endpoints/sources) ```/v2/top-headlines/sources``` – returns information (including name, description, and category) about the most notable sources available for obtaining top headlines from. This list could be piped directly through to your users when showing them some of the options available.

+ Given below is the base URL used in the project
  ```java
    String url;
        if(lang.equals("en")){
            url="https://newsapi.org/v2/top-headlines?country=in&language="+lang+"&excludeDomains=stackoverflow.com&sortBy=publishedAt&apiKey=<YOUR_API_KEY>";
        }
        else{
            url="https://newsapi.org/v2/top-headlines?language="+lang+"&excludeDomains=stackoverflow.com&sortBy=publishedAt&apiKey=<YOUR_API_KEY>";
        }
  ```


