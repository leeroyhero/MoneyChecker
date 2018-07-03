package ru.bogdanov.moneychecker;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.bogdanov.moneychecker.databinding.ActivityMainBinding;
import ru.bogdanov.moneychecker.viewmodel.TopPresenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        TopPresenter topPresenter=new TopPresenter();
        topPresenter.getFromShPref(this);
        binding.setTopPresenter(topPresenter);



    }
}
