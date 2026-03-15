package com.ultimatelifesimulator.core.di

import android.content.Context
import androidx.room.Room
import com.ultimatelifesimulator.core.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase {
        return Room.databaseBuilder(
            context,
            GameDatabase::class.java,
            "ultimate_life_simulator.db"
        ).build()
    }

    @Provides
    fun providePlayerDao(database: GameDatabase): PlayerDao = database.playerDao()

    @Provides
    fun provideSkillDao(database: GameDatabase): SkillDao = database.skillDao()

    @Provides
    fun provideTraitDao(database: GameDatabase): TraitDao = database.traitDao()

    @Provides
    fun provideRelationshipDao(database: GameDatabase): RelationshipDao = database.relationshipDao()

    @Provides
    fun provideInventoryDao(database: GameDatabase): InventoryDao = database.inventoryDao()

    @Provides
    fun provideLocationDao(database: GameDatabase): LocationDao = database.locationDao()

    @Provides
    fun provideFactionDao(database: GameDatabase): FactionDao = database.factionDao()

    @Provides
    fun provideGameFlagDao(database: GameDatabase): GameFlagDao = database.gameFlagDao()
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
}
