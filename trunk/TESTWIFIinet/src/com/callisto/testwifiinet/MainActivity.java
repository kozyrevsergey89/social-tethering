package com.callisto.testwifiinet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       	
            // /.
           //.savedInstanceState
             makeConnection();
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	/*
    	new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				makeConnection();	
			}
		}, 5000);
    	*/
    	
    	   //if ((isNetworkConnected())/*&&(!isWifiConnected()) */){
           //	startSharing();
           //} else {
           	Timer timer = new Timer();
               makeConnection();
           // timer.schedule(new ScheduledTaskWithHandeler(), 1000, 5000);
           //} 
    }
    
    
    private boolean isWifiConnected() {
  	  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
  	  NetworkInfo ni = cm.getActiveNetworkInfo();
  	  if (ni == null) {
  	   // There are no active networks.
  	   return false;
  	  } else if (ni.getTypeName().equalsIgnoreCase("WIFI"))
  	   return true;
  	  return false;
  	   
  	 }
    
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        	//if (!isNetworkConnected()) {
        		makeConnection();
            //}
        }
     };

     class ScheduledTaskWithHandeler extends TimerTask {

        @Override
        public void run() {
           handler.sendEmptyMessage(0) ;
        }
     }
    
    private void makeConnection(){
    	
    	final WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    	if (!wifi.isWifiEnabled()) { wifi.setWifiEnabled(true);   Log.d("WifiPreference", "wifi addapter has been enabled");};
    	new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				WifiConfiguration wc = new WifiConfiguration();
		    	wc.SSID = "\"\"ggg3\"\"";
		    	wc.preSharedKey  = "\"\"qwertyui\"\"";
		    	wc.hiddenSSID = false;
		    	wc.status = WifiConfiguration.Status.ENABLED;        
		    	wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		    	wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		    	wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		    	//wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		    	wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		    	wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
		    	wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
		    	wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		    	
		    	wifi.disconnect();
		    	int res = wifi.addNetwork(wc);
		    	Log.d("WifiPreference", "add Network returned " + res );
		    	boolean b = wifi.enableNetwork(res, true);        
		    	Log.d("WifiPreference", "enableNetwork returned " + b );
		    
			}
		}, 5000);
    	
    	
    	/*
    	WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    	if (!wifi.isWifiEnabled()) wifi.setWifiEnabled(true);
//    	Log.d("WifiPreference", "wifi addapter has been enabled");
    	
    	WifiConfiguration wc = new WifiConfiguration();
    	wc.SSID = "\"ggg\"";
    	wc.preSharedKey  = "\"12345678\"";
    	wc.hiddenSSID = false;
    	wc.status = WifiConfiguration.Status.ENABLED;        
    	wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
    	wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
    	wc.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
    	wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
    	wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
    	wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
    	wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
    	wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
    	int res = wifi.addNetwork(wc);
    	Log.d("WifiPreference", "add Network returned " + res );
    	boolean b = wifi.enableNetwork(res, true);        
    	Log.d("WifiPreference", "enableNetwork returned " + b );
    	  */  }
    
    private boolean isNetworkConnected() {
    	  ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    	  NetworkInfo ni = cm.getActiveNetworkInfo();
    	  if (ni == null) {
    	   // There are no active networks.
    	   return false;
    	  } else
    	   return true;
    	 }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void startSharing(){
    	WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if(wifi.isWifiEnabled()) { wifi.setWifiEnabled(false); }
		Method[] wfMethods = wifi.getClass().getDeclaredMethods();
		boolean methodFound = false;
		for (Method method : wfMethods) {
			if (method.getName().equals("setWifiApEnabled")) {
				methodFound = true;
				WifiConfiguration netConf = new WifiConfiguration();
				netConf.SSID = "\"yyy\"";
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
