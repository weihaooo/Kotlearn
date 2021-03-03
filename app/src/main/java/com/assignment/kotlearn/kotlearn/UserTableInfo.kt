package com.assignment.kotlearn.kotlearn

import android.provider.BaseColumns

class UserTableInfo : BaseColumns {

    companion object {
        val TABLE_NAME = "user"
        val COLUMN_USERNAME = "username"
        val COLUMN_PASSWORD = "password"
        val COLUMN_EMAIL = "email"
    }

}