package com.example.newsmvvmapp.db

import androidx.room.TypeConverter
import com.example.newsmvvmapp.models.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source) : String{
        return source.name
    }

    @TypeConverter
    fun toSource(name : String) : Source{
        return Source(name,name)
    }


}