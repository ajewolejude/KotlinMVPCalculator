## MVP Kotlin Calculator

This project is an android calculator app using kotlin programming language. It was implemented using a MVP + ViewModel architecture
which includes a ViewModel object. 

## APP Link
https://drive.google.com/open?id=1OzRjznhntyo9I-zTWOp2l2Zv4cV9PkHr

## Implementation:
- Unit tests using JUnit
- Regex
- Mvp + viewmodel
- Espresso for UI Tests. https://drive.google.com/open?id=1CbjvejRaDs-ksoPrQYTQDipLI64oE7cN



### Architecture:
- Presenter: Presenter is the "Logic" center of the Application. It is the thing which you must be most certain to test, as it essentially coordinates interaction, of the View and Model.
- View: The View binds data to XML, and forwards click events to the Presenter. With little exception, it shouldn't contain complex logic which leads to unncessary complexity test.  
- Model: Models are the information (data) relevant to the Application. This can be long-term (like a Database), or short-term (like a ViewModel, and even a POJO/Data Class). Like the View, the Model shouldn't contain much logic (in most cases anyway). In this App, it helps us maintain and decouple the UI State, which keeps it safe from being destroyed by orientation changes, thus leading to poor UX.
- UseCase: To avoid our Presenter becoming too complex, and to reduce it's coupling to the backend (Validator and Calculator), the Presenter talks to a Use Case (or Interactor). UseCase Object which handles both Validaiton and Calculation, thus reducing the complexity of the Presenter.
- Repository: In order to decouple the Use Case from specific backend implementations, it talks to them via Interfaces (which can be thought of as the Repository/Facade Pattern). This means that we could swap out Validator or Calculator for something entirely different, and we would not need to change the rest of the Application one bit!
- Validator: Just performs some basic validation to see of an expression is...valid. 
- Calculator:. 
- Activity: Container for my MVP Components.
- Application: LeakCanary.
- Injector: A simple home baked Dependency Injection class.

### Inspiration

Awesome collection of projects made by pro devs and googlers:
https://github.com/googlesamples/android-architecture

Same as above, with emphasis on AAC things like ViewModel:
https://github.com/googlesamples/android-architecture-components

Martin Fowler (see his blog article called "Passive View"):
https://martinfowler.com/
