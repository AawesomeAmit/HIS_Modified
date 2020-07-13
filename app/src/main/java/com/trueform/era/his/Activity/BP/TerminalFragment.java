package com.trueform.era.his.Activity.BP;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.trueform.era.his.Activity.DeviceControlActivity;
import com.trueform.era.his.Activity.ScannerActivity;
import com.trueform.era.his.R;
import com.trueform.era.his.Utils.ConnectivityChecker;
import com.trueform.era.his.Utils.RetrofitClient;
import com.trueform.era.his.Utils.SharedPrefManager;
import com.trueform.era.his.Utils.Utils;
import com.trueform.era.his.view.BaseFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class TerminalFragment extends BaseFragment implements ServiceConnection, SerialListener {

    private enum Connected { False, Pending, True }

    private String deviceAddress;
    private String newline = "\r\n";
    Context context;
    static String sys, dias, pulse;
    private TextView receiveText, btnSaveData;
    private SerialService service;
    private boolean initialStart = true;
    private Connected connected = Connected.False;

    /*
     * Lifecycle
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        deviceAddress = getArguments().getString("device");
    }

    @Override
    public void onDestroy() {
        if (connected != Connected.False)
            disconnect();
        getActivity().stopService(new Intent(getActivity(), SerialService.class));
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(service != null)
            service.attach(this);
        else
            getActivity().startService(new Intent(getActivity(), SerialService.class)); // prevents service destroy on unbind from recreated activity caused by orientation change
    }

    @Override
    public void onStop() {
        if(service != null && !getActivity().isChangingConfigurations())
            service.detach();
        super.onStop();
    }

    @SuppressWarnings("deprecation") // onAttach(context) was added with API 23. onAttach(activity) works for all API versions
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getActivity().bindService(new Intent(getActivity(), SerialService.class), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onDetach() {
        try { getActivity().unbindService(this); } catch(Exception ignored) {}
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(initialStart && service !=null) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        service = ((SerialService.SerialBinder) binder).getService();
        service.attach(this);
        if(initialStart && isResumed()) {
            initialStart = false;
            getActivity().runOnUiThread(this::connect);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        service = null;
    }

    /*
     * UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terminal, container, false);
        receiveText = view.findViewById(R.id.receive_text);                          // TextView performance decreases with number of spans
//        btnSaveData = view.findViewById(R.id.btnSaveData);
        receiveText.setTextColor(getResources().getColor(R.color.black)); // set as default color to reduce number of spans
        receiveText.setMovementMethod(ScrollingMovementMethod.getInstance());
        TextView sendText = view.findViewById(R.id.send_text);
        View sendBtn = view.findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(v -> send(sendText.getText().toString()));
        context=view.getContext();
        /*btnSaveData.setOnClickListener(view1 -> {
//            displayData(spo2, pulse);
            JSONArray dtTableArray = new JSONArray();
            try {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("vitalId", "3");
                jsonObject1.put("vitalValue", pulse1);
                dtTableArray.put(jsonObject1);
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("vitalId", "56");
                jsonObject2.put("vitalValue", spo21);
                dtTableArray.put(jsonObject2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dtTableArray.length() != 0) {
                if (ConnectivityChecker.checker(context)) {
                    saveBluetoothVital(dtTableArray.toString());
                } else {
                    Toast.makeText(context, "Network connection not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        /*btnSaveData.setOnClickListener(view1 -> {
//            displayData(spo2, pulse);
            JSONArray dtTableArray = new JSONArray();
            try {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("vitalId", "4");
                jsonObject1.put("vitalValue", sys);
                dtTableArray.put(jsonObject1);
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("vitalId", "6");
                jsonObject2.put("vitalValue", dias);
                dtTableArray.put(jsonObject2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dtTableArray.length() != 0) {
                if (ConnectivityChecker.checker(context)) {
                    saveBluetoothVital(dtTableArray.toString());
                } else {
                    Toast.makeText(context, "Network connection not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        return view;
    }

    /*public void saveBluetoothVital(String dt) {
        Utils.showRequestDialog(context);
        Log.v("hitApi:", "http://182.156.200.179:201/api/Prescription/saveVitals");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        JSONArray array = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("PID", SharedPrefManager.getInstance(context).getPid());
            jsonObject.put("headID", SharedPrefManager.getInstance(context).getHeadID());
            jsonObject.put("entryDate", format.format(today));
            jsonObject.put("subDeptID", SharedPrefManager.getInstance(context).getSubDept().getId());
            jsonObject.put("isFinalDiagnosis", false);
            jsonObject.put("ipNo", SharedPrefManager.getInstance(context).getIpNo());
            jsonObject.put("userID", SharedPrefManager.getInstance(context).getUser().getUserid());
            jsonObject.put("consultantName", "");
            jsonObject.put("vitals", dt);
            Log.v("hitApiArr", String.valueOf(jsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(RetrofitClient.BASE_URL + "Prescription/saveVitals")
                .addHeaders("accessToken", SharedPrefManager.getInstance(context).getUser().getAccessToken())
                .addHeaders("userID", SharedPrefManager.getInstance(context).getUser().getUserid().toString())
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Vitals saved successfully", Toast.LENGTH_SHORT).show();
//                        bind();
                        Utils.hideDialog();
                    }

                    @Override
                    public void onError(ANError error) {
                        if (error.getErrorCode() != 0) {
                            Log.d("onError errorCode ", "onError errorCode : " + error.getErrorCode());
                            Log.d("onError errorBody", "onError errorBody : " + error.getErrorBody());
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        } else {
                            Log.d("onError errorDetail", "onError errorDetail : " + error.getErrorDetail());
                        }
                        Toast.makeText(context, "Network error", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
    }*/
    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_terminal, menu);
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear) {
            receiveText.setText("");
            return true;
        } else if (id ==R.id.newline) {
            String[] newlineNames = getResources().getStringArray(R.array.newline_names);
            String[] newlineValues = getResources().getStringArray(R.array.newline_values);
            int pos = java.util.Arrays.asList(newlineValues).indexOf(newline);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Newline");
            builder.setSingleChoiceItems(newlineNames, pos, (dialog, item1) -> {
                newline = newlineValues[item1];
                dialog.dismiss();
            });
            builder.create().show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }*/

    /*
     * Serial + UI
     */
    private void connect() {
        try {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceAddress);
            status("connecting...");
            connected = Connected.Pending;
            SerialSocket socket = new SerialSocket(getActivity().getApplicationContext(), device);
            service.connect(socket);
        } catch (Exception e) {
            onSerialConnectError(e);
        }
    }

    private void disconnect() {
        connected = Connected.False;
        service.disconnect();
    }

    private void send(String str) {
        if(connected != Connected.True) {
            Toast.makeText(getActivity(), "not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            SpannableStringBuilder spn = new SpannableStringBuilder(str+'\n');
            spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            receiveText.append(spn);
            byte[] data = (str + newline).getBytes();
            service.write(data);
        } catch (Exception e) {
            onSerialIoError(e);
        }
    }
    private void receive(byte[] data) {

        Log.v("bpdata", new String(data));
        if (data.length > 20) {
            String result = new String(data);
            String validResult = "";
            String[] da = result.split("rx");
            String[] data1 = da[1].split("\r");
            validResult = data1[0];
            String fg=validResult.substring(0, 5);
            int dfd=Integer.parseInt(validResult.substring(0, 5), 16);
            int sys1 = Integer.parseInt(String.valueOf(Integer.parseInt(validResult.substring(0, 5), 16))) / 8;
            receiveText.append(newline+"Systolic: "+ sys1);
            int dias1 = Integer.parseInt(String.valueOf(Integer.parseInt(validResult.substring(5, 9), 16))) / 8;
            receiveText.append(newline+"Diastolic: "+ dias1);
            int pulse1 = Integer.parseInt(String.valueOf(Integer.parseInt(validResult.substring(9, 12), 16)));
            receiveText.append(newline+"Pulse: "+ pulse1 + newline);
            sys= String.valueOf(sys1);
            dias= String.valueOf(dias1);
            pulse= String.valueOf(pulse1);
        }
        receiveText.append(new String(data));
    }

    private void status(String str) {
        SpannableStringBuilder spn = new SpannableStringBuilder(str+'\n');
        spn.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 0, spn.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        receiveText.append(spn);
    }

    /*
     * SerialListener
     */
    @Override
    public void onSerialConnect() {
        status("connected");
        connected = Connected.True;
    }

    @Override
    public void onSerialConnectError(Exception e) {
        status("connection failed: " + e.getMessage());
        disconnect();
    }

    @Override
    public void onSerialRead(byte[] data) {
        receive(data);
    }

    @Override
    public void onSerialIoError(Exception e) {
        status("connection lost: " + e.getMessage());
        disconnect();
    }

}
