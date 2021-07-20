package com.his.android.Activity.OxySmart.Service;

import com.creative.base.Isender;

import java.io.IOException;

public class SenderBLE implements Isender{
	
    private BLEHelper mHelper;

	public SenderBLE(BLEHelper helper) {
		mHelper = helper;
	}
	
	@Override
	public void send(byte[] d) throws IOException {
		mHelper.write(d);
	}

	@Override
	public void close() {
		mHelper = null;
	}

}
