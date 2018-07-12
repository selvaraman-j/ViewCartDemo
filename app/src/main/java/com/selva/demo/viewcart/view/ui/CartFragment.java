package com.selva.demo.viewcart.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selva.demo.viewcart.R;
import com.selva.demo.viewcart.databinding.FragmentViewCartBinding;
import com.selva.demo.viewcart.repository.model.ViewCartModel;
import com.selva.demo.viewcart.repository.model.ViewCartPriceModel;
import com.selva.demo.viewcart.view.adapter.ViewCartListAdapter;
import com.selva.demo.viewcart.viewmodel.ViewCartViewModel;

import java.util.List;

/**
 * @author selva.raman
 * @version 1.0
 * @since 6/29/2018
 */

public class CartFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ViewCartViewModel mViewCartViewModel;
    private ViewCartListAdapter mViewCartListAdapter;
    private FragmentViewCartBinding mFragmentViewCartBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (null != mainActivity) {
            mViewCartViewModel = ViewModelProviders.of(mainActivity).get(ViewCartViewModel.class);
        }
    }

    /**
     * Returns the instance of cart fragment
     *
     * @return Fragment the CartFragment
     */
    public static CartFragment getInstance() {
        return new CartFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentViewCartBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_cart, container, false);
        setCartViewAdapter();
        return mFragmentViewCartBinding.getRoot();
    }

    /**
     * Sets the refresh listener to swipe refresh layout
     * and sets the layout manager, adapter for recycler view to show the cart items
     */
    private void setCartViewAdapter() {
        mFragmentViewCartBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mViewCartListAdapter = new ViewCartListAdapter();
        mFragmentViewCartBinding.cartViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentViewCartBinding.cartViewList.setAdapter(mViewCartListAdapter);
        showCartView(false);
    }

    /**
     * Shows the list of available carts from either db or web server
     */
    private void showCartView(boolean isForceUpdate) {
        if (null != mViewCartViewModel) {
            mFragmentViewCartBinding.swipeRefreshLayout.setRefreshing(true);
            //add observer to LiveData
            //observer gets called every time data changes in LiveData
            mViewCartViewModel.getCartListData(isForceUpdate).observe(
                    this, (@Nullable List<ViewCartModel> viewCartModelList) -> {
                        ViewCartPriceModel viewCartPriceModel = new ViewCartPriceModel();
                        viewCartPriceModel.viewCartModelList = viewCartModelList;
                        if (null == viewCartModelList || viewCartModelList.size() == 0) {
                            viewCartPriceModel.isEmpty = true;
                        }
                        mFragmentViewCartBinding.setModel(viewCartPriceModel);
                        mViewCartListAdapter.setViewCartModelList(viewCartModelList);
                        mFragmentViewCartBinding.swipeRefreshLayout.setRefreshing(false);
                    });
        }
    }

    /**
     * pull down refresh callback, used to fetch and show the
     * list of available carts from server.
     * isForceUpdate value is true so always it will fetch the cart list data from web server
     */
    @Override
    public void onRefresh() {
        showCartView(true);
    }
}
