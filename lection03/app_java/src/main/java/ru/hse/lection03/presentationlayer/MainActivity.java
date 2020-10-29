package ru.hse.lection03.presentationlayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.hse.lection03.R;

public class MainActivity extends AppCompatActivity implements DroidListFragment.IListener {
    protected static final String TAG_DETAILS = "DETAILS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
        DroidListFragment initFragment = new DroidListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.data, initFragment).addToBackStack(null).commit();}
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onDroidClicked(Short number) {
        showDetails(number);
    }

    protected void showDetails(Short number) {
        if (number == null) {
            return;
        }

        final DroidDetailsFragment detailsFragment = DroidDetailsFragment.newInstance(number);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.data, detailsFragment, TAG_DETAILS)
                .addToBackStack(null)
                .commit();
    }

}
