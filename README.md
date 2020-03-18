# Firebase Template

For developing android apps quickly with:
  - Authentication

1. Rename "template" -> "your name".
2. Create a new project in Firebase Console.
    3. Download and place the google-services.json
    4. Authentication > Set up sign-in method
    5. GCP Console (https://console.cloud.google.com/apis/credentials)
           > Credentials > "Web application type client ID"
       Use this for @string/firebase_web_client_id
    6. In order to get the client ID above, you actually need to fill out an "OAuth consent screen". 
    7. If you can't login, here's how to setup the correct OAuth client ID:
        Create OAuth client ID -> Web application
        Authorized JavaScript origins:
          http://localhost
          http://localhost:5000
          https://foraday-app.firebaseapp.com
        Authorized redirect URIs: 
          https://foraday-app.firebaseapp.com/__/auth/handler

