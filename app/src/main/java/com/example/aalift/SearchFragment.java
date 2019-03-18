package com.example.aalift;


import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchFragment extends Fragment {

    public static Button scanBtn;
    public static TextView textView;
    private ScanCodeActivity scanCodeActivity;
    private HomeActivity homeActivity;
    private SearchView searchView;
    private ArrayList<Nutrition> list;
    private ArrayList<String>itemList;
    private NutritionAdapter adapter;
    private RecyclerView recyclerView;
    private RequestQueue mQueue;
    private String tag = "search";
    private ArrayList<Nutrition>detailList;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        homeActivity = new HomeActivity();
        scanBtn = v.findViewById(R.id.scanBtn);
        recyclerView = v.findViewById(R.id.searchList);
        searchView = v.findViewById(R.id.searchBar);
        list = new ArrayList<>();
        itemList = new ArrayList<>();
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        adapter = new NutritionAdapter(getContext(),list,tag);

        detailList = new ArrayList<>();

        mQueue = Volley.newRequestQueue(getContext());
        scanBtn.setOnClickListener(new View.OnClickListener() {


            // @Override
            public void onClick(View v) {
                Intent homeintent = new Intent(getContext(), ScanCodeActivity.class);
                Log.d("foo", "creating..");
                startActivity(homeintent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d("SearchBar", query);
                searchProduct(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchProduct(newText);
                Log.d("SearchBar", newText);
                return false;
            }
        });

        adapter.itemClicked(new NutritionAdapter.clickListener() {
            @Override
            public void onClicked(int position) {
                Log.d("clickedon","pos"
                +position);
                viewItem(position);

            }
        });
       recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(adapter);
        return v;
    }

    private void viewItem(int position) {

        String id = itemList.get(position);
        String url = "https://trackapi.nutritionix.com/v2/search/item?nix_item_id="+id;
        Log.d("id",""+id);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                JSONArray array = response.getJSONArray("foods");
                for(int i = 0; i<array.length(); i++){
                    JSONObject food = array.getJSONObject(i);
                    createList(food);

                }
                for(Nutrition nutrition : detailList){
                    Log.d("detail", ""+nutrition.getValue());
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*  Todo startActivity*/
                Intent intent = new Intent(getContext(), NutritionActivity.class);
                Log.d("foo", "creating.."+detailList.size());
                intent.putExtra("nutritionList", detailList);
                startActivity(intent);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("x-app-id","e30f888c");
                params.put("x-app-key","dd0973ec76cdb419230f46c076edaf0b");
                params.put("x-remote-user-id","0");
                return params;
            }
        };

        Log.d("list",""+detailList.size());
        mQueue.add(request);
    }

    private void createList(JSONObject jsonObject){

        if(jsonObject.has("food_name")){
            try {
                detailList.add(new Nutrition(jsonObject.getString("food_name"),100));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("nf_calories")){
            try {
                detailList.add(new Nutrition("Energy",(float)jsonObject.getDouble("nf_calories")));
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        if(jsonObject.has("nf_total_fat")){
            try {

                detailList.add(new Nutrition("Fat",(float)jsonObject.getDouble("nf_total_fat")));
            } catch (JSONException e) {
                e.printStackTrace();
                detailList.add(new Nutrition("Fat",0));
            }
        }
        if(jsonObject.has("nf_saturated_fat")){
            try {
                detailList.add(new Nutrition("Saturated fat",(float)jsonObject.getDouble("nf_saturated_fat")));
            } catch (JSONException e) {
                e.printStackTrace();
                detailList.add(new Nutrition("Saturated fat",0));
            }
        }
        if(jsonObject.has("nf_total_carbohydrate")){
            try {
                detailList.add(new Nutrition("Carbohydrate",(float)jsonObject.getDouble("nf_total_carbohydrate")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(jsonObject.has("nf_dietary_fiber")){
            try {
                detailList.add(new Nutrition("Fiber",(float)jsonObject.getLong("nf_dietary_fiber")));
            } catch (JSONException e) {
                e.printStackTrace();
                detailList.add(new Nutrition("Fiber",0));
            }
        }
        if(jsonObject.has("nf_sugars")){
            try {
                detailList.add(new Nutrition("Sugars",(float)jsonObject.getLong("nf_sugars")));
            } catch (JSONException e) {
                e.printStackTrace();
                detailList.add(new Nutrition("Sugars",0));
            }
        }
        if(jsonObject.has("nf_protein")){
            try {
                detailList.add(new Nutrition("Protein", (float)jsonObject.getDouble("nf_protein")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("detaillist", ""+detailList.get(0).getName());
    }
    private void searchProduct(final String item) {
        itemList.clear();
        list.clear();
        String url = "https://trackapi.nutritionix.com/v2/search/instant?query=" + item;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList tempList = new ArrayList<Nutrition>();
                            JSONArray jsonArray = response.getJSONArray("branded");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject food = jsonArray.getJSONObject(i);
                                String foodName = food.getString("food_name");
                                float calories = (float) food.getDouble("nf_calories");
                                String id = food.getString("nix_item_id");
                                itemList.add(id);
                                tempList.add(new Nutrition(foodName,calories));
                            }
                            list.addAll(tempList);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            list.clear();
                            adapter.notifyDataSetChanged();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                list.clear();
                adapter.notifyDataSetChanged();
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("x-app-id","e30f888c");
                params.put("x-app-key","dd0973ec76cdb419230f46c076edaf0b");
                params.put("x-remote-user-id","0");
                return params;
            }
        };
        mQueue.add(request);
    }

    @Override
    public void onPause() {
        super.onPause();
        detailList.clear();

    }
}
