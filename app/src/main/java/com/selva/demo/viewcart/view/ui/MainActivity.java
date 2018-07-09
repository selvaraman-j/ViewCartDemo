package com.selva.demo.viewcart.view.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.selva.demo.viewcart.R;

/**
 * @author selva.raman
 * @version 1.0
 * @since 6/28/2018
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (null == savedInstanceState) {
            //Attach the cart fragment into the activity
            getSupportFragmentManager().beginTransaction().add(
                    R.id.container, CartFragment.getInstance()).commit();
        }
    }
}
