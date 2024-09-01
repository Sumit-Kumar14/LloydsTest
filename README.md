The app was created using the Dog [API](https://dog.ceo/dog-api/documentation/). It contains two screens:

Dogs List: Displays a grid of dogs filtered by breed.
Breed Search: Shows a list of breeds that can be searched.
The app is organized into different modules:
 - App
 - Core: Contains submodules such as Network and UI, with the possibility of extending to include others like Local.
 - Features: Includes app features such as List and Search. Each feature is divided into three layers:
  - Data
  - Domain
  - Presentation
