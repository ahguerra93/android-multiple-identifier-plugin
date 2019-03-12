## 1.0.2

* **Breaking change**. Migrate from the deprecated original Android Support Library to AndroidX. This shouldn't result in any functional changes, but it requires any Android apps using this plugin to also [migrate](https://developer.android.com/jetpack/androidx/migrate) if they're using the original support library.

## 0.2.7

* Added compatibility with other plugins that requests permissions.
* Added license.


## 0.2.6

* Added a method for opening the application settings screen.


## 0.2.5 

* Added a method for checking if the user check the "never ask again" box on permission request

## 0.2.4

* Fixed bug on rationale method for android below api 26

## 0.2.1 - 0.2.3

* updated and fixed instruccions


## 0.2.0

* updated plugin info

## 0.1.10

* Added functionality to api lower than 26.

## 0.1.9

* Removed simple_permissions plugin, removed method channels for iOS 
* It's not compatible with iOS but it doesn't crash on iOS when added in the pubspec.yaml file

## 0.1.3 - 0.1.4

* Added an idMap method that returns a map with all the id's

## 0.0.9 - 0.1.2

* Fixed graddle dependencies issues for older versions.

## 0.0.2 

* Added Permission methods

## 0.0.1

* Initial release.
