package com.samplerecyclerview.base;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.uiautomator.UiDevice;


import com.samplerecyclerview.pages.PostScreenPage;
import com.samplerecyclerview.ui.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class TestBase {
    //    public static final int appLogLevel = LogPriority.VERBOSE;
    public static final String TAG = "TestBase";

    public static String currentTestName;
    public static String currentSuiteName;
    public static boolean forceSignOut;
    static UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
    protected boolean mIsCoreScenarioPRGateTest = false;
    private long startTime;

    public static final List<String> COMMON_PERMISSIONS = Arrays.asList(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.DUMP,
            Manifest.permission.PACKAGE_USAGE_STATS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.CALL_PHONE
    );
    public static final List<String> ADDITIONAL_PERMISSIONS_FOR_API_31 = Arrays.asList(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
    );

    public List<String> getAllPermissions() {
        List<String> permissionsList = new ArrayList<>(COMMON_PERMISSIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionsList.addAll(ADDITIONAL_PERMISSIONS_FOR_API_31);
        }
        return permissionsList;
    }

    // granting read, write permissions for external storage, Dump, packageusage, stats, record audio permissions
    @Rule
    public GrantPermissionRule mRuntimePermissionRule = GrantPermissionRule.grant(getAllPermissions().toArray(new String[0]));

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            currentSuiteName = description.getClassName();
            currentTestName = description.getMethodName();
        }
    };


    @Rule
    public TestName testName = new TestName();

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");

    /**
     * True when a user has signed in for the first time.
     **/
    protected boolean mUserWentThroughSignInFlow;

    @Before
    public final void testBaseSetup() throws Exception {
        try {
            String previousValue = System.setProperty("kotlinx.coroutines.debug", "off");
            Log.i(TAG, "Set kotlinx.coroutines.debug property. Previous value: " + previousValue + " new value: off");
        } catch (Exception exception) {
            Log.d(TAG, testName.getMethodName() + " catch the exception while setting system property kotlinx.coroutines.debug " + exception);
        }


        // log start time for each test case.
        startTime = System.currentTimeMillis();
        Log.i(TAG, testName.getMethodName() + " started at (HH:mm:ss:SSS) " + simpleDateFormat.format(new Date(startTime)));

    }

    @After
    public final void testBaseTearDown() {
        long endTime;

        // Closing the app explicitly as we are launching app in test setup
        logPerfData();
    }

    /**
     * Method to log perf data
     */
    public void logPerfData() {
    }

    /**
     * returns true if app is installed, otherwise return false
     */
    public boolean isAppInstalled(@NonNull String packageName) {
        boolean isPkgInstalled = false;
        Context context = getInstrumentation().getTargetContext();
        if (context != null) {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                isPkgInstalled = true;
            }
        }
        return isPkgInstalled;
    }

    /**
     * navigate To other Apps like Authenticator
     */
    public boolean navigateToApp(@NonNull String packageName, int flag) {
        boolean navigationSuccessful = false;
        Context context = getInstrumentation().getTargetContext();
        if (context != null) {
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (launchIntent != null) {
                launchIntent.setFlags(flag);
                context.startActivity(launchIntent);
                navigationSuccessful = true;
            } else {
                Log.e(TAG, testName.getMethodName() + "launchIntent is null");
            }
        } else {
            Log.e(TAG, testName.getMethodName() + "context is null");
        }
        return navigationSuccessful;
    }


    /**
     * @throws InterruptedException
     * @deprecated - Create the IPage object using `new` directly and call navigate
     */
    @Deprecated
    public InPage createInstance(@Nullable InPage mInstance, Class<?> referenceType) throws Exception {
        return createInstance(mInstance, referenceType, true);
    }

    /**
     * @deprecated - Create the IPage object using `new` directly and call navigate
     */
    @Deprecated
    public InPage createInstance(@Nullable InPage mInstance, Class<?> referenceType, boolean shouldNavigateToPage) throws Exception {
        if (mInstance == null) {
            if (referenceType == PostScreenPage.class) {
                mInstance = new PostScreenPage();
            } else {
                return null;
            }
        }

        if (shouldNavigateToPage) {
            //FixIt: We have to move this to right place
            mInstance.navigate();
        }

        return mInstance;
    }
}
