package client.android.paying.com.payingmobileclient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Created by ahmetkucuk on 31/10/14.
 */
public class WifiDirectBroadCastReceiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private TabActivity activity;

    public WifiDirectBroadCastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,
                                       TabActivity activity) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if (manager != null) {
                manager.requestPeers(channel, activity);
            }
            Log.d("TAG", "P2P peers changed");
        }
    }
}
