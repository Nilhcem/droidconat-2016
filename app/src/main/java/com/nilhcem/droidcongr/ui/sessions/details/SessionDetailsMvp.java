package com.nilhcem.droidcongr.ui.sessions.details;

import android.support.annotation.StringRes;

import com.nilhcem.droidcongr.data.app.model.Session;

public interface SessionDetailsMvp {

    interface View {
        void bindSessionDetails(Session session);

        void updateFabButton(boolean isSelected, boolean animate);

        void showSnackbarMessage(@StringRes int resId);
    }

    interface Presenter {
        void onFloatingActionButtonClicked();
    }
}
