package ru.natiel.week1homework.screens.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ru.natiel.week1homework.R;
import ru.natiel.week1homework.screens.second.SecondActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnMainNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivity = new Intent(getApplicationContext(), SecondActivity.class);
                secondActivity.putExtra("Title", "SecondActivity");
                startActivity(secondActivity);
            }
        });

        findViewById(R.id.btnMainNewPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemActivity = new Intent(getApplicationContext(), ChargeListActivity.class);
                addItemActivity.putExtra("Title", "Go to AddItemActivity");
                startActivity(addItemActivity);
            }
        });
    }

}
