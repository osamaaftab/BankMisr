# android-test-assignment
A currency conversion app illustrating Android development best practices with jetpack compose.

# Features
- List of currencies to select from
- swap feature 
- Currency conversion.

# Technologies
- MVVM with clean code architecture.
- Flows
- Coroutines
- Koin dependency injection tool
- Retrofit
- Moshi
- Jetpack compose
- Junit
- MockK

<b>Modular Architecture:</b>
The app adopts a modular architecture, promoting separation of components for better maintenance and scalability:

App Module: Contains presentation components like Activities,and ViewModels.
Domain Module: Handles business logic, including use cases,entities.
Data Module: Manages data access, including communication with the API.

API Reference:

The app retrieves data, from the fixer API. For more details, visit: https://fixer.io/documentation.

NOTE : 
As requested to use hilt but instead I used koin for a reason that I wanted to do UI testing for which koin makes thing easy but due to time constraint I couldn't able to implement the UI testing however I am putting up some of the links to my other repository which has the technologies requested to assess.
https://github.com/osamaaftab/teya
https://github.com/osamaaftab/Nutmeg-home-take-assignment-2

# Author
Osama Aftab
