package com.example.mob_dev_portfolio;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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


        //Access text from search view
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
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
                                    System.out.println(response.getJSONArray("items").getJSONObject(1).getJSONObject("volumeInfo").getString("title"));
                                    System.out.println(response.getJSONArray("items").getJSONObject(1).getJSONObject("volumeInfo").getString("authors"));
                                    String titleResponse =  response.getJSONArray("items").getJSONObject(1).getJSONObject("volumeInfo").getString("title");
                                    String authorResponse =  response.getJSONArray("items").getJSONObject(1).getJSONObject("volumeInfo").getString("authors");
                                    System.out.println("title: " + titleResponse);
                                    System.out.println("author: " + authorResponse);
                                    TextView title = (TextView) getView().findViewById(R.id.textView2);
                                    TextView author = (TextView) getView().findViewById(R.id.textView3);
                                    title.setText(titleResponse);
                                    author.setText(authorResponse);
                                } catch (JSONException err) {
                                    err.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
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