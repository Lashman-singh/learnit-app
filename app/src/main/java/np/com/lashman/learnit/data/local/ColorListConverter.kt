package np.com.lashman.learnit.data.local

import androidx.room.TypeConverter

// Converts between List<Int> and String for database storage.
class ColorListConverter {

    @TypeConverter
    // Converts a list of integers to a comma-separated string.
    fun fromColorList(colorList: List<Int>): String {
        return colorList.joinToString(",") { it.toString() }
    }

    @TypeConverter
    // Converts a comma-separated string back to a list of integers.
    fun toColorList(colorListString: String): List<Int> {
        return colorListString.split(",").map { it.toInt() }
    }
}
