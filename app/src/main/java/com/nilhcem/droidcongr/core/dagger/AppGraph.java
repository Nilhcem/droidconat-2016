package com.nilhcem.droidcongr.core.dagger;

import com.nilhcem.droidcongr.receiver.BootReceiver;
import com.nilhcem.droidcongr.ui.drawer.DrawerActivity;
import com.nilhcem.droidcongr.ui.schedule.day.ScheduleDayFragment;
import com.nilhcem.droidcongr.ui.schedule.pager.SchedulePagerFragment;
import com.nilhcem.droidcongr.ui.sessions.details.SessionDetailsActivity;
import com.nilhcem.droidcongr.ui.sessions.list.SessionsListActivity;
import com.nilhcem.droidcongr.ui.settings.SettingsFragment;
import com.nilhcem.droidcongr.ui.speakers.details.SpeakerDetailsDialogFragment;
import com.nilhcem.droidcongr.ui.speakers.list.SpeakersListFragment;

/**
 * A common interface implemented by both the internal and production flavored components.
 */
public interface AppGraph {

    void inject(DrawerActivity activity);

    void inject(SchedulePagerFragment fragment);

    void inject(ScheduleDayFragment fragment);

    void inject(SessionsListActivity activity);

    void inject(SpeakersListFragment fragments);

    void inject(SessionDetailsActivity activity);

    void inject(SpeakerDetailsDialogFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(BootReceiver receiver);
}
