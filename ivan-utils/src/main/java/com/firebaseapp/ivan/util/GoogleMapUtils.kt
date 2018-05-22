package com.firebaseapp.ivan.util

import com.firebaseapp.ivan.ivan.model.Location
import java.util.*

/**
 * @author phompang on 9/2/2018 AD.
 */
fun Location.createStaticMapUrl(width: Int, height: Int, zoom: Int, scale: Int): String {
	return String.format(Locale.getDefault(), "http://maps.google.com/maps/api/staticmap?markers=%f,%f&zoom=%d&size=%dx%d&sensor=false&scale=%d", lat,
			lng, zoom, width, height, scale)
}