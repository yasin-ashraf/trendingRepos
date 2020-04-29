# Github TrendingRepos MVVM

### Language : Kotlin

### Architecture

MVVM Architecture - Repository Pattern
Architecture Components - LiveData & ViewModel  
Dagger 2 for dependency injection  
OkHttp and Retrofit for networking  
Picasso for image loading  
Navigation Component  
Offline first with Room Database  

### Walk-through

Screen 1  : Home Screen
Initially shows the empty state with search bar. On searching for the repo's, repo's are listed in descending order
of watchers count. Search result is stored in db. Offline results are shown when the same keyword is searched for next two hours.
Refresh can be forced by Swipe refresh.

when in Screen Loading State : Swipe refresh is shown.
when in Screen Error State : Error state shown

Clicking on repo in the list navigates to Screen 2

Screen 2 : Detail Screen
Shows the details of the repo with the list of contributors.
Repo Details are loaded from the storage and contributors list is fetched via api (with error state and loading state)

Clicking on contributor in the list navigates to Screen 3
 
Screen 3 : Contributor Screen
shows the details of Contributor and all his repos (fetched via api - and stored in db).  
  
Clicking on repo in the list navigates to Screen 2