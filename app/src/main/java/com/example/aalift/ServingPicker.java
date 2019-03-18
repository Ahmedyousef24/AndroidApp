package com.example.aalift;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.ListPreference;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ServingPicker extends DialogFragment {

    private ArrayList<Nutrition>list;
    private NumberPicker numberPicker;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout linearLayout = new LinearLayout(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
         numberPicker = new NumberPicker(getActivity());

        linearLayout.setOrientation(LinearLayout.HORIZONTAL);


        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(500);
        numberPicker.setValue(100);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50,50);
        params.gravity = Gravity.CENTER;
        final LinearLayout.LayoutParams numPickerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        numPickerParams.weight = 1;
        linearLayout.addView(numberPicker,numPickerParams);
        list = new ArrayList<>();
        builder.setTitle(R.string.serving_size)
                .setPositiveButton("set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("set", ""+NutritionActivity.getFoodList().size());
                        NutritionActivity.textViewValue.setText(Integer.toString(numberPicker.getValue())+ getContext().getString(R.string.gram));
                        calculateNewServingSize(numberPicker.getValue());
                    }
                });
        builder.setView(linearLayout);

        return builder.create();


    }

    private void calculateNewServingSize(int servingSize){

        list.addAll(NutritionActivity.getFoodList());
        ArrayList foodList = new ArrayList<>();
        float  oldServingSize = list.get(0).getValue();
        foodList.add(new Nutrition(list.get(0).getName(),numberPicker.getValue()));
        for(int i= 1; i<list.size(); i++ ){
            Nutrition nutrition = new Nutrition(list.get(i).getName(),list.get(i).getValue());
            nutrition.setValue(newValue(nutrition.getValue(), numberPicker.getValue(),oldServingSize));
            foodList.add(nutrition);

        }
        NutritionActivity.updateData(foodList);

    }
    private float newValue(float beforeValue, int servingSize, float oldServingSize){

        float perGram = beforeValue/ oldServingSize;
        float newValue = perGram* servingSize;
        Log.d("serving", ""+newValue);
        return newValue;

    }
}
