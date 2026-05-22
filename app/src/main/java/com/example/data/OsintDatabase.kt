package com.example.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "scan_items")
data class OsintScanItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, // "IP", "Domain", "Username"
    val target: String,
    val resultJson: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_dorks")
data class SavedDork(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val query: String,
    val description: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface OsintDao {
    @Query("SELECT * FROM scan_items ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<OsintScanItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScan(item: OsintScanItem)

    @Query("DELETE FROM scan_items WHERE id = :id")
    suspend fun deleteScanById(id: Int)

    @Query("DELETE FROM scan_items")
    suspend fun clearAllScans()

    @Query("SELECT * FROM saved_dorks ORDER BY timestamp DESC")
    fun getAllSavedDorks(): Flow<List<SavedDork>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDork(item: SavedDork)

    @Query("DELETE FROM saved_dorks WHERE id = :id")
    suspend fun deleteDorkById(id: Int)
}

@Database(entities = [OsintScanItem::class, SavedDork::class], version = 1, exportSchema = false)
abstract class OsintDatabase : RoomDatabase() {
    abstract fun osintDao(): OsintDao

    companion object {
        @Volatile
        private var INSTANCE: OsintDatabase? = null

        fun getDatabase(context: Context): OsintDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OsintDatabase::class.java,
                    "osint_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class OsintRepository(private val dao: OsintDao) {
    val allScans: Flow<List<OsintScanItem>> = dao.getAllScans()
    val allDorks: Flow<List<SavedDork>> = dao.getAllSavedDorks()

    suspend fun insertScan(item: OsintScanItem) = dao.insertScan(item)
    suspend fun deleteScan(id: Int) = dao.deleteScanById(id)
    suspend fun clearScans() = dao.clearAllScans()

    suspend fun insertDork(item: SavedDork) = dao.insertDork(item)
    suspend fun deleteDork(id: Int) = dao.deleteDorkById(id)
}
