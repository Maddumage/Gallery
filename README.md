# Gallery
This is an android photo gallery application using Unsplash.
Unsplash is an  online photo gallery and also they provide API to use photos from their gallery. 

### Prerequisites
#### Dependencies
* Volley - 
          
          implementation 'com.android.volley:volley:1.0.0'
* Glide - 

          implementation 'com.github.bumptech.glide:glide:3.7.0'

#### Plugin
* apply plugin: 'realm-android' - apply this plugin to build.gradle in application level

#### Classpath
* add classpath to build.gradle in project level 

                repositories {
                    google()
                    jcenter()
                }
                dependencies {
                    classpath "io.realm:realm-gradle-plugin:5.1.0"
                }
    

   

### Technologies
* Unsplash API - Online Image Gallery to import images 
* Volley Library - to call API
* Realm Database - to create local database to store metadata of images
* Glide Library - image loading library to load image from url to recycle view

----------------------------------------------------------------------------
### Main Functionalities
* 20 random image load from unsplash to gallery 
* user can add as a favorite an image from gallery
* user can add, delete, update description from the favorite items
* Delete favorite item from the list

