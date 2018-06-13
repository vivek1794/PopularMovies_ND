package xyz.vivekc.popularmovies;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        if (BuildConfig.DEBUG) {
//                DescriptorMapping descriptorMapping = DescriptorMapping.withDefaults();
//                JavascriptEnvironment environment = new JavascriptEnvironment();
//                final SonarClient client = AndroidSonarClient.getInstance(this);
//                client.addPlugin(new NetworkSonarPlugin());
//                client.addPlugin(new InspectorSonarPlugin(getApplicationContext(), descriptorMapping));
//                client.addPlugin(new ConsoleSonarPlugin(environment));
//                client.addPlugin(new SharedPreferencesSonarPlugin(getApplicationContext()));
//                client.start();
//        }
    }
}
