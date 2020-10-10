package com.his.android.Activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;
import com.his.android.R;
import com.his.android.Utils.Utils;
import com.his.android.view.BaseActivity;
import java.util.Objects;

public class PlayChatMsg extends BaseActivity {
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_chat_msg);
        videoView = findViewById(R.id.vView);
        Utils.showRequestDialog(mActivity);
        MediaController mediaController = new MediaController(this);
        Uri uri = Uri.parse(getIntent().getExtras().getString("filename"));
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.setOnInfoListener((mediaPlayer, i, i1) -> {
            if (i == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                Utils.hideDialog();
            }
            return false;
        });
        videoView.setOnPreparedListener(mp -> {
            Utils.hideDialog();
            Log.v("finished", "finished");
            videoView.start();
        });
    }
}