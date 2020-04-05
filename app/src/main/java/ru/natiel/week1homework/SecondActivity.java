package ru.natiel.week1homework;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.natiel.week1homework.api.Api;
import ru.natiel.week1homework.api.WebService;
import ru.natiel.week1homework.models.AuthResponse;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private List<Disposable> disposables = new ArrayList<>();
    private WebService webService;
    private Api api;
    private EditText nameEditText;
    private EditText priceEditText;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        nameEditText = findViewById(R.id.name_edittext);
        priceEditText = findViewById(R.id.price_edittext);

        webService = WebService.getInstance();
        api = webService.getApi();

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                String name = nameEditText.getText().toString();
                String price = priceEditText.getText().toString();

                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)) {
                    sendNewExpense(Integer.valueOf(priceEditText.getText().toString()),
                            nameEditText.getText().toString());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        for (Disposable disposable : disposables) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    private void sendNewExpense(Integer price, String name) {
        nameEditText.setEnabled(false);
        priceEditText.setEnabled(false);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String authToken = sharedPreferences.getString(AuthResponse.AUTH_TOKEN_KEY, "");

        Disposable request = api.request("expense", name,
                price, authToken)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Toast.makeText(getApplicationContext(), getString(R.string.message_success), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getApplicationContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        nameEditText.setEnabled(false);
                        priceEditText.setEnabled(false);
                    }
                });

        disposables.add(request);
    }
}
