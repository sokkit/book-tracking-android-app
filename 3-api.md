**Mobile Development 2021/22 Portfolio**
# API description

Your username: `c2061455`

Student ID: `2061455`

To navigate the application, I will be utilising the bottom navigation bar combined with fragments. The bottom navigation bar will comprise of the main features of the application: home page, reading list, read list, to be read list, and trophies. I chose the bottom navigation bar as opposed to something like tabs because of usability. The user’s thumb is going to naturally be at the bottom of the screen, so it makes sense to put the main features there.

Many of the main features of the application (the read, reading, and to be read lists) will be quite similar, so I will be using fragments. The application should also be easier to manage by keeping activities to a small level as fragments are more lightweight and easier to manage. Fragments do not need to be added to the manifest.

To search for books, I will be utilising the SearchView and Volley to query the Google books API. This should be sufficient to obtain all the information needed for the application. To display search results, I will be using ScrollView as there will be multiple results. To display all my views, I will be using Constraint Layout. This is the layout recommended by Google and avoids nested hierarchies while also allowing for complex UI designs. My home page will be quite complex, comprising of multiple views including a ScrollView, so a Constraint Layout will allow me to manage that.

In order to store the user’s books, I plan on using a database. Android provides a package for SQLite which should be sufficient for this application. SQLite doesn’t have stored procedures, but this application will only require a simple database. 


<!-- 
Comments from Sandy

You have done a good job here of focusing in on the important decisions that you've made in your application. I think you could improve the conciseness of this section by referring explicitly to the classes you're using, so there's no room for ambiguity. Putting them in `backticks` would make it even clearer that you were referring to implementations of specific classess in the Android API.

What is the alternative to using Fragments? You say that they are more lightweight and easier to manage, but you're missing a 'than'. I have a decent idea of what you're going to say, but I can't give marks for what I imagine to be the case. Be explicit, be concrete. How are they easier to use and more lightweight that just having lots and lots of activities? Is there anything you have to give up in choosing fragments over activities? Why is that a price worth paying in your context?

You write that "comprising of multiple views including a ScrollView, so a Constraint Layout will allow me to manage that", what features of ConstraintLayouts make this management better/easier/more effective than other options? It'd help strengthen your work if you can describe alternative paths not taken. Appreciate you're short of words, so keep it short, and focus on fewer choices if it means you can provide a full justification for them.

Are you going to interface directly with SQLite using one of the Java libraries? Is this the best option for persistence/interacting with SQLite? What are the alternatives and why haven't you gone with them?

-->
