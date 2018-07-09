package com.selva.demo.viewcart.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.selva.demo.viewcart.databinding.ItemViewCartBinding;
import com.selva.demo.viewcart.repository.model.ViewCartModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author selva.raman
 * @version 1.0
 * @since 7/2/2018
 */

public class ViewCartListAdapter extends RecyclerView.Adapter<ViewCartListHolder> {

    private final List<ViewCartModel> mViewCartModelList = new ArrayList<>();

    /**
     * Updates the new available cart list to the user
     *
     * @param mViewCartModelList the list of view cart model
     */
    public void setViewCartModelList(List<ViewCartModel> mViewCartModelList) {
        this.mViewCartModelList.clear();
        this.mViewCartModelList.addAll(mViewCartModelList);
        notifyDataSetChanged();
    }

    /**
     * Returns the newly created cart list holder
     *
     * @param parent   the ViewGroup
     * @param viewType the int
     * @return ViewCartListHolder
     */
    @NonNull
    @Override
    public ViewCartListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemViewCartBinding itemViewCartBinding = ItemViewCartBinding.inflate(layoutInflater, parent, false);
        return new ViewCartListHolder(itemViewCartBinding);
    }

    /**
     * Binds the view carts to the adapter
     *
     * @param holder   the ViewCartListHolder
     * @param position int, the selected position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewCartListHolder holder, int position) {
        holder.bind(mViewCartModelList.get(position));
    }

    /**
     * Returns the view cart list size
     *
     * @return int the size of the cart list
     */
    @Override
    public int getItemCount() {
        return mViewCartModelList.size();
    }
}
