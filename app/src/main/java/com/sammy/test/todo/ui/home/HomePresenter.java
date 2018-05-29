package com.sammy.test.todo.ui.home;

import android.text.TextUtils;

import com.sammy.test.todo.R;
import com.sammy.test.todo.data.AppDataManager;
import com.sammy.test.todo.model.Task;
import com.sammy.test.todo.ui.home.HomeContract.HomeView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sng
 */

public class HomePresenter implements HomeContract.HomePresenter {
    HomeView mHomeView;

    public HomePresenter(@NotNull HomeView homeView) {
        this.mHomeView = homeView;
        mHomeView.setPresenter(this);
    }

    @Override
    public void start() {
        List<Task> tasks = AppDataManager.getInstance().getTaskList();
        if(tasks.size() > 0) {
            mHomeView.showTaskList(tasks);
        } else {
            mHomeView.showEmptyList();
        }
    }

    @Override
    public void deleteTask(@NotNull Task task) {
        List<Task> tasks = AppDataManager.getInstance().deleteTask(task);
        mHomeView.deleteTask(task);
        if(tasks.size() == 0) {
            mHomeView.showEmptyMessage(true);
        }
    }

    @Override
    public void addItem(@NotNull String title) {
        if(TextUtils.isEmpty(title)) {
            mHomeView.displayMessage(R.string.no_empty);
            return;
        }

        Task task = new Task(title);
        AppDataManager.getInstance().addTask(task);
        mHomeView.addTask(task);
        mHomeView.showEmptyMessage(false);
    }
}
