package com.example.aalift;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;


public class WeightPicker extends DialogFragment {
    private  NumberPicker.OnValueChangeListener valueChangeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TextView textView = new TextView(getActivity());

        textView.setText(",");
        textView.setTextSize(40);
        textView.setPadding(2,60,2,10);
        textView.setGravity(Gravity.CENTER);

        final NumberPicker numberPicker = new NumberPicker(getActivity());
        final NumberPicker numberPicker2 = new NumberPicker(getActivity());

        numberPicker.setMaxValue(500);
        numberPicker.setMinValue(0);
        numberPicker2.setMaxValue(99);
        numberPicker2.setMinValue(0);

       /*TODO
       CHANGE SETVALUE TO LATEST VALUE FROM FIREBASE
        */
        numberPicker.setValue(70);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50,50);
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams numPickerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        numPickerParams.weight = 1;
        linearLayout.addView(numberPicker,numPickerParams);
        linearLayout.addView(textView);
        linearLayout.addView(numberPicker2,numPickerParams);

        builder.setTitle(R.string.set_weight)
                .setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // get the value and pass it to a function maybe
                        ProgressFragment.NewWeight(numberPicker.getValue(),numberPicker2.getValue());
                    }
                });

        builder.setView(linearLayout);
        return builder.create();

    }

}