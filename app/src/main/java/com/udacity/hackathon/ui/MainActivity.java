package com.udacity.hackathon.ui;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.udacity.hackathon.R;
import com.udacity.hackathon.model.MessageRow;

import static com.udacity.hackathon.Config.MSG_PUSHOUT_DATA;
import static com.udacity.hackathon.Config.MSG_REGISTER_ACTIVITY;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    WiFiDirectApplication mApp = null;
    ChatFragment mChatFrag = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        Intent i = getIntent();
        String initMsg = i.getStringExtra("FIRST_MSG");

        mApp = (WiFiDirectApplication) getApplication();
        initFragment(initMsg);
    }

    /**
     * init fragement with possible recvd start up message.
     */
    public void initFragment(String initMsg) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (mChatFrag == null) {
            mChatFrag = ChatFragment.newInstance(this, null, initMsg);
        }

        Log.d(TAG, "initFragment : show chat fragment..." + initMsg);
        ft.add(R.id.frag_chat, mChatFrag, "chat_frag");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: chat activity resume, register activity to connection service !");
        registerActivityToService(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: chat activity closed, de-register activity from connection service !");
        registerActivityToService(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, " onDestroy: nothing... ");
    }

    protected void registerActivityToService(boolean register) {
        if (ConnectionService.getInstance() != null) {
            Message msg = ConnectionService.getInstance().getHandler().obtainMessage();
            msg.what = MSG_REGISTER_ACTIVITY;
            msg.obj = this;
            msg.arg1 = register ? 1 : 0;
            ConnectionService.getInstance().getHandler().sendMessage(msg);
        }
    }

    /**
     * post send msg to service to handle it in background.
     */
    public void pushOutMessage(String jsonstring) {
        Log.d(TAG, "pushOutMessage : " + jsonstring);
        Message msg = ConnectionService.getInstance().getHandler().obtainMessage();
        msg.what = MSG_PUSHOUT_DATA;
        msg.obj = jsonstring;
        ConnectionService.getInstance().getHandler().sendMessage(msg);
    }

    /**
     * show the msg in chat fragment, update view must be from ui thread.
     */
    public void showMessage(final MessageRow row) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "showMessage : " + row.mMsg);
                if (mChatFrag != null) {
                    mChatFrag.appendChatMessage(row);
                }
            }
        });
    }
}
