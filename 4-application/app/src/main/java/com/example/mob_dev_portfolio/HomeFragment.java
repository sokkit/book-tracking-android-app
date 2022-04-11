package com.example.mob_dev_portfolio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        SearchView searchView = (SearchView) getView().findViewById(R.id.searchView);
        ArrayList<BookSearch> bookSearches = new ArrayList<>();
        Button btScan = (Button) getView().findViewById(R.id.bt_scan);


        // Barcode Scanner
        // ref https://www.youtube.com/watch?v=u2pgSu9RhYo
        // adapted to work in fragment and perform api search
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(
                        getActivity());
                intentIntegrator.setPrompt("For flash use volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setCaptureActivity(Capture.class);
                IntentIntegrator.forSupportFragment(HomeFragment.this).initiateScan();
            }
        });
        // end of reference

        //Access text from search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //Query API and display results on search submit
            public boolean onQueryTextSubmit(String s) {
                RequestQueue requestQueue = Volley.newRequestQueue(getContext()); //getContext() might be wrong
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes?q=" + s + "&printType=books", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String bookTitle;
                                    String bookAuthor;
                                    String isbn;
                                    String thumbnail;
                                    String description;
                                    bookSearches.clear();
//                                    Add results to ListView items
                                    for (int i = 0; i < response.getJSONArray("items").length(); i++) {
                                        System.out.println(response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("title"));
//                                        if title and author exist, assign them
                                        try {
                                            bookTitle = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("title");
                                        } catch (JSONException err) {
                                            bookTitle = "Not title found";
                                        }
                                        try {
                                            bookAuthor = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("authors");
                                        } catch (JSONException err) {
                                            bookAuthor = "No author found";
                                        }
                                        try {
                                            isbn = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers").getJSONObject(0).getString("identifier");
                                        } catch (JSONException err) {
                                            isbn = "";
                                        }
                                        try {
                                            thumbnail = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
                                            // turn url into https
                                            StringBuilder sb = new StringBuilder(thumbnail);
                                            sb.insert(4,'s');
                                            thumbnail = sb.toString();
                                        } catch (JSONException err) {
                                            thumbnail = null;
                                        }
                                        try {
                                            description = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("description");
                                        } catch (JSONException err) {
                                            err.printStackTrace();
                                            description = "No description listed for this book";
                                        }
//                                        add values to list
                                        bookSearches.add(new BookSearch(bookTitle, bookAuthor, isbn, thumbnail, description, null));
                                    }
//                                    Add results to ListView
                                    ArrayList<String> listContent = new ArrayList<String>();
                                    for (BookSearch b: bookSearches) {
                                        listContent.add(b.parseBook());
                                    }
                                    HomeListAdapter adapter=new HomeListAdapter(getContext(), bookSearches);
                                    ListView lv = (ListView) getView().findViewById(R.id.searchResults);
                                    lv.setAdapter(adapter);
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            Bundle bundle = new Bundle();
                                            bundle.putParcelable("potential book", bookSearches.get(i));
                                            // ref switch fragment from within fragment. Adapted to add bundle
                                            // https://stackoverflow.com/a/13217087/14457259
                                            Fragment newFragment = new BookInfoFragment();
                                            newFragment.setArguments(bundle);
                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                            transaction.replace(R.id.container, newFragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();
                                            // end of reference
                                        }
                                    });
                                } catch (JSONException err) {
                                    err.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }
                );
                requestQueue.add(getRequest);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    //Barcode Scanner
    // ref https://www.youtube.com/watch?v=u2pgSu9RhYo
    // adapted to work in fragment and perform api search
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents() != null) {
            apiSearchIsbn(intentResult.getContents());
        } else {
            Toast.makeText(getContext(), "Oops, you didn't scan anything",
                    Toast.LENGTH_SHORT).show();
        }
    }
    // End of reference

    public void apiSearchIsbn(String term) {
        ArrayList<BookSearch> bookSearches = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext()); //getContext() might be wrong
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes?q=isbn" + term + "&printType=books", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String bookTitle;
                            String bookAuthor;
                            String isbn;
                            String thumbnail;
                            String description;
                            bookSearches.clear();
//                                    Add results to ListView items
                            for (int i = 0; i < 1; i++) {
                                System.out.println(response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("title"));
//                                        if title and author exist, assign them
                                try {
                                    bookTitle = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("title");
                                } catch (JSONException err) {
                                    bookTitle = "Not title found";
                                }
                                try {
                                    bookAuthor = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("authors");
                                } catch (JSONException err) {
                                    bookAuthor = "No author found";
                                }
                                try {
                                    isbn = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getJSONArray("industryIdentifiers").getJSONObject(0).getString("identifier");
                                } catch (JSONException err) {
                                    isbn = "";
                                }
                                try {
                                    thumbnail = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getJSONObject("imageLinks").getString("thumbnail");
                                    // turn url into https
                                    StringBuilder sb = new StringBuilder(thumbnail);
                                    sb.insert(4,'s');
                                    thumbnail = sb.toString();
                                } catch (JSONException err) {
                                    thumbnail = null;
                                }
                                try {
                                    description = response.getJSONArray("items").getJSONObject(i).getJSONObject("volumeInfo").getString("description");
                                } catch (JSONException err) {
                                    err.printStackTrace();
                                    description = "No description listed for this book";
                                }
//                                        add values to list
                                bookSearches.add(new BookSearch(bookTitle, bookAuthor, isbn, thumbnail, description, null));
                            }
                            //                                    Add results to ListView
                            ArrayList<String> listContent = new ArrayList<String>();
                            for (BookSearch b: bookSearches) {
                                listContent.add(b.parseBook());
                            }
                            HomeListAdapter adapter=new HomeListAdapter(getContext(), bookSearches);
                            ListView lv = (ListView) getView().findViewById(R.id.searchResults);
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("potential book", bookSearches.get(i));
                                    // ref switch fragment from within fragment. Adapted to add bundle
                                    // https://stackoverflow.com/a/13217087/14457259
                                    Fragment newFragment = new BookInfoFragment();
                                    newFragment.setArguments(bundle);
                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                    transaction.replace(R.id.container, newFragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                    // end of reference
                                }
                            });
                        } catch (JSONException err) {
                            err.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );
        requestQueue.add(getRequest);
//        return false;
    }

}