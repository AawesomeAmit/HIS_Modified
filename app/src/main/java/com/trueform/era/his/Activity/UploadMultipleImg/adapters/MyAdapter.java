package com.trueform.era.his.Activity.UploadMultipleImg.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.trueform.era.his.Activity.UploadMultipleImg.ImageCompress;
import com.trueform.era.his.R;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import id.zelory.compressor.Compressor;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<String> list = new ArrayList<>();
    private Context context;
    private ImageCompress mImageCompress;
    Bitmap bitmap;

    public MyAdapter(Context context) {
        this.context = context;
    }

    public void addImage(ArrayList<String> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.image, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Uri imageUri = Uri.fromFile(new File(list.get(position)));// For files on device
        String mImageCompress=new ImageCompress(context).compressImage(list.get(position));

        File f = new File(mImageCompress);
       // Bitmap bitmap;
        if (f.getAbsolutePath().endsWith("mp4")) {
            ((Holder) holder).play.setVisibility(View.VISIBLE);
            bitmap = ThumbnailUtils.createVideoThumbnail(f.getAbsolutePath(), MediaStore.Video.Thumbnails.MINI_KIND);
        } else {
            Compressor.getDefault(context)
                    .compressToFileAsObservable(f)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<File>() {
                        @Override
                        public void call(File file) {
                            //Bitmap bitmap;
                            ((Holder) holder).play.setVisibility(View.GONE);
                            bitmap = new BitmapDrawable(context.getResources(), file.getAbsolutePath()).getBitmap();
                            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
                            final float roundPx = (float) bitmap.getWidth() * 0.06f;
                            roundedBitmapDrawable.setCornerRadius(roundPx);
                            ((Holder) holder).iv.setImageDrawable(roundedBitmapDrawable);


                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                        }
                    });
            //((Holder) holder).play.setVisibility(View.GONE);
            //bitmap = new BitmapDrawable(context.getResources(), f.getAbsolutePath()).getBitmap();
        }
      /*  RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        final float roundPx = (float) bitmap.getWidth() * 0.06f;
        roundedBitmapDrawable.setCornerRadius(roundPx);*/



       // ((Holder) holder).iv.setImageDrawable(roundedBitmapDrawable);
        ((Holder) holder).iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(f.getAbsolutePath()));
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        intent.setDataAndType(Uri.parse(f.getAbsolutePath()), Files.probeContentType(f.toPath()));
                    } else {
                        intent.setData(Uri.parse(f.getAbsolutePath()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                context.startActivity(intent);
            }
        });
        /*Bitmap scaled = com.fxn.utility.Utility.getScaledBitmap(
            500f, com.fxn.utility.Utility.rotate(d,list.get(position).getOrientation()));*/

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public ImageView iv, play;


        public Holder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            play = itemView.findViewById(R.id.play);

        }
    }
}
