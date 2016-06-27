package com.nilhcem.droidcongr.core.dagger;

import com.nilhcem.droidcongr.InternalDroidconApp;

public interface InternalAppGraph extends AppGraph {

    void inject(InternalDroidconApp app);
}
