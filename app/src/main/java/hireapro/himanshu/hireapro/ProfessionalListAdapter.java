package hireapro.himanshu.hireapro;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by root on 17/5/17.
 */

public class ProfessionalListAdapter extends RecyclerView.Adapter<ProfessionalListAdapter.MyViewHolder> {

    LayoutInflater layoutInflater;
    private List<ProfessionalType> professionalTypeList;


    public ProfessionalListAdapter(List<ProfessionalType> professionalType) {
        this.professionalTypeList = professionalTypeList;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pro_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProfessionalType professionalType = professionalTypeList.get(position);
        holder.proTypeTextView.setText("f");
        holder.protypeImageView.setImageResource(R.drawable.catering_blue);
        Log.d("Does Image set ?","Yes");
    }

    @Override
    public int getItemCount() {
        return professionalTypeList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView proTypeTextView;
        ImageView protypeImageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            protypeImageView = (ImageView) itemView.findViewById(R.id.pro_type_imageview);
            proTypeTextView = (TextView) itemView.findViewById(R.id.pro_type_textview);
        }
    }


}
