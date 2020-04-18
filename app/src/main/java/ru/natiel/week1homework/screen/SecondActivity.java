package ru.natiel.week1homework.screen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import org.jetbrains.annotations.NotNull;
import ru.natiel.week1homework.R;
import ru.natiel.week1homework.api.Api;
import ru.natiel.week1homework.api.WebService;
import ru.natiel.week1homework.model.AuthResponse;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private String fragmentType;
    private List<Disposable> disposables = new ArrayList<>();
    private WebService webService;
    private Api api;
    private EditText nameEditText;
    private EditText priceEditText;
    private Button addButton;
    private String nameText;
    private String priceText;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if(getIntent().getExtras()!= null){
            int fragmentId = getIntent().getExtras().getInt("fragmentId");
            if (fragmentId == 0)
                fragmentType = MainActivity.EXPENSE;
            else
                fragmentType = MainActivity.INCOME;
            Log.e("TAG", "Fragment Id is " + fragmentId);
        }

        nameEditText = findViewById(R.id.name_edittext);
        nameEditText.addTextChangedListener(getWatcher(true));
        priceEditText = findViewById(R.id.price_edittext);
        priceEditText.addTextChangedListener(getWatcher(false));

        webService = WebService.getInstance();
        api = webService.getApi();

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                if (!TextUtils.isEmpty(nameText) && !TextUtils.isEmpty(priceText)) {
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

        Disposable request = api.request(fragmentType, name,
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

    @NotNull
    private TextWatcher getWatcher(final boolean isName) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(isName) nameText = s.toString();
                else priceText = s.toString();
                checkEditTextHasText();
            }
        };
    }

    public void checkEditTextHasText(){
        addButton.setEnabled(!TextUtils.isEmpty(nameText) && !TextUtils.isEmpty(priceText));
    }
}
