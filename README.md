# Assignment2
Second Assignment
-----
In this assignment I have added following items in layout file
1. Progressbar- To show progress of the http request.
2. Error message & retry button- this will be shown if internet connection not available, request timeouts or no data found on server.
3. Spinner, label for transport mode, navigate button- this will be visible if data found.

Terms used:
-----
* AsyncTaskLoader is used to load data from given url
* URL & HttpURLConnection class is used to connect to server.
* Once responce received it is parsed using JSONArray and JSONObjet class. In this i have used optString(), optDouble() methods 
  because if key not found in data it will not throw any exception its just ignore the key and returns default values.

This is how I added two build variants with separate package name in gradle file:
```java
android {
    compileSdkVersion 23
    buildToolsVersion "23.0.2"
    defaultConfig {
        applicationId "com.test.assignment2"
        minSdkVersion 16
        targetSdkVersion 23
        versionCode 1
        versionName "1.0"
    }

    signingConfigs {
        releaseConfig {
            keyAlias 'release'
            keyPassword '123456'
            storeFile file('D:/Shailendra/Assignment2/app/release_keystore.jks')
            storePassword '123456'
        }
    }

    buildTypes {
        release {
            minifyEnabled false
            debuggable false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            signingConfig signingConfigs.releaseConfig
        }
        debug {
            debuggable true
            applicationIdSuffix ".debug"
        }
    }

    productFlavors{
        releaseFlavor{
            applicationId "com.test.assignment2.release"
        }

        debugFlavor {
            applicationId "com.test.assignment2.debug"
        }
    }
  }
  ```
