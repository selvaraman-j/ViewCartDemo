package com.selva.demo.viewcart.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.selva.demo.viewcart.repository.model.ViewCartModel;
import com.selva.demo.viewcart.repository.remote.CartRepository;

import java.util.List;

/**
 * @author selva.raman
 * @version 1.0
 * @since 6/28/2018
 */

public class ViewCartViewModel extends AndroidViewModel {
    private MutableLiveData<List<ViewCartModel>> mLiveData;

    public ViewCartViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Returns the cart view live data. If the cart view is empty or null
     * then fetch the data's from the server.
     *
     * @return LiveData the list of cart view live data
     */
    public LiveData<List<ViewCartModel>> getCartListData() {
        if (null == mLiveData || null == mLiveData.getValue() || mLiveData.getValue().size() == 0) {
            mLiveData = CartRepository.getInstance().getCartDetails();
        }
        return mLiveData;
    }

    /**
     * Update the cart view property in the live data
     *
     * @param viewCartModel the ViewCartModel
     */
    public void updateViewCart(ViewCartModel viewCartModel) {
        if (null != viewCartModel && null != mLiveData.getValue()) {
            List<ViewCartModel> viewCartResponseList = mLiveData.getValue();
            viewCartResponseList.set(viewCartResponseList.indexOf(viewCartModel), viewCartModel);
            mLiveData.setValue(viewCartResponseList);
        }
    }

    /**
     * Remove the particular cart view from the live data
     *
     * @param viewCartModel the ViewCartModel
     */
    public void removeViewCartItems(ViewCartModel viewCartModel) {
        if (null != viewCartModel && null != mLiveData.getValue()) {
            List<ViewCartModel> viewCartResponseList = mLiveData.getValue();
            if (viewCartResponseList.contains(viewCartModel)) {
                viewCartResponseList.remove(viewCartModel);
                mLiveData.setValue(viewCartResponseList);
            }
        }
    }
}
