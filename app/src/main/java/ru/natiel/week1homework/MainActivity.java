package ru.natiel.week1homework;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.natiel.week1homework.api.Api;
import ru.natiel.week1homework.api.WebService;

public class MainActivity extends AppCompatActivity {

    public static final String TOKEN = "token";
    private static final String USER_ID = "zfadeev";

    private WebService webService;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new BudgetPagerAdapter(getSupportFragmentManager(),
                            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText(R.string.expences);
        tabLayout.getTabAt(1).setText(R.string.income);

        webService = WebService.getInstance();
        api = webService.getApi();

        final String token = PreferenceManager.getDefaultSharedPreferences(this).getString(TOKEN, "");
        if (TextUtils.isEmpty(token)) {
            Call<Status> auth = api.auth(USER_ID);
            auth.enqueue(new Callback<Status>() {

                @Override
                public void onResponse(
                        final Call<Status> call, final Response<Status> response
                ) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                            MainActivity.this).edit();
                    editor.putString(TOKEN, response.body().getToken());
                    editor.apply();

                    for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                        if (fragment instanceof BudgetFragment) {
                            ((BudgetFragment)fragment).loadItems();
                        }
                    }
                }

                @Override
                public void onFailure(final Call<Status> call, final Throwable t) {

                }
            });
        }
    }

    static class BudgetPagerAdapter extends FragmentPagerAdapter {

        public BudgetPagerAdapter(@NonNull final FragmentManager fm, final int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(final int position) {
            return new BudgetFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
