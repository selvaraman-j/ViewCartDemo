package com.selva.demo.viewcart.repository.model;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BindingAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.selva.demo.viewcart.R;
import com.selva.demo.viewcart.view.ui.MainActivity;
import com.selva.demo.viewcart.viewmodel.ViewCartViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * @author selva.raman
 * @version 1.0
 * @since 6/29/2018
 */

public class ViewCartModel {

    public String itemName;
    public String itemDescription;
    public String itemDelivery;
    public String itemImage;
    String itemQuantity;
    public String itemPrice;
    private static boolean isTouched;

    /**
     * Constructor for assigning the cart view values
     *
     * @param viewCartResponse the ViewCartResponse
     */
    public ViewCartModel(ViewCartResponse viewCartResponse) {
        this.itemName = viewCartResponse.getTitle();
        this.itemDescription = viewCartResponse.getDescription();
        this.itemDelivery = viewCartResponse.getDelivery();
        this.itemImage = viewCartResponse.getImage();
        this.itemQuantity = viewCartResponse.getQuantity();
        this.itemPrice = viewCartResponse.getPrice();
    }

    /**
     * create and returns the list of view cart model from view cart response
     *
     * @param viewCartResponseList the List<ViewCartResponse>
     * @return List<ViewCartModel>
     */
    public static List<ViewCartModel> getViewCartModelList(List<ViewCartResponse> viewCartResponseList) {
        List<ViewCartModel> viewCartModelList = new ArrayList<>();
        for (ViewCartResponse viewCartResponse : viewCartResponseList) {
            viewCartModelList.add(new ViewCartModel(viewCartResponse));
        }
        return viewCartModelList;
    }

    /**
     * Updates the cart view image using picasso library
     *
     * @param imageView the ImageView
     * @param imageUrl  String, the image url
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    /**
     * Updates the item price for each cart list item
     *
     * @param textView the TextView
     * @param price    String, the price
     */
    @BindingAdapter({"itemPrice"})
    public static void setItemPrice(TextView textView, String price) {
        textView.setText(textView.getContext().getString(R.string.price, price));
    }

    /**
     * Sets the quantity adapter for each cart list item
     *
     * @param spinner       the Spinner view
     * @param viewCartModel the ViewCartModel
     */
    @BindingAdapter({"quantity"})
    public static void setQuantitySpinner(final Spinner spinner, final ViewCartModel viewCartModel) {
        final String[] quantityArray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(spinner.getContext()
                , android.R.layout.simple_spinner_dropdown_item, quantityArray);
        spinner.setAdapter(quantityAdapter);
        int selectedPosition;

        try {
            selectedPosition = Integer.parseInt(viewCartModel.itemQuantity);
            //array index starts with zero
            selectedPosition--;
        } catch (NumberFormatException e) {
            selectedPosition = 0;
        }

        spinner.setSelection(selectedPosition);

        spinner.setOnTouchListener((View v, MotionEvent event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
            }
            return false;
        });

        //spinner on item selection listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isTouched) {
                    viewCartModel.itemQuantity = quantityArray[position];
                    ViewCartViewModel cartViewModel = ViewModelProviders.of(
                            (MainActivity) view.getContext()).get(ViewCartViewModel.class);
                    cartViewModel.updateViewCart(viewCartModel);
                    isTouched = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing selected
            }
        });
    }

    /**
     * Click listener for cart view remove button
     *
     * @param view          the Button
     * @param viewCartModel the ViewCartModel
     */
    @BindingAdapter({"onClick"})
    public static void onRemoveClick(View view, ViewCartModel viewCartModel) {
        view.setOnClickListener(v -> {
            ViewCartViewModel cartViewModel = ViewModelProviders.of(
                    (MainActivity) view.getContext()).get(ViewCartViewModel.class);
            cartViewModel.removeViewCartItems(viewCartModel);
            Toast.makeText(view.getContext(), view.getContext().getString(R.string.removed, viewCartModel.itemName), Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Click listener for save later button
     *
     * @param view the Button
     */
    public static void onSaveLaterClick(View view) {
        Toast.makeText(view.getContext(), view.getContext().getString(R.string.todo_save_for_later), Toast.LENGTH_SHORT).show();
    }
}
