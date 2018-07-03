package ru.bogdanov.moneychecker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ru.bogdanov.moneychecker.model.items.SmsItem;
import ru.bogdanov.moneychecker.viewmodel.SmsAdapter;

public class DayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SmsAdapter smsAdapter=new SmsAdapter();
        ArrayList<SmsItem> list=(ArrayList<SmsItem>) getIntent().getSerializableExtra("list");
        smsAdapter.setList(list);
        recyclerView.setAdapter(smsAdapter);
    }
}
