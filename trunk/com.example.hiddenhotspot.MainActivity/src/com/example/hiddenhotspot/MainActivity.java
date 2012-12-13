package com.example.hiddenhotspot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import android.content.Context;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if(wifi.isWifiEnabled()) { wifi.setWifiEnabled(false); }
		Method[] wfMethods = wifi.getClass().getDeclaredMethods();
		boolean methodFound = false;
		for (Method method : wfMethods) {
			if (method.getName().equals("setWifiApEnabled")) {
				methodFound = true;
				WifiConfiguration netConf = new WifiConfiguration();
				netConf.SSID = "\"ggg\"";
				netConf.preSharedKey="\"qwertyui\"";
				netConf.hiddenSSID = false;
				netConf.status = WifiConfiguration.Status.ENABLED;
			    netConf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
			    netConf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
			    netConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);    
			    netConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
			    netConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			    netConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			    netConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			    
			    try {
			    	int appstate = -1;
			        method.invoke(wifi, netConf, true);
			        for (Method met : wfMethods) {
			        	if (met.getName().equals("isWifiApEnabled")) {
			        		while(!(Boolean)met.invoke(wifi)) {};
			        		for (Method method1 : wfMethods) {
			        			if(method1.getName().equals("getWifiApState")) {
			        				appstate =(Integer) method1.invoke(wifi);
			        			}
			        		}
			        	}
			        }
    				Log.i("123123", "App status - " + appstate);

			       
			      } catch (IllegalArgumentException e) {
			        e.printStackTrace();
			      } catch (IllegalAccessException e) {
			        e.printStackTrace();
			      } catch (InvocationTargetException e) {
			        e.printStackTrace();
			      }
			}
		}
		if(!methodFound) { Log.i("123123", "method not found"); }
	}

}
