package com.selva.demo.viewcart.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.selva.demo.viewcart.databinding.ItemViewCartBinding;
import com.selva.demo.viewcart.repository.model.ViewCartModel;

/**
 * @author selva.raman
 * @version 1.0
 * @since 7/2/2018
 */

class ViewCartListHolder extends RecyclerView.ViewHolder {
    private ItemViewCartBinding mItemViewCartBinding;

    /**
     * Constructor is to initialize the ItemViewCartBinding
     *
     * @param itemViewCartBinding the ItemViewCartBinding
     */
    ViewCartListHolder(ItemViewCartBinding itemViewCartBinding) {
        super(itemViewCartBinding.getRoot());
        mItemViewCartBinding = itemViewCartBinding;
    }

    /**
     * bind the view cart model and refresh the screen when new data's available
     *
     * @param viewCartModel the ViewCartModel
     */
    void bind(ViewCartModel viewCartModel) {
        mItemViewCartBinding.setModel(viewCartModel);
        mItemViewCartBinding.executePendingBindings();
    }
}
