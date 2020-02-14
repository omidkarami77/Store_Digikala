package com.example.store_digikala.categories;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store_digikala.R;
import com.example.store_digikala.model.Categories;
import com.example.store_digikala.network.Api;
import com.example.store_digikala.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesSeperatorFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private static final String ARG_GALLERY = "gallery_fragment";

    public static CategoriesSeperatorFragment newInstance() {

        Bundle args = new Bundle();

        CategoriesSeperatorFragment fragment = new CategoriesSeperatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public CategoriesSeperatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery_seperator, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_gallery_list);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        getApi().getCategories().enqueue(new Callback<List<Categories>>() {
            @Override
            public void onResponse(Call<List<Categories>> call, Response<List<Categories>> response) {
                if (response.isSuccessful()) {
                    List<Categories> categoriesList = response.body();
                    mAdapter = new GalleryAdapter(categoriesList);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Categories>> call, Throwable t) {
                Toast.makeText(getActivity(), "your gallery is fail :(", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private Api getApi() {
        return RetrofitClientInstance.getRetrofitInstance().create(Api.class);
    }


    private class GalleryViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private Categories mCategories;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.item_gallery_list_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_list_container, CategoriesProductFragment.newInstance(mCategories.getId())).commit();
                }
            });
        }


        public void bind(Categories categories) {
            mCategories = categories;
            if (categories.getName() != null) {
                mTextView.setText(categories.getName());
            }
        }
    }


    private class GalleryAdapter extends RecyclerView.Adapter<GalleryViewHolder> {

        public void setmCategories(List<Categories> mCategories) {
            this.mCategories = mCategories;
        }

        public GalleryAdapter(List<Categories> mCategories) {
            this.mCategories = mCategories;
        }

        List<Categories> mCategories;

        @NonNull
        @Override
        public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_gallery_list, parent, false);
            return new GalleryViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
            Categories categories = mCategories.get(position);
            holder.bind(categories);
        }

        @Override
        public int getItemCount() {
            return mCategories.size();
        }
    }
}
