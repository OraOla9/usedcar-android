package com.oraola.usedcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oraola.usedcar.CarSoidViewActivity;
import com.oraola.usedcar.CarViewActivity;
import com.oraola.usedcar.R;
import com.oraola.usedcar.bean.Car;

import java.text.SimpleDateFormat;
import java.util.List;

public class CarSoldAdapter extends RecyclerView.Adapter<CarSoldAdapter.CarViewHolder> {
    private List<Car> carList;
    private Context context;

    public CarSoldAdapter(List<Car> carList) {
        this.carList = carList;
    }

    public CarSoldAdapter(List<Car> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @NonNull
    @Override
    public CarSoldAdapter.CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new CarSoldAdapter.CarViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {

        Car car = carList.get(position);

        // 处理品牌和型号，防止字段为 null
        String brandModel = (car.getBrand() != null ? car.getBrand() : "Unknown") +
                (car.getModel() != null ? car.getModel() : " Model");

        holder.brandTextView.setText(brandModel);

        // 处理价格，防止字段为 null
        String price = "￥" + car.getSalePrice();

        holder.priceTextView.setText(price);

        // 处理日期，防止字段为 null
        String date = car.getAcquisitionDate() != null ?
                new SimpleDateFormat("yyyy-MM-dd").format(car.getSaleDate()) :
                "Unknown Date";
        holder.createTimeTextView.setText(date);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CarSoidViewActivity.class);
            intent.putExtra("carId", car.getCarId());
            Log.d("vbvbvbb",Integer.toString(car.getCarId()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        TextView brandTextView;
        TextView priceTextView;
        TextView createTimeTextView;


        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.brand);
            priceTextView = itemView.findViewById(R.id.price);
            createTimeTextView = itemView.findViewById(R.id.create_time);
        }
    }
}
