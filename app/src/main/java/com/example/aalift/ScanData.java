package com.example.aalift;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ScanData extends AsyncTask<Void, Void, Void> {


    private String url;
    private String data = "";
    private String dataParsed = "";

    private static CallBack listener;
    private Boolean flag = false;

    public ScanData(){
        this.listener = null;
    }


    public static ArrayList<Nutrition> getFoodList() {
        return foodList;
    }

    private static ArrayList<Nutrition> foodList;
    public String getUrl() {
        return url;
    }



    public static void setCustomListener(CallBack callBack){
        listener = callBack;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {

            String theUrl = url;
            URL url = new URL(theUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            foodList  = new ArrayList<>();

            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }


            JSONObject jsonObject = new JSONObject(data);
            JSONObject product = jsonObject.getJSONObject("product");
            String productName = product.getString("product_name");
            float theSize = 100;
            foodList.add(new Nutrition(productName, theSize));


          //  JSONArray jsonArray = product.getJSONArray("nutriments");


         //   for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jOb = product.getJSONObject("nutriments");
                foodList.add(new Nutrition("Energy", KjToKcal(jOb.getString("energy_100g"))));
                foodList.add(new Nutrition("Fat",ParseFloat(jOb.getString("fat_value"))));
                foodList.add(new Nutrition("Saturated fat",ParseFloat(jOb.getString("saturated-fat"))));
                foodList.add(new Nutrition("Carbohydrate",ParseFloat(jOb.getString("carbohydrates"))));
                foodList.add(new Nutrition("Sugars",ParseFloat(jOb.getString("sugars_100g"))));
                foodList.add(new Nutrition("Fiber",ParseFloat(jOb.getString("fiber"))));
                foodList.add(new Nutrition("Protein",ParseFloat(jOb.getString("proteins"))));
                foodList.add(new Nutrition("Salt",ParseFloat(jOb.getString("salt_value"))));
                foodList.add(new Nutrition("Sodium",ParseFloat(jOb.getString("sodium_100g"))));
           // }

               Log.d("foodlist","name"+foodList.size() );


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        flag = true;
        if(listener !=null){
            Log.d("foodlist","ready ");
            listener.onDataLoaded(foodList);
            listener.onDataReady(flag);
        }

    }


    private float ParseFloat(String value) {
        float f = Float.parseFloat(value);
        return f;
    }

    private float KjToKcal(String value) {
        float f = ParseFloat(value);
        float kcal = f / 4.184f;
        return kcal;
    }

    public  interface CallBack{
        void onDataLoaded(ArrayList<Nutrition>list);
        void onDataReady(Boolean flag);
    }


}
