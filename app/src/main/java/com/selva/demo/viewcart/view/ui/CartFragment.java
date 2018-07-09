package com.selva.demo.viewcart.view.ui;

import android.arch.lifecycle.Observer;
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
import android.widget.Toast;

import com.selva.demo.viewcart.R;
import com.selva.demo.viewcart.databinding.FragmentViewCartBinding;
import com.selva.demo.viewcart.repository.model.ViewCartModel;
import com.selva.demo.viewcart.repository.model.ViewCartPriceModel;
import com.selva.demo.viewcart.utils.NetworkUtils;
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
     * sets the adapter for cart view list and fetch the live data
     */
    private void setCartViewAdapter() {
        mFragmentViewCartBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mViewCartListAdapter = new ViewCartListAdapter();
        mFragmentViewCartBinding.cartViewList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentViewCartBinding.cartViewList.setAdapter(mViewCartListAdapter);
        onRefresh();
    }

    /**
     * Shows the list of available carts
     */
    private void showCartView() {
        if (null != mViewCartViewModel) {
            //add observer to LiveData
            //observer gets called every time data changes in LiveData
            mViewCartViewModel.getCartListData().observe(this, new Observer<List<ViewCartModel>>() {
                @Override
                public void onChanged(@Nullable List<ViewCartModel> viewCartModelList) {
                    ViewCartPriceModel viewCartPriceModel = new ViewCartPriceModel();
                    viewCartPriceModel.viewCartModelList = viewCartModelList;
                    viewCartPriceModel.isLoaded = true;
                    if (null == viewCartModelList || viewCartModelList.size() == 0) {
                        viewCartPriceModel.isEmpty = true;
                    }
                    mFragmentViewCartBinding.setModel(viewCartPriceModel);
                    mViewCartListAdapter.setViewCartModelList(viewCartModelList);
                    mFragmentViewCartBinding.swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    /**
     * pull down refresh callback, used to fetch and show the list of available carts
     * from server and will show to user as No Internet Connection! when the device is not connected
     * to Internet
     */
    @Override
    public void onRefresh() {
        if (NetworkUtils.isNetworkConnected(getActivity())) {
            mFragmentViewCartBinding.swipeRefreshLayout.setRefreshing(true);
            showCartView();
        } else {
            mFragmentViewCartBinding.swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }
}
