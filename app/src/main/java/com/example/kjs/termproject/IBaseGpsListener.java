package com.example.kjs.termproject;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by KJS on 2016-12-21.
 */

public abstract interface IBaseGpsListener extends LocationListener
{
    // 위치가 변경 될 때 호출
    public abstract void onLocationChanged(Location paramLocation);
    // 공급자는 사용자가 비활성화 될 때 호출
    public abstract void onProviderDisabled(String paramString);
    // 공급자가 사용자에 의해 활성화 될 때 호출
    public abstract void onProviderEnabled(String paramString);
    // 공급자의 상태가 변경 될 때 호출
    public abstract void onStatusChanged(String paramString, int paramInt, Bundle paramBundle);
}
