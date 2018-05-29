package com.sammy.test.todo.utils

import android.support.v7.util.DiffUtil
import com.sammy.test.todo.model.Task

/**
 * Created by sng on 4/28/18.
 */
class TaskDiffUtilCallback : DiffUtil.Callback {
    var mNewList: List<Task>
    var mOldList: List<Task>

    constructor(newList: List<Task>, oldList: List<Task>) : super() {
        mNewList = newList
        mOldList = oldList
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }

    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList.get(oldItemPosition).equals(mNewList.get(newItemPosition))
    }
}