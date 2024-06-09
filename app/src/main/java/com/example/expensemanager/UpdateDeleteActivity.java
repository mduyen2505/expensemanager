package com.example.expensemanager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensemanager.adapter.RecycleViewAdapter.Item;
import com.example.expensemanager.dal.SQLiteHelper;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner sp;
    private EditText Title, Price, Date;
    private Button btUpdate, btBack, btRemove;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_delete);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        btUpdate.setOnClickListener(this);
        btBack.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        Date.setOnClickListener(this);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");  // Ép kiểu về Item
        Title.setText(item.getTitle());
        Price.setText(item.getPrice());
        Date.setText(item.getDate());
        int p = 0;
        for (int i = 0; i < sp.getCount(); i++) {

            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())) {
                p = i;
                break;
            }
        }
        sp.setSelection(p);
    }





    private void initView() {
        sp = findViewById(R.id.SpinderCategory);
        Title = findViewById(R.id.Title);
        Price = findViewById(R.id.Price);
        Date = findViewById(R.id.Date);
        btUpdate = findViewById(R.id.btUpdate);
        btBack = findViewById(R.id.btBack);
        btRemove = findViewById(R.id.btRemove);

        sp.setAdapter(new ArrayAdapter<>(this, R.layout.itemspinder, getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View view) {
        SQLiteHelper db = new SQLiteHelper(this);
        if (view == Date) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, (datePicker, year1, month1, dayOfMonth) -> {
                String date = (dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth) + "/" +
                        ((month1 + 1) > 9 ? (month1 + 1) : "0" + (month1 + 1)) + "/" + year1;
                Date.setText(date);
            }, year, month, day);
            dialog.show();
        } else if (view == btBack) {
            finish();
        } else if (view == btUpdate) {
            String t = Title.getText().toString();
            String p = Price.getText().toString();
            String c = sp.getSelectedItem().toString();
            String d = Date.getText().toString();
            if (!t.isEmpty() && p.matches("\\d+")) {
                int id=item.getId();
                Item i =new Item(id,t,c,p,d);
                db=new SQLiteHelper(this);
                db.update(i);
                finish();
            }
        } else if (view == btRemove) {
            int id = item.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thông báo xóa");
            builder.setMessage("Bạn có chắc muốn xóa " + item.getTitle() + " không?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SQLiteHelper bb=new SQLiteHelper(getApplicationContext());
                    bb.delete(id);
                    finish();
                }


            });
            builder.setNegativeButton("Không", (dialog, which) -> {
                // Do nothing
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
