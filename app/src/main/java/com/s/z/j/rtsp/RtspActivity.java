package com.s.z.j.rtsp;

/**
 * Created by Administrator on 2017-07-10.
 */

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import com.s.z.j.R;

public class RtspActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    Button playButton;
    VideoView videoView;
    EditText rtspUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_rtsp);
        rtspUrl = (EditText) this.findViewById(R.id.url);
        playButton = (Button) this.findViewById(R.id.start_play);
        playButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                PlayRtspStream(rtspUrl.getEditableText().toString());
            }
        });
        videoView = (VideoView) this.findViewById(R.id.rtsp_player);
    }

    //play rtsp stream
    private void PlayRtspStream(String rtspUrl) {
        videoView.setVideoURI(Uri.parse(rtspUrl));
        videoView.requestFocus();
        videoView.start();
    }
}