package com.his.android.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.his.android.Adapter.RecyclerTouchListener;
import com.his.android.Adapter.ViewNotificationAdp;
import com.his.android.R;
import com.his.android.Response.ViewNotificationResp;
import com.his.android.Utils.ClickListener;
import com.his.android.Utils.RecyclerItemTouchHelper;
import com.his.android.Utils.RetrofitClient;
import com.his.android.Utils.SharedPrefManager;
import com.his.android.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationList extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    TextView txtDrName, txtDept, txtPName, txtRange, txtDepartment, txtGender, txtAge, txtPid, txtDName, txtNote, img, txtResult;
    RecyclerView rView;
    LinearLayout llRange;
    ViewNotificationAdp notificationAdp;
    List<ViewNotificationResp> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        txtDept = findViewById(R.id.txtDept);
        txtDrName = findViewById(R.id.txtDrName);
        img = findViewById(R.id.img);
        rView = findViewById(R.id.rView);
        @SuppressLint("InflateParams") View popupView = getLayoutInflater().inflate(R.layout.popup_notification, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        txtPName = popupView.findViewById(R.id.txtPName);
        txtRange = popupView.findViewById(R.id.txtRange);
        txtPid = popupView.findViewById(R.id.txtPid);
        txtAge = popupView.findViewById(R.id.txtAge);
        llRange = popupView.findViewById(R.id.llRange);
        txtGender = popupView.findViewById(R.id.txtGender);
        txtDepartment = popupView.findViewById(R.id.txtDepartment);
        txtResult = popupView.findViewById(R.id.txtResult);
        txtDName = popupView.findViewById(R.id.txtDName);
        txtNote = popupView.findViewById(R.id.txtNote);
        notificationList=new ArrayList<>();
        notificationAdp=new ViewNotificationAdp(this, notificationList);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        txtDrName.setText(SharedPrefManager.getInstance(this).getUser().getDisplayName());
        txtDept.setText(SharedPrefManager.getInstance(this).getSubDept().getSubDepartmentName());
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(notificationAdp);
        loadData();


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rView);


        rView.addOnItemTouchListener(new RecyclerTouchListener(this, rView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(NotificationList.this, NotificationDetail.class);
                intent.putExtra("nType", notificationList.get(position).getNotificationTypeID().toString());
                intent.putExtra("title", notificationList.get(position).getNotificationTitle());
                intent.putExtra("nId", notificationList.get(position).getNotificationID().toString());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        img.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(NotificationList.this, img);
            menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
            menu.setOnMenuItemClickListener(item -> {
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().logOut(SharedPrefManager.getInstance(NotificationList.this).getUser().getAccessToken(), SharedPrefManager.getInstance(NotificationList.this).getUser().getUserid().toString(), SharedPrefManager.getInstance(NotificationList.this).getFCMToken(), String.valueOf(SharedPrefManager.getInstance(NotificationList.this).getUser().getUserid()));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Utils.showRequestDialog(NotificationList.this);
                        if (response.isSuccessful()) {
                            Toast.makeText(NotificationList.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(NotificationList.this).clear();
                            Intent intent = new Intent(NotificationList.this, MainActivity.class);
                            startActivity(intent);
                        }
                        Utils.hideDialog();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(NotificationList.this, "Network problem!", Toast.LENGTH_SHORT).show();
                        Utils.hideDialog();
                    }
                });
                return true;
            });
            menu.show();
        });
    }

    public void loadData() {
        Utils.showRequestDialog(NotificationList.this);
        Call<List<ViewNotificationResp>> call = RetrofitClient.getInstance().getApi().getNotificationList(SharedPrefManager.getInstance(this).getUser().getAccessToken(), SharedPrefManager.getInstance(this).getUser().getUserid().toString(), SharedPrefManager.getInstance(this).getUser().getUserid());
        call.enqueue(new Callback<List<ViewNotificationResp>>() {
            @Override
            public void onResponse(Call<List<ViewNotificationResp>> call, Response<List<ViewNotificationResp>> response) {
                Utils.showRequestDialog(NotificationList.this);
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        notificationList.addAll(response.body());
                        if (notificationList.size() > 0)
                            notificationAdp.notifyDataSetChanged();
                        else
                            Toast.makeText(NotificationList.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                }
                Utils.hideDialog();
            }

            @Override
            public void onFailure(Call<List<ViewNotificationResp>> call, Throwable t) {
                Utils.hideDialog();
                Toast.makeText(NotificationList.this, "Network error!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotificationList.this, PreDashboard.class));
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ViewNotificationAdp.RecyclerViewHolder) {
            // get the removed item name to display it in snack bar
            String name = notificationList.get(viewHolder.getAdapterPosition()).getNotificationTitle();

            // backup of removed item for undo purpose
            final ViewNotificationResp deletedItem = notificationList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
//            notificationAdp.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(rView, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", view -> {

                // undo is selected, restore the deleted item
//                    notificationAdp.restoreItem(deletedItem, deletedIndex);
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}