package com.sandeep.mvvmnewsapp.data.db

import androidx.room.TypeConverter
import com.sandeep.mvvmnewsapp.models.Source

/**
 * Created by SandeepY on 27052021
 **/


class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}