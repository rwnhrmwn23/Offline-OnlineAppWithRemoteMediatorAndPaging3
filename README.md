# AstroTest

In this repository, there is an android application that implements
a list of GitHub users sourced from the [Github API](https://docs.github.com/en/rest) and you can
store data favorite someone with Room Database.

# Stack
- Kotlin
- UI Architecture with MVVM
- Remote Mediator for implementing an online-offline feature and combined with Paging3
- Dependency Injection with Koin
- Local Data Storage with Room
- Coroutine to handle data flow
- Networking with Retrofit

# Configuration
Before run the app please open CoreModule.kt at core > di > networkModule and replace
{{YOUR_TOKEN_GITHUB}} with yours. You can get the token [here](https://github.com/settings/tokens).
