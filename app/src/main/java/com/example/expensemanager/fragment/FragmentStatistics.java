package com.example.expensemanager.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.UpdateDeleteActivity;
import com.example.expensemanager.adapter.RecycleViewAdapter;
import com.example.expensemanager.adapter.RecycleViewAdapter.Item;
import com.example.expensemanager.dal.SQLiteHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentStatistics extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;
    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        pieChart = view.findViewById(R.id.pieChart);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Item> list = db.getAll();
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        setupPieChart(list);
    }

    private void setupPieChart(List<Item> itemList) {
        List<PieEntry> entries = new ArrayList<>();
        for (Item item : itemList) {
            float price = Float.parseFloat(item.getPrice());
            entries.add(new PieEntry(price, item.getCategory()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Categories");
        dataSet.setColors
                (new int[]{Color.GRAY, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA});
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        Description description = new Description();
        description.setText("Expense Distribution");
        pieChart.setDescription(description);
        pieChart.invalidate(); // refresh
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("Item", (Serializable) item); // Truyền đối tượng Item một cách đúng đắn
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Item> list = db.getAll();
        adapter.setList(list);
        setupPieChart(list);
    }
}
