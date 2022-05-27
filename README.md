# ClassManagement

**Environment:**

  - Coding: Android, Kotlin
  - Clould: Firebase
      + FirebaseAuth: supports for authentication
      + Firebase Realtime Database: supports CRUD database realtime
      + Firebase Storage: supports store file (jpg, pdf,...)
  - Backend: JavaSpringBoot
 
 **Architecture**: MVVM

**Feature:**
  - Auth: Athenticate with Firebase Auth
    + Login
    + Logout
    + Register
  - Chat Realtime: Chat with another user via FirebaseRealtimeDatabase
  
  - Class Management:
    + Create Class: create a class and you is the owner
    + Get Class: get all classes you are joining.
    + View Class detail: view detail a specify class
    + Post: 
      + Create post: Creates post include image
      + Like post: like a specify post
      + Comment post: get all comments and comment a specify post
    + Assignment:
      + Create assignment: create an assignment if you are the owner
      + Get assignment: gets all assignment in your class
      + View assignment: view detail an specify assignment
      + Submit assignment: submit to firebase storage if the assignment needs file include, otherwise send result to process by webService.
    + Class Member:
      + Get member of the class: gets all members of your class.
      + Add member: adds new member to your class
    - WebService:
      + Mark the submission: give score for the submission and respone to the student

**Dependencies:**
  - Firebase Auth
  - Firebase Storage
  - Firebase Realtime Database
  - Jetpack Compose: Navigation, LiveData
  - Retrofit

  

  
