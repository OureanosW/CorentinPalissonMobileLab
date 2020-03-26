package com.example.mobilelab.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mobilelab.OnFactListener;
import com.example.mobilelab.R;
import com.example.mobilelab.data_layer.DataViewModel;
import com.example.mobilelab.business_layer.FactsAdapter;

import java.util.ArrayList;


/**
 * ListFragment class will search for cats' facts, using the DataViewModel and display them
 * via the FactsAdapter.
 * When one fact is clicked it provides the fact as argument to the DetailFragment.
 *
 */


public class ListFragment extends Fragment implements OnFactListener {

    private View rootView;
    private TextView catTitle;
    private ProgressBar progressBarCatFacts;
    private RecyclerView recyclerViewCatsFacts;
    private FactsAdapter adapter = new FactsAdapter();

    private OnFragmentInteractionListener mListener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        catTitle = rootView.findViewById(R.id.textViewCatTitle);
        progressBarCatFacts = rootView.findViewById(R.id.progressBarCatsFacts);
        recyclerViewCatsFacts = rootView.findViewById(R.id.recyclerViewCatsFacts);
        recyclerViewCatsFacts.setAdapter(adapter);

        if(recyclerViewCatsFacts == null)
        {
            System.err.println("RecyclerView non trouve");
        }else {
            System.err.println("RecyclerView trouve");
        }


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter.setListener(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        if(recyclerViewCatsFacts == null)
        {
            System.err.println("RecyclerView non trouvee");
        }else {
            recyclerViewCatsFacts.setLayoutManager(layoutManager);
        }


        final DataViewModel model = new ViewModelProvider(this).get(DataViewModel.class);
        model.getFact().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> facts) {
                if(adapter.getItemCount() < 1)
                {
                    adapter.addFacts(facts);
                }
                catTitle.setText(getString(R.string.cats_facts_title));
                progressBarCatFacts.setVisibility(View.INVISIBLE);
                recyclerViewCatsFacts.setVisibility(View.VISIBLE);
                adapter.printFacts();

            }

        });
    }

    @Override
    public void onFactClicked(String fact) {
        Navigation.findNavController(rootView).navigate(ListFragmentDirections.actionListFragmentToDetailFragment(fact));

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
