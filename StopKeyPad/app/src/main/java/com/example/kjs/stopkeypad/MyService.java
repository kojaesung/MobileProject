package com.example.kjs.stopkeypad;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;

public class MyService extends AccessibilityService {

    AccessibilityService service;

    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @TargetApi(24)
    @SuppressLint("NewApi")
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //Log.i("EventType", " " + event.getEventType());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            if (event.getPackageName() != null && event.getClassName() != null) {
                ComponentName componentName = new ComponentName(
                        event.getPackageName().toString(),
                        event.getClassName().toString()
                );

                ActivityInfo activityInfo = tryGetActivity(componentName);
	            /*String pkgName = activityInfo.applicationInfo.packageName;
	            Context c = null;
	            try {
					c = createPackageContext(pkgName, Context.CONTEXT_IGNORE_SECURITY);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Activity ac = ((Activity)c.getApplicationContext());
	            View view = ac.getCurrentFocus();
	            IBinder ib = view.getWindowToken();
	            Log.i("IBinder", ib.toString());*/

                //((Application)c.getApplicationContext()).get().

                boolean isActivity = activityInfo != null;
                if (isActivity)
                    Log.i("CurrentActivity", componentName.flattenToShortString());
            }
        }
        else if(event.getEventType() == AccessibilityEvent.TYPE_VIEW_FOCUSED)
        {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //inputManager.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
            inputManager.hideSoftInputFromInputMethod(null, InputMethodManager.HIDE_NOT_ALWAYS);

    		/*if(((KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE)).isKeyguardLocked()){
    			Log.i("TYPE_VIEW_FOCUSED", "lock");
    		}
    		else
    		{
    			Log.i("TYPE_VIEW_FOCUSED", "unlock");
    		}*/

            if (Build.VERSION.SDK_INT >= 24)
                getSoftKeyboardController().setShowMode(SHOW_MODE_HIDDEN);

            //findFocus(1).addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_FOCUS);
        }
    }


}
