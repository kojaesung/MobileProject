package com.example.kjs.termproject;

import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.Formatter;
import java.util.
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements IBaseGpsListener
{
    private void updateSpeed(CLocation paramCLocation)
    {
        float f = 0.0F;
        if (paramCLocation != null)
        {
            paramCLocation.setUseMetricunits(useMetricUnits()); // Metric값을 세팅 한 후에
            f = paramCLocation.getSpeed(); // f 에 현재 위치에대한 속도를 반환한다.
        }
        paramCLocation = new Formatter(new StringBuilder());
        paramCLocation.format(Locale.US, "%5.1f", new Object[] { Float.valueOf(f) });
        String str = paramCLocation.toString().replace(' ', '0');
        paramCLocation = "miles/hour";
        if (useMetricUnits()) {  // 체크박스를 클릭했을 경우 미터/초 로 변환
            paramCLocation = "meters/second"; // Meter per seconds
        }
        ((TextView)findViewById(2131165186)).setText(str + " " + paramCLocation); // 현재 위치값 출력
    }

    // 체크박스를 체크했는지 안했는지 확인..
    private boolean useMetricUnits()
    {
        return ((CheckBox)findViewById(2131165187)).isChecked();
    }

    // 액티비티 종료
    public void finish()
    {
        super.finish();
        System.exit(0);
    }

    @SuppressLint({"NewApi"})
    // 이곳은 체크박스 객체를 인플레이션 한 후에 그려주고 맨 처음 값은 0으로 초기화 시킨다!!
    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(2130903041);
        ((LocationManager)getSystemService("location")).requestLocationUpdates("gps", 0L, 0.0F, this);
        updateSpeed(null);
        ((CheckBox)findViewById(2131165187)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
            {
                MainActivity.this.updateSpeed(null);
            }
        });
    }

    // 위치가 변했을 경우 메트릭값과 위치값을 기반으로 움직인 속도를 출력
    public void onLocationChanged(Location paramLocation)
    {
        if (paramLocation != null) {
            updateSpeed(new CLocation(paramLocation, useMetricUnits()));
        }
    }

    public void onProviderDisabled(String paramString) {}

    public void onProviderEnabled(String paramString) {}

    public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {}
}