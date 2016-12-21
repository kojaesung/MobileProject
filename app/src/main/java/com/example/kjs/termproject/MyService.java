package com.example.kjs.termproject;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_FOCUSED;
import static android.view.accessibility.AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED;

/**
 * Created by KJS on 2016-12-21.
 */


class MyService extends AccessibilityService implements LocationListener
{
    ImageView back;
    ImageView home;
    boolean m_enableType = true;
    ImageView minimize;
    ImageView notification;
    LayoutParams params;
    AccessibilityService service;
    WindowManager windowManager;

    public MyService() {
    }

    private ActivityInfo tryGetActivity(ComponentName var1) {
        try {
            ActivityInfo var3 = this.getPackageManager().getActivityInfo(var1, 0);
            return var3;
        } catch (NameNotFoundException var2) {
            return null;
        }
    }

    private void updateSpeed(CLocation var1) {
        float var2 = 0.0F;
        if(var1 != null) {
            var1.setUseMetricunits(true);
            var2 = var1.getSpeed();
        }

        Formatter var3 = new Formatter(new StringBuilder());
        var3.format(Locale.US, "%5.1f", new Object[]{Float.valueOf(var2)});
        var3.toString().replace(' ', '0');
    }

    @SuppressLint({"NewApi"})
    @TargetApi(24)
    public void onAccessibilityEvent(AccessibilityEvent var1) {
        boolean var2 = true;
        ((LocationManager)this.getSystemService("location")).requestLocationUpdates("gps", 0L, 0.0F, this);
        // 윈도우 스테이지가 변화했을 경우
        if(var1.getEventType() == TYPE_WINDOW_STATE_CHANGED) {
            //패키지 이름과 클래스이름이 Null값이 아닌 경우 실행
            if(var1.getPackageName() != null && var1.getClassName() != null) {
                // 새로운 Component 식별자를 만든다.
                ComponentName var4 = new ComponentName(var1.getPackageName().toString(), var1.getClassName().toString());
                if(this.tryGetActivity(var4) == null) {
                    var2 = false;
                }

                if(var2) {
                    Log.i("CurrentActivity", var4.flattenToShortString());
                }
            }
        } else {  // 뷰의 포커싱이 됬을경우에 실행
            if(var1.getEventType() == TYPE_VIEW_FOCUSED) {
                InputMethodManager var5 = (InputMethodManager)this.getSystemService("input_method");
                var5.toggleSoftInput(0, 1);
                var5.hideSoftInputFromInputMethod((IBinder)null, 2);
                return;
            }


            if(var1.getEventType() == TYPE_VIEW_TEXT_CHANGED && !this.m_enableType) {  // 텍스트가 변화할 때에 실행..
                Log.i("event", "TYPE_VIEW_TEXT_CHANGE " + var1.getBeforeText());
                Bundle var3 = new Bundle();  // 번들객체 생성
                var3.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", var1.getBeforeText());
                // 현지 지정된 키의 값을 이전 텍스트값으로 변경시킨다 = 처음에 썼던 텍스트 이전으로 돌아간다.
                this.findFocus(1).performAction(2097152, var3);
                return;
            }
        }

    }

    @SuppressLint({"RtlHardcoded"})
    public void onCreate() {
        super.onCreate();
    }

    // 사용자가 터치 스크린의 특정 동작을 수행 할 때 시스템에서 호출
    public boolean onGesture(int var1) {
        Log.d("onGesture", "" + var1);
        return super.onGesture(var1);
    }
    // 접근성 피드백을 차단하기위한 콜백
    public void onInterrupt() {
        Log.i("TAG", "onInterrupt");
    }
    // 시스템의 나머지 부분으로 전달되기 전에 접근성 서비스는 키 이벤트를 관찰 할 수 있도록 콜백
    public boolean onKeyEvent(KeyEvent var1) {
        Log.d("onKeyEvent", "" + var1.getKeyCode());
        return super.onKeyEvent(var1);
    }
    // 위치가 변경될 때 호출되는 함수 정의
    public void onLocationChanged(Location var1) {
        // 움직이는 속도가 0.5이상일경우 false = 움직일 때 키패드를 못쓰도록 하기 위해...
        if((double)var1.getSpeed() > 0.5D) {
            this.m_enableType = false;
        } else {
            this.m_enableType = true;
        }

        if(var1 != null) {
            this.updateSpeed(new CLocation(var1, true));
        }

    }

    public void onProviderDisabled(String var1) {
        Toast.makeText(this.getBaseContext(), "onProviderDisabled", 0).show();
    }

    public void onProviderEnabled(String var1) {
        Toast.makeText(this.getBaseContext(), "onProviderEnabled", 0).show();
    }

    // 서비스 연결 됬을시...
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("TAG", "onServiceConnected");
        System.out.println("KKKKKKKK onServiceConnected");
        AccessibilityServiceInfo var1 = new AccessibilityServiceInfo();
        var1.eventTypes = -1;
        var1.feedbackType = -1;
        if(VERSION.SDK_INT >= 16) {
            var1.flags = 2;
        }

        this.setServiceInfo(var1);
        ((LocationManager)this.getSystemService("location")).requestLocationUpdates("gps", 0L, 0.0F, this);
        this.updateSpeed((CLocation)null);
    }

    // 움직임이 감지됬을경우..
    public void onStatusChanged(String var1, int var2, Bundle var3) {
        Toast.makeText(this.getBaseContext(), "onStatusChanged", 0).show();
    }

    public boolean onUnbind(Intent var1) {
        Log.d("TAG", "onUnBind");
        return super.onUnbind(var1);
    }
}