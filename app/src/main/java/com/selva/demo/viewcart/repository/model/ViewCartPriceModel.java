package com.selva.demo.viewcart.repository.model;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.selva.demo.viewcart.R;
import com.selva.demo.viewcart.utils.NetworkUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * @author selva.raman
 * @version 1.0
 * @since 4/7/2018
 */

public class ViewCartPriceModel {
    public List<ViewCartModel> viewCartModelList;
    public boolean isEmpty;

    /**
     * Sets the visibility of continue button layout
     *
     * @param view              the continue button layout
     * @param viewCartModelList List<ViewCartModel>, the list of available cart items
     */
    @BindingAdapter({"isShowContinue"})
    public static void setVisibility(View view, List<ViewCartModel> viewCartModelList) {
        view.setVisibility(null == viewCartModelList || 0 == viewCartModelList.size() ? View.GONE : View.VISIBLE);
    }

    /**
     * Sets the visibility for No Internet Connection view / Your shopping cart is empty
     *
     * @param view    the TextView
     * @param isEmpty boolean, true if cart list is not available otherwise false
     */
    @BindingAdapter({"isEmpty"})
    public static void setNoInternetViewVisibility(View view, boolean isEmpty) {
        if (isEmpty) {
            TextView textView = (TextView) view;
            textView.setText(view.getContext().getString(R.string.cart_empty));
            textView.setVisibility(View.VISIBLE);
            if (!NetworkUtils.isNetworkConnected(view.getContext())) {
                Toast.makeText(view.getContext()
                        , view.getContext().getString(R.string.no_internet_connection)
                        , Toast.LENGTH_SHORT).show();
            }
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Calculates the total cart amount and update to the user
     *
     * @param textView          the TextView
     * @param viewCartModelList the List<ViewCartModel>
     */
    @BindingAdapter({"totalAmount"})
    public static void getTotalCartAmount(TextView textView, List<ViewCartModel> viewCartModelList) {
        long total = 0;
        if (null != viewCartModelList) {
            for (ViewCartModel viewCartModel : viewCartModelList) {
                int quantity = (null == viewCartModel.itemQuantity || "".equals(viewCartModel.itemQuantity)) ?
                        1 : Integer.parseInt(viewCartModel.itemQuantity);
                int price = null == viewCartModel.itemPrice ?
                        0 : Integer.parseInt(viewCartModel.itemPrice.replace(",", ""));
                total += price * quantity;
            }
        }
        textView.setText(textView.getContext().getString(R.string.price, new DecimalFormat("##,##,##0").format(total)));
    }

    /**
     * Click listener for continue button
     *
     * @param view the Continue button
     */
    public static void onContinueClick(View view) {
        Toast.makeText(view.getContext(), view.getContext().getString(R.string.todo_continue), Toast.LENGTH_SHORT).show();
    }
}
