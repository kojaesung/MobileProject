package com.example.kjs.termproject;

/**
 * Created by KJS on 2016-12-21.
 */

import android.location.Location;

public class CLocation extends Location     // Location 클래스 상속
{
    private boolean bUseMetricUnits = false;  // 처음 매트릭값은 사용안하므로 false로 설정

    public CLocation(Location paramLocation)
    {
        this(paramLocation, true); // 실행이 될경우 true로 변환
    }

    public CLocation(Location paramLocation, boolean paramBoolean)
    {
        super(paramLocation);
        this.bUseMetricUnits = paramBoolean;
    }

    // 이 위치와 지정된 위치 사이의 거리의 대략적인 거리를 돌려줍니다
    public float distanceTo(Location paramLocation)

    {
        float f2 = super.distanceTo(paramLocation);
        float f1 = f2;
        if (!getUseMetricUnits()) {
            f1 = f2 * 3.28084F; //
        }
        return f1;
    }

    // m의 위치의 추정된 정확도를 반환하는 함수 (신뢰도 68%)
    public float getAccuracy()
    {
        float f2 = super.getAccuracy();
        float f1 = f2;
        if (!getUseMetricUnits()) {
            f1 = f2 * 3.28084F;
        }
        return f1;
    }

    // WGS84 기준으로 타원체 위 미터, 높이를 반환
    public double getAltitude()
    {
        double d2 = super.getAltitude();
        double d1 = d2;
        if (!getUseMetricUnits()) {
            d1 = d2 * 3.28083989501312D;
        }
        return d1;
    }


// 이 제 2 접지 이상 / 미터, 사용할 수있는 경우 속도를 가져온다

    public float getSpeed()
    {
        float f2 = super.getSpeed() * 3.6F;
        float f1 = f2;
        if (!getUseMetricUnits()) {
            f1 = 2.2369363F * f2 / 3.6F;
        }
        return f1;
    }


// UseMetricUnits을 반환하는 함수

    public boolean getUseMetricUnits()
    {
        return this.bUseMetricUnits;
    }

    public void setUseMetricunits(boolean paramBoolean)
    {
        this.bUseMetricUnits = paramBoolean;
    }
}
