//package com.example.safetech.data.database
//
//import androidx.room.TypeConverter
//import com.example.safetech.data.network.models.Employee
//import com.example.safetech.data.network.models.ViolationsOnObject
//import com.example.safetech.view.theme.models.UiControlItem
//import kotlinx.serialization.encodeToString
//import kotlinx.serialization.json.Json
//
//class Converters {
//    @TypeConverter
//    fun fromList(value: List<UiControlItem>?): String = Json.encodeToString(value ?: emptyList())
//
//    @TypeConverter
//    fun toList(value: String): List<UiControlItem> = Json.decodeFromString(value)
//
//    @TypeConverter
//    fun fromEmployeesList(value: List<Employee>?): String = Json.encodeToString(value ?: emptyList())
//
//    @TypeConverter
//    fun toEmployeesList(value: String): List<Employee> = Json.decodeFromString(value)
//
//    @TypeConverter
//    fun fromViolationsList(value: MutableList<ViolationsOnObject>?): String = Json.encodeToString(value ?: mutableListOf())
//
//    @TypeConverter
//    fun toViolationsList(value: String): MutableList<ViolationsOnObject> = Json.decodeFromString(value)
//}
