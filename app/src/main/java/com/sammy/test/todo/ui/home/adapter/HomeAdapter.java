package com.sammy.test.todo.ui.home.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sammy.test.todo.R;
import com.sammy.test.todo.model.Task;
import com.sammy.test.todo.ui.home.HomeFragment;
import com.sammy.test.todo.utils.TaskDiffUtilCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by sng
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_TASK = 1;
    private List<Task> mTasks;
    private HomeFragment.HomeItemListener mListener;

    public HomeAdapter(@NonNull List<Task> tasks, @Nullable HomeFragment.HomeItemListener listener) {
        //Create new list because we're modifying original one for delete/adds
        this.mTasks = new ArrayList<>(tasks);
        mListener = listener;
    }

    /**
     * This is for optimizing
     * @param newData
     */
    public void onNewData(List<Task> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new TaskDiffUtilCallback(newData, mTasks), false);
        this.mTasks.clear();
        this.mTasks.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }

    public void deleteTask(Task task) {
        int indexOfTask = mTasks.indexOf(task);
        mTasks.remove(task);
        notifyItemRemoved(indexOfTask);
    }

    public void addTask(Task task) {
        mTasks.add(task);
        notifyItemInserted(mTasks.size() - 1);
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_TASK;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if(viewType == VIEW_TYPE_TASK)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_TASK)
            ((ViewHolder)holder).bind(mTasks.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.delete_button)
        ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Task task, final HomeFragment.HomeItemListener listener) {

            mTitle.setText(task.getTitle());

            if(listener != null) {
                mDeleteButton.setOnClickListener((View v) -> {
                    listener.deleteTask(task);
                });
            }
        }
    }
}