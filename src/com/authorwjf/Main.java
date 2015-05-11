package com.authorwjf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

public class Main extends Activity
{
	private Handler mHandler = new Handler();
	private long mStartRX = 0;
	private long mStartTX = 0;
	public Main instance;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        instance = this;
        
        mStartRX = TrafficStats.getTotalRxBytes();
        mStartTX = TrafficStats.getTotalTxBytes();
        if (mStartRX == TrafficStats.UNSUPPORTED || mStartTX == TrafficStats.UNSUPPORTED)
        {
        	AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Uh Oh!");
			alert.setMessage("Your device does not support traffic stat monitoring.");
			alert.show();
        }
        else 
        {
        	mHandler.postDelayed(mRunnable, 1000);
        }
    }
    
    private final Runnable mRunnable = new Runnable()
    {
        public void run()
        {
        	TextView RX = (TextView)findViewById(R.id.RX);
        	TextView TX = (TextView)findViewById(R.id.TX);
        	long rxBytes = TrafficStats.getTotalRxBytes()- mStartRX;
        	RX.setText(Long.toString(rxBytes));
        	long txBytes = TrafficStats.getTotalTxBytes()- mStartTX;
        	TX.setText(Long.toString(txBytes));
        	mHandler.postDelayed(mRunnable, 1000);
        }
    };
     
    public void getWifiSpeed()
 	{
 		WifiManager wifiManager=(WifiManager)instance.getSystemService(Context.WIFI_SERVICE);
 		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
 		if (wifiInfo != null) 
 		{
 		    Integer linkSpeed = wifiInfo.getLinkSpeed(); //measured using WifiInfo.LINK_SPEED_UNITS
 		    Log.d("aaaa", "speed:"+linkSpeed);
 		}
 	}
}