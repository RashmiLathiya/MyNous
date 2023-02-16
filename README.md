# Fetch JSON data from Assets file

## Project Title
>MyNous 

## Requirements  
1. The application should load a JSON from
https://cloud.nousdigital.net/s/Njedq4WpjWz4KKk/download
The JSON includes an array of objects consisting of an imageURL, a title, and a description.
2. The images should be browsable in a grid. When clicking on an image, a details-view should
open and display the enlarged image, title and description.
3. The details-view should also provide the functionality to send image, title and description via
email
4. The app should have the ability to filter grid using a search field,
both title and description should be considered.
5. It should perform as well in dark mode as in light mode.

## Description
* Add Doanloaded JSON file to Assets folder in Android source project
* Simply fetch json obejct-array data from items.json file from assets folder
* Create Items data class to set json data 
* Using Recyclerview to show all data in to grid view
* Show custom detail view to see all items data like id, title, description and image. 
* List can be filterable using title and description
* In deatil view all data can share via email
* App have Light and Dark mode on the main screen. 

## Dependencies
* 'com.github.bumptech.glide:glide:4.11.0'
* 'com.google.code.gson:gson:2.8.8'
* 'com.google.android.material:material:1.0.0'
* 'com.squareup.picasso:picasso:2.71828'

## Installing
* Android operating system

## Language
* Kotlin

## IDE
* Android Studio

## Version
* compileSdk/targetSdk 32





