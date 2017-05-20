package hireapro.himanshu.hireapro;

/**
 * Created by root on 20/5/17.
 */



    import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.RatingBar;
    import android.widget.TextView;

import java.util.List;

    public class SearchProfessionalAdapter extends RecyclerView.Adapter<SearchProfessionalAdapter.MyViewHolder> {

        private List<Professional> ProfessionalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView profilePictureImageView;
            public TextView proNameTextView,jobTypeTextView,distanceTextView,locationTextView,baseRateTextView;
            public RatingBar proRating;

            public MyViewHolder(View view) {
                super(view);
                profilePictureImageView= (ImageView) view.findViewById(R.id.profile_pic_search_result_row_imageview);
                proNameTextView = (TextView) view.findViewById(R.id.name_search_result_row_textview);
                jobTypeTextView = (TextView) view.findViewById(R.id.job_search_result_row_textview);
                distanceTextView = (TextView) view.findViewById(R.id.distance_search_result_row_textview);
                locationTextView = (TextView) view.findViewById(R.id.location_search_result_row_textview);
                baseRateTextView = (TextView) view.findViewById(R.id.base_rate_search_result_row_textview);
                proRating = (RatingBar) view.findViewById(R.id.rating_result_row_ratingbar);
            }
        }


        public SearchProfessionalAdapter(List<Professional> ProfessionalList) {
            this.ProfessionalList  = ProfessionalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.search_pro_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Professional Professional = ProfessionalList.get(position);
            holder.profilePictureImageView.setImageResource(R.drawable.catering_green);
            holder.proNameTextView.setText(Professional.getName());
            holder.jobTypeTextView.setText(Professional.getJob());
            holder.distanceTextView.setText(Professional.getDistanceFromUser()+" Km(s) Away");
            holder.locationTextView.setText(Professional.getAddress());
            holder.baseRateTextView.setText("₹ " + Professional.getBaseRate()+" per hour");
        }

        @Override
        public int getItemCount() {
            return ProfessionalList.size();
        }
    }



