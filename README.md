# Capybara 

![capybara_new-form_of_shopping](https://user-images.githubusercontent.com/113629828/200191359-306bb5ec-8c1a-46f2-8beb-bb7a6962f461.jpeg)

An Android e-commerce application built with MVVM clean code architecture which uses Fake Store API to retrieve store data.
​
# App Visuals
<img src="https://user-images.githubusercontent.com/113629828/200200681-3ee60a2d-e3b4-4a0e-b901-b945ff85f6cc.gif" width="250" height="450"/> <img src="https://user-images.githubusercontent.com/113629828/200200998-35e40eb0-8e6c-48d8-b1a4-80726fc6c1dc.gif" width="250" height="450"/> 
​
# Features
* Onboarding Page
* Login-Sign Up with Firebase Auth.
* Home Page which lists store items with respect to categories
* Searching/Categorizing 
* Product Detail/User Cart real-time interaction
* User Cart with checkout scenario
* Profile Page
​
# Tech Stack
* Kotlin for Android
* LifeCycle-Aware Components
* ViewModel
* GSON
* Kotlin Coroutines
* Kotlin Flow
* Room Database
* Firebase
* Glide
* Hilt-Dagger dependency injection
​
# How to Download?
You can download the file directly or clone the repository and open in Android Studio.
​
# Design and Architecture 
Built with MVVM clean code architecture for achieving a well-strcutured code. Parallel to recommendations, three different layers (data, domain and presentation) which are implemented as packages constitute the whole project.
To give an insight about layers,
* Data Layer provides Retrofit for remote data, Room for local database, Firebase, dataStore, adapters, models and utilization for app.
* Domain Layer provides remote repository and use-cases, core logics of the app and interactions with data layer.
* Presentation Layer displays content on the screen, interacts with user and sends/retrieves data according to user actions using use-cases and repository provided by above layers.
​
# Future Roadmap
As a first step, UI design improvements can be done in light of the feedbacks that will be received.
