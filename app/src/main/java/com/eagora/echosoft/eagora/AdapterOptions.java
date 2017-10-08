package com.eagora.echosoft.eagora;

/**
 * Created by rosangela on 07/10/17.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterOptions extends RecyclerView.Adapter<AdapterOptions.MyViewHolder> {

        private List<OpcoesDoUsuario> userOpt;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView opcao;

            public MyViewHolder(View view) {
                super(view);
                opcao = (TextView) view.findViewById(R.id.txt);
            }
        }


        public AdapterOptions(List<OpcoesDoUsuario> optList) {
            this.userOpt = optList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_comece_viagem, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            OpcoesDoUsuario user= userOpt.get(position);
            holder.opcao.setText(user.getTitle());
        }

        @Override
        public int getItemCount() {
            return userOpt.size();
        }
    }

