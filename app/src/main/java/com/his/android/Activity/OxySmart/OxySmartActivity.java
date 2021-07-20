package com.his.android.Activity.OxySmart;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.creative.FingerOximeter.FingerOximeter;
import com.creative.FingerOximeter.IFingerOximeterCallBack;
import com.creative.FingerOximeter.IPC60FCallBack;
import com.creative.base.BaseDate;
import com.his.android.Activity.OxySmart.Service.BLEManager;
import com.his.android.Activity.OxySmart.Service.BluetoothLeService;
import com.his.android.Activity.OxySmart.Service.ReaderBLE;
import com.his.android.Activity.OxySmart.Service.SenderBLE;
import com.his.android.R;

import java.util.ArrayList;
import java.util.List;

public class OxySmartActivity extends Activity {
    private static final String TAG = "frf";//"MainActivity";

    private FingerOximeter mFingerOximeter;
    private TextView tv_SPO2, tv_PR, tv_PI,tv_BlueState,tv_Version,tv_IR_LightLevel,tv_RED_LightLevel,tv_workStatus;
    private ImageView iv_Pulse, iv_Battery;

    private DrawThreadNW mDrawRunble;
    private Thread mDrawThread = null;
    private DrawPC300SPO2Rect mDrawSPO2Rect;
    private Thread mDrawSPO2RectThread;

    /** list for drawing spo2 rect */
    public static List<BaseDate.Wave> SPO_RECT = new ArrayList<BaseDate.Wave>();
    /** list for drawing spo2 wave */
    public static List<BaseDate.Wave> SPO_WAVE = new ArrayList<BaseDate.Wave>();

    private String[] pc60f_prResult;

    private BLEManager mManager;

    //----------- message -------------
    public static final byte MSG_DATA_SPO2_PARA = 0x01;
    public static final byte MSG_DATA_SPO2_WAVE = 0x02;
    public static final byte MSG_DATA_PULSE = 0x03;
    public static final byte RECEIVEMSG_PULSE_OFF = 0x04;
    public static final byte MSG_BLUETOOTH_STATE = 0x05;
    public static final byte MSG_PROBE_OFF = 0x06;
    public static final byte MSG_VERSION = 0x07;
    public static final byte MSG_WORK_STATUS = 0x08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxy_smart);

        initView();
        initBLE();
        android6_RequestLocation(this);

//
//        iniFileDir();
    }

    private void initView(){
        tv_BlueState = (TextView) findViewById(R.id.blueState);
        tv_SPO2 = (TextView) findViewById(R.id.realplay_spo2_spo2);
        tv_PR = (TextView) findViewById(R.id.realplay_spo2_pr);
        tv_PI = (TextView) findViewById(R.id.realplay_spo2_pi);
        tv_Version = (TextView) findViewById(R.id.tv_version);
        iv_Pulse = (ImageView) findViewById(R.id.realplay_spo2_pulse);
        iv_Battery = (ImageView) findViewById(R.id.realplay_spo2_battery);
        tv_IR_LightLevel = (TextView) findViewById(R.id.tv_IR_LightLevel);
        tv_RED_LightLevel = (TextView) findViewById(R.id.tv_RED_LightLevel);
        tv_workStatus = (TextView) findViewById(R.id.tv_workStatus);
        mDrawSPO2Rect = (DrawPC300SPO2Rect)findViewById(R.id.realplay_spo2_draw_rect);
        mDrawRunble = (DrawThreadNW) findViewById(R.id.realplay_spo2_draw_wave);

        pc60f_prResult = getResources().getStringArray(R.array.pr_result);

        //set handler for updating UI
        mDrawRunble.setmHandler(myHandler);

        findViewById(R.id.btnConn).setOnClickListener(v -> {
            if(mManager!=null){
                mManager.scanLeDevice(true);
            }
            showDeviceList();
        });
        findViewById(R.id.btnDiscon).setOnClickListener(v -> {
            if(mManager!=null){
                mManager.disconnect();
            }
        });

    }

    private String text1="",text2="";
    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_BLUETOOTH_STATE: {
                    tv_BlueState.setText((String) msg.obj);
                    //Log.d(TAG, (String) msg.obj);
                }
                break;
                case MSG_DATA_SPO2_PARA:{
                    //nStatus ->true false
                    //probe status ->true noraml, false off
                    Bundle bundle = msg.getData();
                    if (!bundle.getBoolean("nStatus")) {
                        myHandler.sendEmptyMessage(MSG_PROBE_OFF);
                    }
                    int nSpo2 = bundle.getInt("nSpO2");
                    int nPR = bundle.getInt("nPR");
                    float fPI = bundle.getFloat("fPI");
                    float b = bundle.getFloat("nPower");
                    int powerLevel = bundle.getInt("powerLevel");
                    //Log.d(TAG, "b："+b+",powerLevel:"+powerLevel);

                    int battery = 0;
//				if(b!=0){ //bybyMonitor nPower
//					if (b < 2.5f) {
//						battery = 0;
//					} else if (b < 2.8f) {
//						battery = 1;
//					} else if (b < 3.0f)
//						battery = 2;
//					else
//						battery = 3;
//				}else {
//					battery = powerLevel;
//				}

                    battery = powerLevel;

                    setBattery(battery);
                    setTVSPO2(nSpo2 + "");
                    setTVPR(nPR + "");
                    setTVPI(fPI+"");
                }
                break;
                case MSG_DATA_PULSE:{
                    showPulse(true);
                }
                break;
                case RECEIVEMSG_PULSE_OFF: {
                    showPulse(false);
                }
                break;
                case MSG_PROBE_OFF:{
                    Toast.makeText(OxySmartActivity.this, "probe off", Toast.LENGTH_SHORT).show();
                }
                break;
                case MSG_VERSION:{
                    Bundle bundle = (Bundle) msg.obj;
                    tv_Version.setText("hVer:"+bundle.getString("hVer")+" ,sVer:"+bundle.getString("sVer"));
                }
                break;
                case 1001:{
                    tv_IR_LightLevel.setText("IR:"+msg.arg1);
                    tv_RED_LightLevel.setText("RED:"+msg.arg2);
                }
                break;
                case MSG_WORK_STATUS:{
                    Bundle bundle = msg.getData();
                    if(bundle!=null){
                        String meassage = null;
                        int mode = bundle.getInt("mode");
                        if(mode == 1){//point measure mode
                            int pointMesureStep = bundle.getInt("pointMesureStep");
                            int param = bundle.getInt("param");
                            int pr = bundle.getInt("pr");
                            if(pointMesureStep==0){
                                text1=getString(R.string.idle);
                            }else if(pointMesureStep==1){
                                text2 = "";
                                text1=getString(R.string.ready);
                            }else if(pointMesureStep==2){
                                text1=getString(R.string.remain_time)+param;
                            }else if(pointMesureStep==3){
                                text1=getString(R.string.spo2Value)+param+getString(R.string.prValue)+pr;
                            }else if(pointMesureStep == 4){
                                if(param == 0x0a){
                                    param = 10;
                                }else if(param == 0xff){
                                    param = 11;
                                }
                                text2=getString(R.string.pr_result)+ pc60f_prResult[param];
                            }else {
                                Log.d(TAG, "finish");
                            }
                            meassage =getString(R.string.point_measure)+text1+text2;
                        }else if(mode == 2){//continuous measure mode
                            meassage = getString(R.string.continous);
                        }else if(mode == 3){//menu mode
                            meassage = getString(R.string.menu);
                        }
                        tv_workStatus.setText(meassage);
                    }
                }
                break;
                default:break;
            }
        }
    };


    /**
     * received FingerOximeter of data
     */
    class FingerOximeterCallBack implements IFingerOximeterCallBack, IPC60FCallBack {

        @Override
        public void OnGetSpO2Param(int nSpO2, int nPR, float fPI, boolean nStatus, int nMode, float nPower,int powerLevel) {
            Log.i(TAG, "OnGetSpO2Param nSpO2:"+nSpO2+",nPR:"+nPR);
            Message msg = myHandler.obtainMessage(MSG_DATA_SPO2_PARA);
            Bundle data = new Bundle();
            data.putInt("nSpO2", nSpO2);
            data.putInt("nPR", nPR);
            data.putFloat("fPI", fPI);
            data.putFloat("nPower", nPower);
            data.putBoolean("nStatus", nStatus);
            data.putInt("nMode", nMode);
            data.putInt("powerLevel", powerLevel);
            msg.setData(data);
            myHandler.sendMessage(msg);
            //Log.d(TAG, "nSpO2:"+nSpO2+",nPR:"+nPR+",fPI:"+fPI);
        }

        //spo2 sampling rate is 50hz, 5 wave data in a packet,
        //send 10 packet 1/s. param "waves" is 1 data packet
        @Override
        public void OnGetSpO2Wave(List<BaseDate.Wave> waves) {
            //Log.d(TAG, "wave.size:"+waves.size()); // size = 5
            SPO_RECT.addAll(waves);
            SPO_WAVE.addAll(waves);
        }

        @Override
        public void OnGetDeviceVer(String hardVer,String softVer,String deviceName) {
            Bundle bundle = new Bundle();
            bundle.putString("sVer", softVer);
            bundle.putString("hVer", hardVer);
            myHandler.obtainMessage(MSG_VERSION, bundle).sendToTarget();
        }

        @Override
        public void OnConnectLose() {
            myHandler.obtainMessage(MSG_BLUETOOTH_STATE, "connect lost").sendToTarget();
        }

        @Override
        public void onGetWorkStatus_60F(int mode,int pointMesureStep,int param,int pr) {
            Log.d(TAG, "60F mode:"+mode+",pointMesureStep:"+pointMesureStep+",param:"+param+",pr:"+pr);
            Message msg = myHandler.obtainMessage(MSG_WORK_STATUS);
            Bundle bundle = new Bundle();
            bundle.putInt("mode", mode);
            bundle.putInt("pointMesureStep", pointMesureStep);
            bundle.putInt("param", param);
            bundle.putInt("pr", pr);
            msg.setData(bundle);
            myHandler.sendMessage(msg);
        }

//		@Override
//		public void OnGetSpO2Param_BabyRedLight(int IR_LightLevel, int RED_LightLevel) {
//			myHandler.obtainMessage(1001, IR_LightLevel, RED_LightLevel).sendToTarget();
//		}

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, BLEManager.makeGattUpdateIntentFilter());
        startDraw();
    }

//	@Override
//	protected void onPause() {
//		super.onPause();
//		pauseDraw();
//	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopDraw();
        unregisterReceiver(mGattUpdateReceiver);
    }


    private void startDraw() {
        if (mDrawThread == null) {
            mDrawThread = new Thread(mDrawRunble, "DrawPOD_Thread");
            mDrawThread.start();
            mDrawSPO2RectThread = new Thread(mDrawSPO2Rect, "DrawPODRect_Thread");
            mDrawSPO2RectThread.start();
        } else if (mDrawRunble.isPause()) {
            mDrawRunble.Continue();
            mDrawSPO2Rect.Continue();
        }

    }

    private void pauseDraw() {
        if (mDrawThread != null && !mDrawRunble.isPause()) {
            mDrawRunble.Pause();
            mDrawSPO2Rect.Pause();
        }
    }

    private void stopDraw() {
        if (!mDrawRunble.isStop()) {
            mDrawRunble.Stop();
            mDrawSPO2Rect.Stop();
        }
        mDrawThread = null;
        mDrawSPO2RectThread = null;
    }

    /**
     * battery level
     */
    private int batteryRes[] = { R.drawable.battery_0, R.drawable.battery_1, R.drawable.battery_2,
            R.drawable.battery_3 };

    /** message: battery level is 0 */
    private static final int BATTERY_ZERO = 0x302;

    /**
     * set battery icon
     */
    private void setBattery(int battery) {
        iv_Battery.setImageResource(batteryRes[battery]);
        if (battery == 0) {
            if (!myHandler.hasMessages(BATTERY_ZERO)) {
                myHandler.sendEmptyMessage(BATTERY_ZERO);
            }
        } else {
            iv_Battery.setVisibility(View.VISIBLE);
            if (myHandler.hasMessages(BATTERY_ZERO))
                myHandler.removeMessages(BATTERY_ZERO);
        }
    }


    /**
     * set pulse flag
     */
    private void showPulse(boolean isShow) {
        if (isShow) {
            iv_Pulse.setVisibility(View.VISIBLE);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myHandler.sendEmptyMessage(RECEIVEMSG_PULSE_OFF);
                }
            }.start();
        } else {
            iv_Pulse.setVisibility(View.INVISIBLE);
        }
    }

    private void setTVSPO2(String data) {
        setTVtext(tv_SPO2, data);
    }

    private void setTVPR(String data) {
        setTVtext(tv_PR, data);
    }

    private void setTVPI(String data) {
        setTVtext(tv_PI, data);
    }

    private void setTVtext(TextView tv, String msg) {
        if (tv != null && !TextUtils.isEmpty(msg)) {
            tv.setText(msg);
        }
    }


    //-----------------  ble operation ---------------------------
    private BluetoothAdapter mBluetoothAdapter;

    private void initBLE(){
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported", Toast.LENGTH_SHORT).show();
        }

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        // 检查设备上是否支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "error bluetooth not supported", Toast.LENGTH_SHORT).show();
        }else{
            mBluetoothAdapter.enable();
            mManager = new BLEManager(this, mBluetoothAdapter);
        }
    }

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            // Log.d(TAG, "action->"+action);
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Toast.makeText(OxySmartActivity.this, "connected success", Toast.LENGTH_SHORT).show();
                tv_BlueState.setText("connected success");

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                if(mManager!=null){
                    mManager.closeService();
                }
                Toast.makeText(OxySmartActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                tv_BlueState.setText("disconnected");

                if (mFingerOximeter != null)
                    mFingerOximeter.Stop();
                mFingerOximeter = null;
//				stopWrite();

            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on
                // theuser interface.
                // showAllCharacteristic();

            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                //Toast.makeText(MainActivity.this,intent.getStringExtra(BluetoothLeService.EXTRA_DATA),Toast.LENGTH_SHORT).show();

            } else if (BluetoothLeService.ACTION_SPO2_DATA_AVAILABLE.equals(action)) {
                //byte[] data =intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                //Log.d(TAG, "MainActivity received:"+Arrays.toString(data));

            } else if (BluetoothLeService.ACTION_CHARACTER_NOTIFICATION.equals(action)) {
                Toast.makeText(OxySmartActivity.this, "send wave request", Toast.LENGTH_SHORT).show();
                startFingerOximeter();

            }else if(BLEManager.ACTION_FIND_DEVICE.equals(action)){
                tv_BlueState.setText("find device, start service");

            }else if(BLEManager.ACTION_SEARCH_TIME_OUT.equals(action)){
                tv_BlueState.setText("search time out!");
                if(progressBar!=null){
                    progressBar.setVisibility(View.GONE);
                }

            }else if(BLEManager.ACTION_START_SCAN.equals(action)){
                tv_BlueState.setText("discoverying");

            }
        }
    };

    private void startFingerOximeter(){
        if(BLEManager.mBleHelper!=null){
            mFingerOximeter = new FingerOximeter(new ReaderBLE(BLEManager.mBleHelper), new SenderBLE(BLEManager.mBleHelper), new FingerOximeterCallBack());
            mFingerOximeter.Start();
            mFingerOximeter.QueryDeviceVer();
            startDraw();

//			stopWrite();
//			writeTimeFile();
        }
    }


//	public void showAllCharacteristic() {
//		List<BluetoothGattService> services = mBluetoothLeService.getSupportedGattServices();
//	}


    /**
     * android6.0 Bluetooth, need to open location for bluetooth scanning
     * android6.0
     */
    private void android6_RequestLocation(final Context context){
        if (Build.VERSION.SDK_INT >= 23) {
            // BLE device need to open location
            if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
                    && !isGpsEnable(context)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("Prompt")
                        .setIcon(android.R.drawable.ic_menu_info_details)
                        .setMessage("Android6.0 need to open location for bluetooth scanning")
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        // startActivityForResult(intent,0);
                        context.startActivity(intent);
                    }
                });
                builder.show();
            }

            //request permissions
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))
                    Toast.makeText(context,"need to open location info for discovery bluetooth device in android6.0 version，otherwise find not！", Toast.LENGTH_LONG).show();

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }
        }

    }

    // whether or not location is open, 位置是否打开
    public final boolean isGpsEnable(final Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }



//    private void scrollLog(String message) {
//        Spannable colorMessage = new SpannableString(message + "\n");
//        colorMessage.setSpan(new ForegroundColorSpan(0xff0000ff), 0, message.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tv_spo2_hex.append(colorMessage);
//        Layout layout = tv_spo2_hex.getLayout();
//        if (layout != null) {
//            int scrollAmount = layout.getLineTop(tv_spo2_hex.getLineCount()) - tv_spo2_hex.getHeight();
//            if (scrollAmount > 0) {
//            	tv_spo2_hex.scrollTo(0, scrollAmount + tv_spo2_hex.getCompoundPaddingBottom());
//            } else {
//            	tv_spo2_hex.scrollTo(0, 0);
//            }
//        }
//    }


//--------------------------------------------------------------
//	private boolean isWrite = true;
//	private Thread mWriteTh;
//	private SimpleDateFormat sdf;
//	private BufferedWriter writer = null;
//	private File saveFile;
//
//	private void iniFileDir() {
//		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//		String filename = sdf.format(new Date())+".txt";
//		String sdcard = android.os.Environment.getExternalStorageDirectory().getPath();
//		try {
//			saveFile = createDir(sdcard+"/babyMonitor");
//			String savePath = saveFile.getPath() +"/"+ filename;
//			saveFile = new File(savePath);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
//
//	private void writeTimeFile(){
//		if(saveFile == null){
//			return;
//		}
//
//		isWrite = true;
//		mWriteTh = new Thread(){
//			@Override
//			public void run() {
//				try {
//					writer = new BufferedWriter(new FileWriter(saveFile, true));//utf-8
//					while(isWrite){
//						Log.e(TAG, "--- writeTimeFile2 --");
//						writer.write(sdf.format(new Date())+"\n");
//						try {
//							sleep(1000*60);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}finally{
//					if(writer != null){
//						try {
//							writer.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		};
//		mWriteTh.start();
//	}
//
//	private void stopWrite() {
//		if(mWriteTh!=null){
//			isWrite = false;
//			mWriteTh = null;
//		}
//	}
//
//    /**
//     * 递归创建文件夹
//     * @param path   文件夹路径
//     */
//    private File createDir(String path) throws Exception{
//        if (path == null || path.equals("")){
//        	new NullPointerException();
//        }
//        File file = new File(path);
//        if (file.exists())
//            return file;
//        File p = file.getParentFile();
//        if (!p.exists()) {
//            createDir(p.getPath());
//        }
//        if (file.mkdir())
//            return file;
//        return null;
//    }

    public static LeDeviceListAdapter mLeDeviceListAdapter;
    private ProgressBar progressBar;
    private void showDeviceList() {
        LayoutInflater layoutInflater = LayoutInflater.from(OxySmartActivity.this);
        View titleView = layoutInflater.inflate(R.layout.dialog_title, null);
        View contentView= layoutInflater.inflate(R.layout.dialog_list, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(OxySmartActivity.this);
        builder.setCustomTitle(titleView);
        builder.setView(contentView);
        final Dialog dialog = builder.show();
        ListView listView = (ListView) contentView.findViewById(R.id.lv_device);
        progressBar = (ProgressBar) titleView.findViewById(R.id.progressBar1);
        mLeDeviceListAdapter = new LeDeviceListAdapter(OxySmartActivity.this);
        listView.setAdapter(mLeDeviceListAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
            if(mManager!=null && device != null){
                mManager.scanLeDevice(false);
                mManager.myBLEConnect(device);
            }
            dialog.dismiss();
        });
        dialog.setOnDismissListener(dialog1 -> {
            if (mManager!=null) {
                mManager.scanLeDevice(false);
                mLeDeviceListAdapter.clear();
                mLeDeviceListAdapter = null;
            }
        });
    }
}