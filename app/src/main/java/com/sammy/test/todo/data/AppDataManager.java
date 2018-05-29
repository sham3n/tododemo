package com.sammy.test.todo.data;

import android.support.annotation.NonNull;

import com.sammy.test.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sng
 * THis is the datamanager that handles how to retrieve the data
 * Cache, api, db, etc
 */

public class AppDataManager {
    private static AppDataManager mInstance;
    private List<Task> mTasks;

    private AppDataManager() {
        mTasks = new ArrayList<>();
        //For testing add 3 todo items
        mTasks.add(new Task("Task 1"));
        mTasks.add(new Task("Task 2"));
        mTasks.add(new Task("Task 2a"));
        mTasks.add(new Task("Task 3a"));
        mTasks.add(new Task("Task 3b"));
        mTasks.add(new Task("Task 3c"));
    }

    //TODO Use Dependecy Injection instead
    public static AppDataManager getInstance() {
        if(mInstance == null) {
            mInstance = new AppDataManager();
        }

        return mInstance;
    }

    @NonNull
    public List<Task> getTaskList() {
        return mTasks;
    }

    @NonNull
    public List<Task> addTask(Task task) {
        mTasks.add(task);
        return mTasks;
    }

    @NonNull
    public List<Task> deleteTask(Task task) {
        mTasks.remove(task);
        return mTasks;
    }
}
