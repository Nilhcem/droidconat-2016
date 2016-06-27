package com.nilhcem.droidcongr.ui.schedule.pager;

import com.nilhcem.droidcongr.data.app.model.Schedule;

public interface SchedulePagerMvp {

    interface View {
        void displaySchedule(Schedule schedule);

        void displayLoadingError();
    }

    interface Presenter {
        void reloadData();
    }
}
