package com.sammy.test.todo.ui.home

import android.support.annotation.StringRes
import com.sammy.test.todo.model.Task
import com.sammy.test.todo.ui.BasePresenter
import com.sammy.test.todo.ui.BaseView

/**
 * Created by sng
 * This is the MVP contract
 * This is a centralized location for contract for the Home HomeView, HomePresenter, and HomeInteractor
 */
interface HomeContract {
    //These are the actions that wants to be done on the view
    interface HomeView : BaseView<HomePresenter> {
        fun showTaskList(taskList: List<Task>)
        fun showEmptyList()
        fun deleteTask(task: Task)
        fun addTask(task: Task)
        fun displayMessage(@StringRes text: Int)
        fun showEmptyMessage(shouldShow: Boolean)
    }

    //This is the middle-man between the model and view.
    interface HomePresenter : BasePresenter {
        fun deleteTask(task: Task)
        fun addItem(title: String)
    }
}