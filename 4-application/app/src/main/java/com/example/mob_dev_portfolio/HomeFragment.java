package com.example.mob_dev_portfolio;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstance) {
        SearchView searchView = (SearchView) getView().findViewById(R.id.searchView);
        ArrayList<Book> books = new ArrayList<>();



        //Access text from search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //Query API and display results on search submit
            public boolean onQueryTextSubmit(String s) {

                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                String API_KEY = "AIzaSyDbNPjEszabwPq-xMd3sNTUIaXp9A5IbDA";
                String url = "https://www.googleapis.com/books/v1/volumes?q=";
                RequestQueue requestQueue = Volley.newRequestQueue(getContext()); //getContext() might be wrong
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, "https://www.googleapis.com/books/v1/volumes?q=" + s, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    System.out.println(response);
                                    System.out.println("length: " + response.getJSONArray("items").length());
                                    String bookTitle;
                                    String bookAuthor;
                                    books.clear();
//                                    Add results to ListView items
                                    for (int i = 0; i < response.getJSONArray("items").length(); i++) {
                                        System.out.println(response.getJSONArray("items").getJSONObject(i));
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
//                                        add values to list
                                        books.add(new Book(bookTitle, bookAuthor));
                                    }
//                                    Add results to ListView
                                    ArrayList<String> listContent = new ArrayList<String>();
                                    for (Book b: books) {
                                        listContent.add(b.parseBook());
                                    }
                                    ArrayAdapter<String> la = new ArrayAdapter<String>(
                                            getContext(),
                                            android.R.layout.simple_list_item_1,
                                            listContent
                                    );

                                    ListView lv = (ListView) getView().findViewById(R.id.searchResults);
                                    lv.setAdapter(la);
                                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                            PopupMenu popupMenu = new PopupMenu(getContext(), lv, Gravity.CENTER_HORIZONTAL, R.attr.actionOverflowMenuStyle, 1);
                                            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                                            popupMenu.show();
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

}