package com.example.expensemanager;

import android.app.DatePickerDialog;
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

import com.example.expensemanager.adapter.RecycleViewAdapter;
import com.example.expensemanager.dal.SQLiteHelper;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner sp;
    private EditText Title, Price, Date;
    private Button btUpdate, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        initView();
        btUpdate.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        Date.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initView() {
        sp = findViewById(R.id.SpinderCategory);
        Title = findViewById(R.id.Title);
        Price = findViewById(R.id.Price);
        Date = findViewById(R.id.Date);
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCancel);
        sp.setAdapter(new ArrayAdapter<>(this, R.layout.itemspinder, getResources().getStringArray(R.array.category)));
    }

    @Override
    public void onClick(View view) {
        if (view == Date) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, (datePicker, year1, month1, dayOfMonth) -> {
                String date = (dayOfMonth > 9 ? dayOfMonth : "0" + dayOfMonth) + "/" +
                        ((month1 + 1) > 9 ? (month1 + 1) : "0" + (month1 + 1)) + "/" + year1;
                Date.setText(date);
            }, year, month, day);
            dialog.show();
        }
        if (view == btCancel) {
            finish();
        }
        if (view == btUpdate) {
            String t = Title.getText().toString();
            String p = Price.getText().toString();
            String c = sp.getSelectedItem().toString();
            String d = Date.getText().toString();
            if (!t.isEmpty() && p.matches("\\d+")) {
                RecycleViewAdapter.Item i = new RecycleViewAdapter.Item(0, t, c, p, d);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addItem(i);
                finish();
                };

            }
        }

}
