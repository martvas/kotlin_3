package ru.martin.kotlin.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note(
    val id: String = "",
    val title: String = "",
    val text: String = "",
    val color: Color = Color.WHITE,
    val lastChanged: Date = Date()
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(javaClass != other?.javaClass) return false

        other as Note

        if(id != other.id) return false
        return true
    }

    override fun hashCode() = id.hashCode()

    enum class Color (val id: Int) {
        WHITE (0),
        YELLOW(1),
        GREEN(2),
        BLUE(3),
        RED(4),
        VIOLET(5),
        PINK(6)
    }
}