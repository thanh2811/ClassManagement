package com.example.dclassmanagement.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import kotlinx.coroutines.Deferred


//@Module
//@InstallIn(Application::class)
//object AppModule {
//
//    @Provides
//    fun provideFirebaseUser(): FirebaseUser? {
//        return FirebaseAuth.getInstance().currentUser
//    }
//}
//
//interface FirebaseRepository {
////    suspend fun getUserInfoAsync(token: String): Deferred<UserInfoResponse?>
//}