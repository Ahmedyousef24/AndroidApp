package com.example.aalift;

import android.util.Log;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.ResponseListener;

public class SearchData implements ResponseListener {
    @Override
    public void onFoodListRespone(Response<CompactFood> foods) {
        Log.d("Res",""+foods.getTotalResults());
    }

    @Override
    public void onFoodResponse(Food food) {
        Log.d("Res",""+food.getName());
    }
}
