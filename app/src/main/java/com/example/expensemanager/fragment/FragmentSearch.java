package com.example.expensemanager.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.adapter.RecycleViewAdapter;
import com.example.expensemanager.dal.SQLiteHelper;
import com.example.expensemanager.model.Item;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private TextView tvTong;
    private Button btSearch;
    private SearchView searchView;
    private EditText efrom, eto;
    private Spinner spCategory;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Item> list = db.getAll();
        adapter.setList(list);
        tvTong.setText("Tổng tiền: " + tong(list) + "K");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Item> list = db.searchByTitle(newText);
                tvTong.setText("Tổng tiền: " + tong(list));
                adapter.setList(list);
                return true;
            }
        });

        efrom.setOnClickListener(this);
        eto.setOnClickListener(this);
        btSearch.setOnClickListener(this);

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cate = spCategory.getItemAtPosition(position).toString();
                List<Item> list;
                if (!cate.equalsIgnoreCase("all")) {
                    list = db.searchByCategory(cate);
                } else {
                    list = db.getAll();
                }
                adapter.setList(list);
                tvTong.setText("Tổng tiền: " + tong(list));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private int tong(List<Item> list) {
        int t = 0;
        for (Item i : list) {
            try {
                t += Integer.parseInt(i.getPrice());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tong);
        btSearch = view.findViewById(R.id.btnSearch);
        searchView = view.findViewById(R.id.search);
        efrom = view.findViewById(R.id.from);
        eto = view.findViewById(R.id.to);
        spCategory = view.findViewById(R.id.spCategory);
        String[] arr = getResources().getStringArray(R.array.category);
        String[] arr1 = new String[arr.length + 1];
        arr1[0] = "All";
        for (int i = 0; i < arr.length; i++) {
            arr1[i + 1] = arr[i];
        }
        spCategory.setAdapter(new ArrayAdapter<>(getContext(), R.layout.itemspinder, arr1));
    }

    @Override
    public void onClick(View v) {
        if (v == efrom) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (datePicker, year1, month1, dayOfMonth) -> {
                String date = (dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth) + "/" +
                        ((month1 + 1) > 9 ? (month1 + 1) : "0" + (month1 + 1)) + "/" + year1;
                efrom.setText(date);
            }, year, month, day);
            dialog.show();
        }

        if (v == eto) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), (datePicker, year1, month1, dayOfMonth) -> {
                String date = (dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth) + "/" +
                        ((month1 + 1) > 9 ? (month1 + 1) : "0" + (month1 + 1)) + "/" + year1;
                eto.setText(date);
            }, year, month, day);
            dialog.show();
        }

        if (v == btSearch) {
            String from = efrom.getText().toString();
            String to = eto.getText().toString();
            if (!from.isEmpty() && !to.isEmpty()) {
                List<Item> list = db.searchByDateFromTo(from, to);
                adapter.setList(list);
                tvTong.setText("Tổng tiền: " + tong(list));
            }
        }
    }
}
