package in.askdial.askdialsalescrm.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.askdial.askdialsalescrm.R;
import in.askdial.askdialsalescrm.main.My_Coustmers;
import in.askdial.askdialsalescrm.values.DetailsValue;
import in.askdial.askdialsalescrm.values.FunctionCalls;

/**
 * Created by Chetan Gani on 2/13/2017.
 */

public class My_CustomerAdapters extends RecyclerView.Adapter<My_CustomerAdapters.CustomerViewHolder> {

    private static final int REQUEST_FOR_ACTIVITY_CODE = 1;
    ArrayList<DetailsValue> arrayList = new ArrayList<DetailsValue>();
    My_Coustmers context;
    String ContextView, User_ID, Device, PrinterType;
    DetailsValue details;
    FunctionCalls functionCalls = new FunctionCalls();


    public My_CustomerAdapters(My_Coustmers context, ArrayList<DetailsValue> arrayList, String user_ID) {
        this.context = context;
        this.arrayList = arrayList;
        this.User_ID = user_ID;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerdetails_cardview, parent, false);
        CustomerViewHolder viewHolder = new CustomerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        details = arrayList.get(position);
        holder.tv_cust_name.setText(details.getC_Name());
        holder.tv_cust_mobile.setText(details.getC_Mobile());
        holder.tv_cust_email.setText(details.getC_Email());
        holder.tv_cust_address.setText(details.getC_Addres());
        holder.tv_cust_notes.setText(details.getC_Notes());
        holder.tv_cust_notes2.setText(details.getC_Notes2());
        holder.tv_cust_leading_date.setText(details.getC_Date());
        holder.tv_cust_plan.setText(details.getC_PlanID());
        holder.tv_cust_price.setText(details.getC_Price());
        holder.tv_cust_date.setText(details.getC_AddedOn_Date());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_cust_name, tv_cust_mobile, tv_cust_email, tv_cust_address,tv_cust_notes,tv_cust_notes2,tv_cust_leading_date,tv_cust_plan, tv_cust_price,tv_cust_date;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_cust_name = (TextView) itemView.findViewById(R.id.txt_name);
            tv_cust_mobile = (TextView) itemView.findViewById(R.id.txt_mobile);
            tv_cust_email = (TextView) itemView.findViewById(R.id.txt_email);
            tv_cust_address = (TextView) itemView.findViewById(R.id.txt_address);
            tv_cust_notes= (TextView) itemView.findViewById(R.id.txt_notes);
            tv_cust_notes2= (TextView) itemView.findViewById(R.id.txt_notes2);
            tv_cust_leading_date= (TextView) itemView.findViewById(R.id.txt_leading_date);
            tv_cust_plan = (TextView) itemView.findViewById(R.id.txt_plan);
            tv_cust_price = (TextView) itemView.findViewById(R.id.txt_price);
            tv_cust_date= (TextView) itemView.findViewById(R.id.txt_date);
        }

        @Override
        public void onClick(View v) {
            /*int pos = getAdapterPosition();
            DetailsValue details = arrayList.get(pos);
            Intent intent = null;
            intent = new Intent(context, Appointment_Details.class);
            intent.putExtra("NAME", details.getVisitors_Name());
            intent.putExtra("MOBILE", details.getVisitors_Mobile());
            intent.putExtra("EMAIL", details.getVisitors_Email());
            intent.putExtra("TOMEET", details.getVisitors_tomeet());
            intent.putExtra("DATE", functionCalls.ConvertApointmentDate(details.getAppointmentDate()));
            intent.putExtra("TIME", details.getAppointmentTime());
            intent.putExtra("PURPOSE", details.getPurpose());
            intent.putExtra("LOCATION",details.getAppointmentLocation());
            intent.putExtra("FROM",details.getAppointmentFrom());
            ((Activity) context).startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);*/
        }
    }
}
