package ru.natiel.week1homework.screens.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import ru.natiel.week1homework.screens.main.MainActivity;
import ru.natiel.week1homework.screens.main.fragment.BudgetFragment;

public class BudgetPagerAdapter extends FragmentPagerAdapter {
    private MainActivity mainActivity;

    public BudgetPagerAdapter(FragmentManager fm, MainActivity mainActivity) {
        super(fm);
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public Fragment getItem(final int position) {
        return new BudgetFragment(mainActivity);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
