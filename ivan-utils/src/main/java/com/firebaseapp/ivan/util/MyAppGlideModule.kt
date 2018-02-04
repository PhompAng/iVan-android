package com.firebaseapp.ivan.util

import android.content.Context
import com.bumptech.glide.annotation.GlideModule
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream


/**
 * @author phompang on 18/1/2018 AD.
 */

@GlideModule
class MyAppGlideModule : AppGlideModule() {
	override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
		// Register FirebaseImageLoader to handle StorageReference
		registry.append(StorageReference::class.java, InputStream::class.java,
				FirebaseImageLoader.Factory())
	}
}