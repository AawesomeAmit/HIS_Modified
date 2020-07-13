package com.trueform.era.his.Activity.BP;

interface SerialListener {
    void onSerialConnect();
    void onSerialConnectError (Exception e);
    void onSerialRead(byte[] data);
    void onSerialIoError      (Exception e);
}
