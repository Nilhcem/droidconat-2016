package com.nilhcem.droidcongr.ui.schedule.day;

import com.nilhcem.droidcongr.data.app.model.ScheduleSlot;

import java.util.List;

public interface ScheduleDayMvp {

    interface View {
        void initSlotsList(List<ScheduleSlot> slots);

        void refreshSlotsList();
    }
}
