package com.example.shopease.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shopease.data.model.ProductCartEntity
import com.example.shopease.data.model.ProductFavoriteEntity


@Database(entities = [ProductCartEntity::class, ProductFavoriteEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun favoriteDao(): FavoriteDao

    companion object{
        @Volatile
        private var INSTANCE :ProductDatabase? = null

        fun getInstance(context: Context):ProductDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}