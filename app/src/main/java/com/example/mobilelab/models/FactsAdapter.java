package com.example.mobilelab.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mobilelab.OnFactListener;
import com.example.mobilelab.R;

import java.util.ArrayList;

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.MyViewHolder> {
    public OnFactListener listener;
    private ArrayList<String> facts;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;


        public MyViewHolder(TextView v, final OnFactListener listener) {
            super(v);
            textView = v;


            this.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnFactClicked(textView.getText().toString());
                }
            });

        }
    }

    public FactsAdapter()
    {
        facts = new ArrayList<String>();
    }

    public FactsAdapter(String[] factsList)
    {
        for(int i = 0; i <= factsList.length;i++)
        {
            facts.add(factsList[i]);
        }
    }

    public FactsAdapter(ArrayList<String> factsList)
    {
        facts.addAll(factsList);
    }

    public void addFact(String fact)
    {
        facts.add(fact);
        notifyItemChanged(facts.size());
    }

    public void addFacts(ArrayList<String> fact)
    {
        facts.addAll(fact);
        notifyDataSetChanged();
    }

    public void printFacts()
    {
        for(int i = 0; i < facts.size(); i++)
        {
            System.err.println(i + " " + facts.get(i));
        }
    }


    @NonNull
    @Override
    public FactsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v, this.listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull FactsAdapter.MyViewHolder holder, int position) {
        System.err.println("Coucou onBindViewHolder");
        holder.textView.setText(facts.get(position));
    }

    @Override
    public int getItemCount() {
        return facts.size();
    }

    public void setListener(OnFactListener listener)
    {
        this.listener = listener;
    }


}
