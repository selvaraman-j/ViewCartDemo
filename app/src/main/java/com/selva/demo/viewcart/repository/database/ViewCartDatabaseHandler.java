package com.selva.demo.viewcart.repository.database;

import android.os.AsyncTask;

import com.selva.demo.viewcart.repository.database.entity.ViewCartModelEntity;
import com.selva.demo.viewcart.viewmodel.ViewCartViewModel;

import java.util.List;

/**
 * @author selva.raman
 * @version 1.0
 * @since 7/12/2018
 */

public class ViewCartDatabaseHandler {

    public static class AddCartList extends AsyncTask<Void, Void, Void> {

        private ViewCartDatabase db;
        List<ViewCartModelEntity> mViewCartModelEntity;

        public AddCartList(ViewCartDatabase viewCartDatabase, List<ViewCartModelEntity> viewCartModelEntity) {
            db = viewCartDatabase;
            mViewCartModelEntity = viewCartModelEntity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.getViewCartDao().addCart(mViewCartModelEntity);
            return null;
        }
    }

    public static class UpdateCartItem extends AsyncTask<Void, Void, Void> {
        private final ViewCartDatabase db;
        private final ViewCartModelEntity mViewCartModelEntity;
        private final ViewCartViewModel mViewCartViewModel;

        public UpdateCartItem(ViewCartDatabase viewCartDatabase
                , ViewCartModelEntity viewCartModelEntity, ViewCartViewModel viewCartViewModel) {
            db = viewCartDatabase;
            mViewCartModelEntity = viewCartModelEntity;
            mViewCartViewModel = viewCartViewModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.getViewCartDao().updateCartItem(mViewCartModelEntity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mViewCartViewModel.getCartListData(false);
        }
    }

    public static class RetrieveCartList extends AsyncTask<Void, Void, List<ViewCartModelEntity>> {

        private ViewCartDatabase db;
        private AsyncResponse asyncResponse;

        public RetrieveCartList(ViewCartDatabase viewCartDatabase, AsyncResponse asyncResponse) {
            db = viewCartDatabase;
            this.asyncResponse = asyncResponse;
        }

        @Override
        protected List<ViewCartModelEntity> doInBackground(Void... voids) {
            return db.getViewCartDao().getViewCartList();
        }

        @Override
        protected void onPostExecute(List<ViewCartModelEntity> listLiveData) {
            asyncResponse.finished(listLiveData);
            super.onPostExecute(listLiveData);
        }

        public interface AsyncResponse {
            void finished(List<ViewCartModelEntity> listLiveData);
        }
    }


    public static class DeleteCartItem extends AsyncTask<Void, Void, Void> {
        private final ViewCartDatabase db;
        private final ViewCartModelEntity mViewCartModelEntity;
        private final ViewCartViewModel mViewCartViewModel;

        public DeleteCartItem(ViewCartDatabase viewCartDatabase
                , ViewCartModelEntity viewCartModelEntity, ViewCartViewModel viewCartViewModel) {
            db = viewCartDatabase;
            mViewCartModelEntity = viewCartModelEntity;
            mViewCartViewModel = viewCartViewModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.getViewCartDao().deleteCartItem(mViewCartModelEntity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mViewCartViewModel.getCartListData(false);
        }
    }
}
