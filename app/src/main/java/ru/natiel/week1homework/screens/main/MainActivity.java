package ru.natiel.week1homework.screens.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.natiel.week1homework.R;
import ru.natiel.week1homework.screens.main.adapter.ChargeModel;
import ru.natiel.week1homework.screens.main.adapter.ChargesAdapter;
import ru.natiel.week1homework.screens.second.SecondActivity;

public class MainActivity extends AppCompatActivity {

    private ChargesAdapter chargesAdapter = new ChargesAdapter();
    public static final int ADD_ITEM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerMain);
        recyclerView.setAdapter(chargesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(  getApplicationContext(),
                                                                LinearLayoutManager.VERTICAL,
                                                                false));

        final Intent secondIntent = SecondActivity.startIntent(this);
        findViewById(R.id.fabMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(secondIntent, ADD_ITEM_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ITEM_REQUEST && resultCode == RESULT_OK && data != null){
            ChargeModel chargeModel = (ChargeModel)data.getSerializableExtra(ChargeModel.KEY_NAME);
            chargesAdapter.addDataToTop(chargeModel);
        }
    }
}
