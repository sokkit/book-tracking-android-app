**Mobile Development 2021/22 Portfolio**
# API description

Your username: `c2061455`

Student ID: `2061455`

To navigate the application, I will be utilising the `BottomNavigationView`. The navigation bar will comprise of the main features of the application: home page, reading list, read list, to be read list, and trophies. I chose the `BottomNavigationView` as opposed to something like `TabLayout` because of usability. The user’s thumb is going to naturally be at the bottom of the screen, so it makes sense to put the main features there.

I will be using `Fragments` with a single `Activity`. An advantage of doing this is that by using `Fragments` with `BottomNavigationView` is that the nav bar only has to be in one `Activity` and the `Fragments` are nested inside. If the nav bar switched between `Activities`, I would need to add a nav bar to each `Activity` and the animation would not work. To communicate between the `Fragments`, I will use a `Bundle`.

To search for books, I will be utilising `SearchView` and `Volley` to query the Google Books API. This should be sufficient to obtain all the information needed for the application. To display search results, I will be using a `ListView` with a custom adapter to allow me to add pictures and customise the display of the results. The trophies page requires a grid layout so I will use a `RecyclerView` for the trophies page as I can easily add a `GridLayoutManager` to it. 

In order to store the user’s books and quotes, I will use an SQLite database in combination with `Room`. This will allow data to persist between opening and closing the application. `Room` is recommended by Google and provides benefits over using SQLite directly, such as compile-time verification of SQL queries. For more basic data storage, such as Booleans stating if the user has won a trophy, I will use `SharedPreferences`.



