package ru.natiel.week1homework.screens.second;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import ru.natiel.week1homework.R;
import ru.natiel.week1homework.screens.main.adapter.ChargeModel;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final EditText textName = findViewById(R.id.textSecondName);
        final EditText textValue = findViewById(R.id.textSecondValue);
        Button buttonAdd = findViewById(R.id.btnSecondEnter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textName.getText().toString();
                String value = textValue.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
                    return;
                }
                ChargeModel chargeModel = new ChargeModel(name, value + " P");
                Intent intent = new Intent();
                intent.putExtra(ChargeModel.KEY_NAME, chargeModel);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public static Intent startIntent(Context context){
        return  new Intent(context, SecondActivity.class);
    }
}
