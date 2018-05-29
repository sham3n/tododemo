package com.sammy.test.todo.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sammy.test.todo.R;
import com.sammy.test.todo.model.Task;
import com.sammy.test.todo.ui.home.adapter.HomeAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements HomeContract.HomeView {

    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.input)
    EditText mEditText;
    @BindView(R.id.empty_view)
    View mEmptyView;

    private HomeAdapter mAdapter;

    private HomeContract.HomePresenter mHomePresenter;

    public HomeFragment() {
        //needed by default
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        mEditText.setOnEditorActionListener((TextView textView, int actionId, KeyEvent event) -> {
            int result = actionId & EditorInfo.IME_MASK_ACTION;
            switch (result) {
                case EditorInfo.IME_ACTION_DONE:
                    onAddClicked();
                    return true;
            }

            return false;
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //start the presenter
        mHomePresenter.start();
    }

    @Override
    public void setPresenter(HomeContract.HomePresenter presenter) {
        mHomePresenter = presenter;
    }

    @Override
    public void showTaskList(@NotNull List<Task> taskList) {
        updateTaskList(taskList);
    }

    @Override
    public void showEmptyList() {
        updateTaskList(new ArrayList<>());
        showEmptyMessage(true);
    }

    @Override
    public void showEmptyMessage(boolean shouldShow) {
        mEmptyView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    private void updateTaskList(List<Task> tasks) {
        if(mAdapter == null) {
            mAdapter = new HomeAdapter(tasks, (Task task) -> {
                mHomePresenter.deleteTask(task);
            });

            mRecyclerView.setAdapter(mAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(layoutManager);
        } else {
            mAdapter.onNewData(tasks);
        }
    }

    @Override
    public void deleteTask(@NotNull Task task) {
        mAdapter.deleteTask(task);
    }

    @Override
    public void addTask(@NotNull Task task) {
        mAdapter.addTask(task);
    }

    @Override
    public void displayMessage(@StringRes int text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.add)
    void onAddClicked() {
        mHomePresenter.addItem(mEditText.getText().toString());
        mEditText.setText(null);
        //Hide keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * Listener for the recycler view interaction
     */
    public interface HomeItemListener {

        void deleteTask(Task task);
    }
}
