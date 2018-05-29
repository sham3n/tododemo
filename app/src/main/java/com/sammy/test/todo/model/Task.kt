package com.sammy.test.todo.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by sng
 */
data class Task(@Expose
                @SerializedName("title")
                var title: String) {

    fun compareTo(task: Task): Int {
        return task.title.compareTo(title)
    }
}