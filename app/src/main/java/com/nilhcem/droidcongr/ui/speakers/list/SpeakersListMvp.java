package com.nilhcem.droidcongr.ui.speakers.list;

import com.nilhcem.droidcongr.data.app.model.Speaker;

import java.util.List;

public interface SpeakersListMvp {

    interface View {
        void displaySpeakers(List<Speaker> speakers);

        void displayLoadingError();

        void showSpeakerDetails(Speaker speaker);
    }

    interface Presenter {
        void reloadData();
    }
}
