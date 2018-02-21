package brandon.example.com.starwarsapp;


import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link ListFragment} subclass.
 */
public class StarWarsListFragment extends ListFragment {
    private ListView listView;
    private RequestQueue mQueue;
    private StarWarsAdapter starWarsAdapter;
    private Context context;

    public StarWarsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_star_wars_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        starWarsAdapter = getAdapter(getStarWarsString());
        setListAdapter(starWarsAdapter);


    }

    private StarWarsAdapter getAdapter(String url){
        //adapter.clear();
        final StarWarsAdapter adapter1 = new StarWarsAdapter(getActivity(), R.layout.starwars_layout,new ArrayList<StarWars>());
        for(int i =1;i<=9;i++){
            String newUrl = url+String.valueOf(i)+"&format=json";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject jsonObject = results.getJSONObject(i);
                            StarWars starWars = new StarWars();
                            starWars.name = jsonObject.getString("name");
                            starWars.birth_year = jsonObject.getString("birth_year");
                            Log.e("MYLOG",starWars.name);
                            adapter1.add(starWars);
                        }
                        adapter1.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(request);
        }

        return adapter1;
    }

    private String getStarWarsString(){

        final String CHARACTER_BASE_URL =
                "https://swapi.co/api/people/?page=";
        //+String.valueOf(page)+"&format=json

        Uri builtUri;
        builtUri = Uri.parse(CHARACTER_BASE_URL).buildUpon()
                //.appendQueryParameter("id", "5")
                .build();


        return builtUri.toString();
    }
}
