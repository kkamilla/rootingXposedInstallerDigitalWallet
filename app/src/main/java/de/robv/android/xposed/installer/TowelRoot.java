// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 
package de.robv.android.xposed.installer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TowelRoot extends Activity
{

    public TowelRoot()
    {
    }

    public void buttonClicked(View view)
    {
        ((TextView)findViewById(0x7f050002)).setText(rootTheShit());
    }

    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(0x7f030000);
        ((TextView)findViewById(0x7f050002)).setText(rootTheShit());

    }

    public native String rootTheShit();

    static 
    {
        System.loadLibrary("exploit");
        //System.load("/Users/krutikakamilla/Desktop/mobile_sec/Towelroot-by-Geohot/app/lib/armeabi/libexploit.so");
    }
}
