package com.example.expensemanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.UpdateDeleteActivity;
import com.example.expensemanager.adapter.RecycleViewAdapter;
import com.example.expensemanager.dal.SQLiteHelper;
import com.example.expensemanager.adapter.RecycleViewAdapter.Item;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;
    private TextView tong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        tong = view.findViewById(R.id.tong);
        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());

        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        List<Item> list = db.getByDate(f.format(d));
        adapter.setList(list);
        tong.setText("Tong tien: " + tong(list));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
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

    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", (CharSequence) item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        List<Item> list = db.getByDate(f.format(d));
        adapter.setList(list);
        tong.setText("Tong tien: " + tong(list));
    }
}
