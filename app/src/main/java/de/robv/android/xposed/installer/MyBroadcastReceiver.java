package de.robv.android.xposed.installer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.robv.android.xposed.installer.util.AssetUtil;
import de.robv.android.xposed.installer.util.ModuleUtil;
import de.robv.android.xposed.installer.util.NavUtil;
import de.robv.android.xposed.installer.util.RootUtil;

/**
 * Created by krutikakamilla on 11/21/14.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       // Intent startServiceIntent = new Intent(context, XposedInstallerActivity.class);
        System.out.println("starting xposed installer activity");
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            String BINARIES_FOLDER = AssetUtil.getBinariesFolder();
            String APP_PROCESS_NAME = BINARIES_FOLDER + "app_process_xposed_sdk16";
            String JAR_PATH = XposedApp.BASE_DIR + "bin/XposedBridge.jar";
            String JAR_PATH_NEWVERSION = JAR_PATH + ".newversion";
            List<String> messages = new LinkedList<String>();
            XposedApp mApp;
             SharedPreferences mPref;
             final PackageManager mPm;
           /* Intent myStarterIntent = new Intent(context, WelcomeActivity.class);
            myStarterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myStarterIntent);*/
            System.out.println("2nd intent starting activity");
            Intent i = new Intent(context, XposedInstallerActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(XposedInstallerActivity.EXTRA_SECTION, XposedDropdownNavActivity.TAB_INSTALL);
            intent.putExtra(NavUtil.FINISH_ON_UP_NAVIGATION, true);
            context.startActivity(i);
            System.out.println("creating object of rootutil");
            RootUtil mRootUtil = new RootUtil();
            mRootUtil.startShell();
            AssetUtil.writeAssetToSdcardFile("Xposed-Disabler-Recovery.zip", 00644);
            File appProcessFile = AssetUtil.writeAssetToFile(APP_PROCESS_NAME, new File(XposedApp.BASE_DIR + "bin/app_process"), 00700);
            messages.add("/system");
            mRootUtil.executeWithBusybox("mount -o remount,rw /system", messages);
            mRootUtil.executeWithBusybox("cp -a /system/bin/app_process /system/bin/app_process.orig", messages);
            mRootUtil.executeWithBusybox("sync", messages);
            mRootUtil.executeWithBusybox("cp -a " + appProcessFile.getAbsolutePath() + " /system/bin/app_process", messages);
            mRootUtil.executeWithBusybox("chmod 755 /system/bin/app_process", messages);
            mRootUtil.executeWithBusybox("chown root:shell /system/bin/app_process", messages);
            File blocker = new File(XposedApp.BASE_DIR + "conf/disabled");
            mRootUtil.executeWithBusybox("rm " + blocker.getAbsolutePath(), messages);
            File jarFile = AssetUtil.writeAssetToFile("XposedBridge.jar", new File(JAR_PATH_NEWVERSION), 00644);
            mRootUtil.executeWithBusybox("sync", messages);
            System.out.println("finished installing successfully with......"+ TextUtils.join("\n", messages).trim());
            //InstallerFragment inst= new InstallerFragment();
            AssetUtil.removeBusybox();
            //inst.install();
            System.out.println("finished in installing xposedframework");
            mApp = XposedApp.getInstance();
            mPref = mApp.getSharedPreferences("enabled_modules", Context.MODE_PRIVATE);
            ModuleUtil mModuleUtil;
            mModuleUtil = ModuleUtil.getInstance();
            String packageName ="at.zweng.xposed.modifyaidrouting";
            mModuleUtil.setModuleEnabled(packageName, true);
            mModuleUtil.updateModulesList(true);
            mPref.edit().putInt(packageName, 1).commit();
            System.out.println("enabled digital wallet module in xposedframework");
        }

    System.out.println("started welcome activity");
       // startActivity(i);
    }
}
