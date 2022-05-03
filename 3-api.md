**Mobile Development 2021/22 Portfolio**
# API description

Your username: `c2061455`

Student ID: `2061455`

To navigate the application, I will be utilising the `BottomNavigationView`. The navigation bar will comprise of the main features of the application: home page, reading list, read list, to be read list, and trophies. I chose the bottom navigation bar as opposed to something like `TabLayout` because of usability. The user’s thumb is going to naturally be at the bottom of the screen, so it makes sense to put the main features there.

Many of the main features of the application (the read, reading, and to be read lists) will be quite similar, so I will be using `Fragments`. The application should also be easier to manage by keeping activities to a small level as fragments are more lightweight and easier to manage. Fragments do not need to be added to the manifest.

I will be using `Fragments` with a single activity. An advantage of doing this is that by using `Fragments` with `BottomNavigationView` is that the nav bar only has to be in one activity and the `Fragments` are nested. If the nav bar switched between activities, I would need to add a nav bar to each activity and the animation would not work.

To search for books, I will be utilising the SearchView and Volley to query the Google books API. This should be sufficient to obtain all the information needed for the application. To display search results, I will be using ScrollView as there will be multiple results. To display all my views, I will be using Constraint Layout. This is the layout recommended by Google and avoids nested hierarchies while also allowing for complex UI designs. My home page will be quite complex, comprising of multiple views including a ScrollView, so a Constraint Layout will allow me to manage that.

In order to store the user’s books, I plan on using a database. Android provides a package for SQLite which should be sufficient for this application. SQLite doesn’t have stored procedures, but this application will only require a simple database. 
