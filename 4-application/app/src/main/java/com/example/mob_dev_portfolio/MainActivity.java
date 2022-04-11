package com.example.mob_dev_portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mob_dev_portfolio.data.Book;
import com.example.mob_dev_portfolio.data.BookDB;
import com.example.mob_dev_portfolio.data.Quote;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    ExecutorService executor;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String currentBook = "currentBookKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // disable dark theme - causes bug with search
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        Set home as default navigation area
        bottomNavigationView.setSelectedItemId(R.id.home);

//        Build database which can be accessed by other fragments
//        BookDB db1 = BookDB.getInstance(this);
//        System.out.println("db1: " + db1);
//        List<Book> allBooks = db1.bookDao().getAllBooks();
//        System.out.println("all books: " + allBooks);
//        BookDB db = Room.databaseBuilder(
//                getApplicationContext(),
//                BookDB.class,
//                "book-db").build();

//        Context context = getContext();
//        BookDB db = BookDB.getInstance(this);

        BookDB db = Room.databaseBuilder(getApplicationContext(),
                BookDB.class, "book-db")
                .allowMainThreadQueries().createFromAsset("database/mob2.db")
                .build();

        this.executor = Executors.newFixedThreadPool(4);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("All books: ");
                List<Book> allBooks = db.bookDao().getAllBooks();
                System.out.println(allBooks);
//                db.clearAllTables();
//                db.bookDao().insertAll(
//                        new Book("Thomas Pynchon", "Mason & Dixon",
//                                1, "1/3/2022",
//                                "15/3/2022", "Good book lol", 4.0F, "https://books.google.com/books/content?id=xAYD5jMNRzMC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                                "Charles Mason (1728 -1786) and Jeremiah Dixon (1733-1779) were the British Surveyors best remembered for running the boundary between Pennsylvania and Maryland that we know today as the Mason-Dixon Line. Here is their story as re-imagined by Thomas Pynchon",
//                                "1/3/2022"),
//                        new Book("Hunter S Thompson", "Fear and Loathing in Las Vegas",
//                                1, "15/3/2022",
//                                "20/3/2022", "Very good haha", 4.0F, "https://books.google.com/books/content?id=R11qaqN4jzQC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                                "This cult classic of gonzo journalism is the best chronicle of drug-soaked, addle-brained, rollicking good times ever committed to the printed page. It is also the tale of a long weekend road trip that has gone down in the annals of American pop culture as one of the strangest journeys ever undertaken. Also a major motion picture directed by Terry Gilliam, starring Johnny Depp and Benicio del Toro.",
//                                "14/3/2022"),
//                        new Book("Franz Kafka", "The Metamorphosis",
//                                1, "20/3/2022",
//                                "25/3/2022", "Weird book", 4.5F, "https://books.google.com/books/content?id=p2opDwAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
//                                "As Gregor Samsa awoke one morning from uneasy dreams he found himself transformed in his bed into a gigantic insect.The Metamorphosis - the masterpiece of Franz Kafka - was first published in 1915 and is one of the seminal works of fiction of the twentieth century. The novel is cited as a key influence for many of today’s leading authors; as Auden wrote: \\\"Kafka is important to us because his predicament is the predicament of modern man\\\".Traveling salesman, Gregor Samsa, wakes to find himself transformed into a large, monstrous insect-like creature. The cause of Gregor's transformation is never revealed, and as he attempts to adjust to his new condition he becomes a burden to his parents and sister, who are repelled by the horrible, verminous creature Gregor has become.A harrowing, yet strangely comic, meditation on human feelings of inadequacy, guilt, and isolation, The Metamorphosishas taken its place as one of the most widely read and influential works of twentieth-century fiction.",
//                                "10/2/2022"),
//                        new Book("Robert Louis Stevenson", "The Strange Case of Dr Jekyll and Mr Hyde",
//                                0, "25/3/2022",
//                                null, "", 0.0F, "https://books.google.com/books/content?id=yl1oAwAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                                "Everyone has a dark side. Dr Jekyll has discovered the ultimate drug. A chemical that can turn him into something else. Suddenly, he can unleash his deepest cruelties in the guise of the sinister Hyde. Transforming himself at will, he roams the streets of fog-bound London as his monstrous alter-ego. It seems he is master of his fate. It seems he is in complete control. But soon he will discover that his double life comes at a hideous price...",
//                                "25/3/2022"),
//                        new Book("David Foster Wallace", "Infinite Jest",
//                                0, "20/3/2022",
//                                null, "", 0F, "https://books.google.com/books/content?id=9uXSayGPlgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                                "'A writer of virtuostic talents who can seemingly do anything' New York Times 'Wallace is a superb comedian of culture . . . his exuberance and intellectual impishness are a delight' James Wood, Guardian 'He induces the kind of laughter which, when read in bed with a sleeping partner, wakes said sleeping partner up . . . He's damn good' Nicholas Lezard, Guardian 'One of the best books about addiction and recovery to appear in recent memory' Sunday Times Somewhere in the not-so-distant future the residents of Ennet House, a Boston halfway house for recovering addicts, and students at the nearby Enfield Tennis Academy are ensnared in the search for the master copy of Infinite Jest, a movie said to be so dangerously entertaining its viewers become entranced and expire in a state of catatonic bliss . . .",
//                                "20/3/2022"),
//                        new Book("Haruki Murakami", "Kafka on the Shore", 2,
//                                null, null, "", 0.0F, "https://books.google.com/books/content?id=L6AtuutQHpwC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                                "Kafka Tamura runs away from home at fifteen, under the shadow of his father's dark prophesy. The aging Nakata, tracker of lost cats, who never recovered from a bizarre childhood affliction, finds his pleasantly simplified life suddenly turned upside down. As their parallel odysseys unravel, cats converse with people; fish tumble from the sky; a ghost-like pimp deploys a Hegel-spouting girl of the night; a forest harbours soldiers apparently un-aged since World War II. There is a savage killing, but the identity of both victim and killer is a riddle - one of many which combine to create an elegant and dreamlike masterpiece. 'Wonderful... Magical and outlandish' Daily Mail 'Hypnotic, spellbinding' The Times 'Cool, fluent and addictive' Daily Telegraph",
//                                "1/4/2022"),
//                        new Book("Thomas Pynchon", "Vineland", 2, null,
//                                null, "", 0, "https://books.google.com/books/content?id=LIpPEAAAQBAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
//                                "Vineland, a zone of blessed anarchy in northern California, is the last refuge of hippiedom, a culture devastated by the sobriety epidemic, Reaganomics, and the Tube. Here, in an Orwellian 1984, Zoyd Wheeler and his daughter Prairie search for Prairie's long-lost mother, a Sixties radical who ran off with a narc. Vineland is vintage Pynchon, full of quasi-allegorical characters, elaborate unresolved subplots, corny songs (\\\"Floozy with an Uzi\\\"), movie spoofs (Pee-wee Herman in The Robert Musil Story), and illicit sex (including a macho variation on the infamous sportscar scene in V.).",
//                                "4/2/2022"),
//                        new Book("Richard Gott", "Cuba: A New History", 2,
//                                null, null, "", 0.0F, "https://books.google.com/books/content?id=aVq0qOnLFusC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api",
//                                "A thorough examination of the history of the controversial island country looks at little-known aspects of its past, from its pre-Columbian origins to the fate of its native peoples, complete with up-to-date information on Cuba's place in a post-Soviet world.",
//                                "15/2/2022"));
//                ArrayList<Integer> bookIds = new ArrayList<>();
//                for (Book b:
//                     db.bookDao().getAllBooks()) {
//                    bookIds.add(b.getBookId());
//                }
//                db.bookDao().insertAll(new Quote(bookIds.get(0), "The general public has long been divided into two parts; those who think that science can do anything and those who are afraid it will"),
//                        new Quote(bookIds.get(0), "and men of science,' cries dixon, 'may be but the simple tools of others, with no more idea of what they are about, than a hammer knows of a house."),
//                        new Quote(bookIds.get(0), "Mason glowers, shaking his head. \"I've ascended, descended, even condescended, and the List's not ended,— but haven't yet trans-cended a blessed thing, thankee" ));
//                db.bookDao().insertAll(new Quote(bookIds.get(1), "In a closed society where everybody's guilty, the only crime is getting caught. In a world of thieves, the only final sin is stupidity."),
//                        new Quote(bookIds.get(1), "Take it from me, there's nothing like a job well done. Except the quiet enveloping darkness at the bottom of a bottle of Jim Beam after a job done any way at all."));
//                db.bookDao().insertAll(new Quote(bookIds.get(2), "As Gregor Samsa awoke one morning from uneasy dreams he found himself transformed in his bed into a gigantic insect."),
//                        new Quote(bookIds.get(2), "He was a tool of the boss, without brains or backbone."),
//                        new Quote(bookIds.get(2), "A picture of my existence... would show a useless wooden stake covered in snow... stuck loosely at a slant in the ground in a ploughed field on the edge of a vast open plain on a dark winter night."));
            }
        });


    }
    HomeFragment homeFragment = new HomeFragment();
    ReadingFragment readingFragment = new ReadingFragment();
    ReadFragment readFragment = new ReadFragment();
    TbrFragment tbrFragment = new TbrFragment();
    TrophiesFragment trophiesFragment = new TrophiesFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                return true;

            case R.id.reading:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, readingFragment).commit();
                return true;

            case R.id.read:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, readFragment).commit();
                return true;

            case R.id.tbr:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, tbrFragment).commit();
                return true;

            case R.id.trophies:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, trophiesFragment).commit();
                return true;
        }
        return false;
    }
}
