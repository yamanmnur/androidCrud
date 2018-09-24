package id.co.github.yamanmnur.belajarcrud;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class ListPegawaiAdapter extends RecyclerView.Adapter<ListPegawaiAdapter.ViewHolder> {

    private ArrayList<DataPegawai> dataPegawai;
    public ListPegawaiAdapter(ArrayList<DataPegawai> dataPegawai){
        this.dataPegawai = dataPegawai;

    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pegawai_root,parent,false );
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataPegawai data = dataPegawai.get(position);
        holder.mNama.setText(data.getNamaPegawai());
        holder.mPosisi.setText(data.getPosisiPegawai());
    }

    @Override
    public int getItemCount() {
        return dataPegawai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mNama, mPosisi;

        public ViewHolder(View itemView){

            super(itemView);

            mNama = itemView.findViewById(R.id.textListNamaPegawai);
            mPosisi =  itemView.findViewById(R.id.textListPosisi);
        }

    }
}
