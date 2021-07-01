
package com.example.qrapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {



    //vars
    private List<User> mCriminalsList;

    ListAdapter() {
        this.mCriminalsList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final User object = mCriminalsList.get(position);

        holder.txVw_type.setText(object.getTitle());
        holder.txVw_value.setText(object.getValue());

    }
    void setItems(List<User> users) {
        mCriminalsList = users;
        notifyDataSetChanged();
    }
    void setItem(User model) {
        mCriminalsList.add(model);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCriminalsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txVw_type;
        private final TextView txVw_value;

        ViewHolder(View itemView) {
            super(itemView);


            txVw_type = itemView.findViewById(R.id.itemDetails_txVw_type);
            txVw_value = itemView.findViewById(R.id.itemDetails_txVw_value);


        }
    }
}










//package com.example.qrapp;
//
//import java.util.List;
//
//import android.app.Activity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//public class ListAdapter extends ArrayAdapter {
//
//    private Activity mContext;
//    List<User> userList;
//
//    public ListAdapter(Activity mContext, List<User> userList){
//        super(mContext, R.layout.activity_retrieve_data_,userList);
//        this.mContext = mContext;
//        this.userList = userList;
//
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        LayoutInflater inflater = mContext.getLayoutInflater();
//        View listItemView = inflater.inflate(R.layout.activity_retrieve_data_, null, true);
//
//        TextView type = listItemView.findViewById(R.id.itemDetails_txVw_type);
//        TextView value = listItemView.findViewById(R.id.itemDetails_txVw_value);
//        User user = userList.get(position);
//        type.setText(user.getTitle());
//        value.setText(user.getValue());
//
////        TextView name1 = listItemView.findViewById(R.id.name1);
////        TextView num = listItemView.findViewById(R.id.num);
////        TextView blood1 = listItemView.findViewById(R.id.blood1);
////        TextView num1 = listItemView.findViewById(R.id.num1);
////        TextView num2 = listItemView.findViewById(R.id.num2);
////        TextView num3 = listItemView.findViewById(R.id.num3);
////        TextView id1 = listItemView.findViewById(R.id.id1);
////        TextView age = listItemView.findViewById(R.id.age1);
////        TextView email = listItemView.findViewById(R.id.email1);
////        TextView medicine1 = listItemView.findViewById(R.id.medicine1);
////        TextView diseases = listItemView.findViewById(R.id.diseases);
////        TextView surgeries = listItemView.findViewById(R.id.surgeries);
////
////        User user = userList.get(position);
//
////        name1.setText(user.fullname);
////        num.setText(user.phone);
////        blood1.setText(user.bloodtype);
////        num1.setText(user.phone1);
////        num2.setText(user.phone2);
////        num3.setText(user.phone3);
////        id1.setText(user.id);
////        age.setText(user.age);
////        email.setText(user.email);
////        medicine1.setText(user.medicine);
////        diseases.setText(user.diseases);
////        surgeries.setText(user.surgeries);
//
//        return listItemView;
//
//    }
//}
