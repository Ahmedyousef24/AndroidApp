package com.example.aalift;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeFragment extends Fragment {


    private Button btn;
    private RecyclerView recycleBreakfast, recycleLunch, recycleDinner, recycleSnack;
    private ArrayList<Nutrition> breakfastList, lunchList, dinnerList, snackList;
    private NutritionAdapter breakfastAdapter, lunchAdpater, dinnerAdapter, snackAdapter;
    private Button breakfastButton, luncButton, dinnerButton, snackButton;
    public static TextView textGoal, totalText;
    private ArrayList<String> breakfastIdList, lunchIdList, dinnerIdList, snackIdList;
    private final String breakfastTag = "breakfast";
    private final String lunchTag = "lunch";
    private final String dinnerTag = "dinner";
    private final String snackTag = "snack";
    private final String meal = "meal";
    private NutritionData nutritionData;
    private Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recycleBreakfast = (RecyclerView) v.findViewById(R.id.recycleBreakfast);
        recycleLunch = (RecyclerView) v.findViewById(R.id.recycleLunch);
        recycleDinner = (RecyclerView) v.findViewById(R.id.recycleDinner);
        recycleSnack = (RecyclerView) v.findViewById(R.id.recycleSnack);
        totalText = v.findViewById(R.id.textResult);
        textGoal = v.findViewById(R.id.textGoal);
        breakfastButton = v.findViewById(R.id.breakfastBtn);
        luncButton = v.findViewById(R.id.lunchBtn);
        dinnerButton = v.findViewById(R.id.dinnerBtn);
        snackButton = v.findViewById(R.id.snackBtn);

        nutritionData = new NutritionData();
        nutritionData.execute();

        breakfastList = new ArrayList<>();
        lunchList = new ArrayList<>();
        dinnerList = new ArrayList<>();
        snackList = new ArrayList<>();

        breakfastIdList = new ArrayList<>();
        lunchIdList = new ArrayList<>();
        dinnerIdList = new ArrayList<>();
        snackIdList = new ArrayList<>();


        breakfastAdapter = new NutritionAdapter(getContext(), breakfastList, breakfastTag);
        lunchAdpater = new NutritionAdapter(getContext(), lunchList, lunchTag);
        dinnerAdapter = new NutritionAdapter(getContext(), dinnerList, dinnerTag);
        snackAdapter = new NutritionAdapter(getContext(), snackList, snackTag);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        recycleSnack.setLayoutManager(linearLayoutManager);
        recycleDinner.setLayoutManager(linearLayoutManager1);
        recycleLunch.setLayoutManager(linearLayoutManager2);
        recycleBreakfast.setLayoutManager(linearLayoutManager3);


        recycleSnack.setAdapter(snackAdapter);
        recycleDinner.setAdapter(dinnerAdapter);
        recycleLunch.setAdapter(lunchAdpater);
        recycleBreakfast.setAdapter(breakfastAdapter);

        GoalData.getTdee(new GoalData.TdeeCallBack() {
            @Override
            public void onTdeeReady(int tdee) {
                HomeFragment.textGoal.setText(Integer.toString(tdee));
            }
        });

        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra(meal, breakfastTag);
                startActivity(intent);
            }
        });
        luncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra(meal, lunchTag);
                startActivity(intent);
            }
        });
        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra(meal, dinnerTag);
                startActivity(intent);
            }
        });
        snackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtra(meal, snackTag);
                startActivity(intent);
            }
        });
        getList();

        intent = new Intent(getActivity(), NutritionActivity.class);

        breakfastAdapter.itemClicked(new NutritionAdapter.clickListener() {
            @Override
            public void onClicked(int position) {


                nutritionData.getProduct(breakfastTag, breakfastIdList.get(position), new NutritionData.NutritionCallBack() {
                    @Override
                    public void OnNutritionListReady(ArrayList<Nutrition> nutritionList) {
                        intent.putExtra("nutritionList", nutritionList);
                        startActivity(intent);
                    }
                });

            }
        });
        lunchAdpater.itemClicked(new NutritionAdapter.clickListener() {
            @Override
            public void onClicked(int position) {


                nutritionData.getProduct(lunchTag, lunchIdList.get(position), new NutritionData.NutritionCallBack() {
                    @Override
                    public void OnNutritionListReady(ArrayList<Nutrition> nutritionList) {
                        intent.putExtra("nutritionList", nutritionList);
                        startActivity(intent);
                    }
                });

            }
        });
        dinnerAdapter.itemClicked(new NutritionAdapter.clickListener() {
            @Override
            public void onClicked(int position) {


                nutritionData.getProduct(dinnerTag, dinnerIdList.get(position), new NutritionData.NutritionCallBack() {
                    @Override
                    public void OnNutritionListReady(ArrayList<Nutrition> nutritionList) {
                        intent.putExtra("nutritionList", nutritionList);
                        startActivity(intent);
                    }
                });

            }
        });
        snackAdapter.itemClicked(new NutritionAdapter.clickListener() {
            @Override
            public void onClicked(int position) {


                nutritionData.getProduct(snackTag, snackIdList.get(position), new NutritionData.NutritionCallBack() {
                    @Override
                    public void OnNutritionListReady(ArrayList<Nutrition> nutritionList) {
                        intent.putExtra("nutritionList", nutritionList);
                        startActivity(intent);
                    }
                });

            }
        });

        ItemTouchHelper breakfastHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                nutritionData.remove(breakfastIdList.get(position), breakfastTag);
                breakfastList.remove(position);
                breakfastIdList.remove(position);
                breakfastAdapter.notifyDataSetChanged();
            }
        });
        breakfastHelper.attachToRecyclerView(recycleBreakfast);

        ItemTouchHelper lunchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                nutritionData.remove(lunchIdList.get(position), lunchTag);
                lunchList.remove(position);
                lunchIdList.remove(position);
                lunchAdpater.notifyDataSetChanged();
            }
        });
        lunchHelper.attachToRecyclerView(recycleLunch);

        ItemTouchHelper dinnerHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                nutritionData.remove(dinnerIdList.get(position), dinnerTag);
                dinnerList.remove(position);
                dinnerIdList.remove(position);
                dinnerAdapter.notifyDataSetChanged();
            }
        });
        dinnerHelper.attachToRecyclerView(recycleDinner);

        ItemTouchHelper snackHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                nutritionData.remove(snackIdList.get(position), snackTag);
                snackList.remove(position);
                snackIdList.remove(position);
                snackAdapter.notifyDataSetChanged();
            }
        });
        snackHelper.attachToRecyclerView(recycleSnack);
        return v;
    }


    public void getList() {
        nutritionData.setCustomListener(new NutritionData.FirebaseCallBack() {
            @Override
            public void OnBreakfastListReady(ArrayList<Nutrition> list, ArrayList<String> keyList) {
                if (breakfastList != null) {
                    breakfastList.clear();
                    breakfastIdList.clear();
                }
                breakfastList.addAll(list);
                breakfastIdList.addAll(keyList);
                breakfastAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnDinnerListReady(ArrayList<Nutrition> list, ArrayList<String> keyList) {
                if (dinnerList != null) {
                    dinnerList.clear();
                    dinnerIdList.clear();
                }

                dinnerList.addAll(list);
                dinnerIdList.addAll(keyList);
                dinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnSnackListReady(ArrayList<Nutrition> list, ArrayList<String> keyList) {
                if (snackList != null) {
                    snackList.clear();
                    snackIdList.clear();
                }
                snackIdList.addAll(keyList);
                snackList.addAll(list);
                snackAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnLunchListReady(ArrayList<Nutrition> list, ArrayList<String> keyList) {
                if (lunchList != null) {
                    lunchList.clear();
                    lunchIdList.clear();
                }
                lunchList.addAll(list);
                lunchIdList.addAll(keyList);
                lunchAdpater.notifyDataSetChanged();
            }

        });
    }
}
