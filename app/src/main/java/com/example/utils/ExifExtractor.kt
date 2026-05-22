package com.example.utils

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import android.util.Log

data class ImageExifMetadata(
    val hasExif: Boolean,
    val deviceMake: String?,
    val deviceModel: String?,
    val dateTimeTaken: String?,
    val latitude: Double?,
    val longitude: Double?,
    val width: String?,
    val height: String?,
    val iso: String?,
    val aperture: String?,
    val exposureTime: String?,
    val focalLength: String?,
    val flashStatus: String?,
    val software: String?
)

object ExifExtractor {

    fun extractMetadata(context: Context, uri: Uri): ImageExifMetadata {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val exifInterface = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    ExifInterface(inputStream)
                } else {
                    return ImageExifMetadata(
                        hasExif = false,
                        deviceMake = null, deviceModel = null, dateTimeTaken = "API 24+ required",
                        latitude = null, longitude = null, width = null, height = null,
                        iso = null, aperture = null, exposureTime = null, focalLength = null,
                        flashStatus = null, software = null
                    )
                }

                val make = exifInterface.getAttribute(ExifInterface.TAG_MAKE)
                val model = exifInterface.getAttribute(ExifInterface.TAG_MODEL)
                val date = exifInterface.getAttribute(ExifInterface.TAG_DATETIME)
                val software = exifInterface.getAttribute(ExifInterface.TAG_SOFTWARE)

                val width = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH)
                val height = exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH)

                val iso = exifInterface.getAttribute(ExifInterface.TAG_ISO_SPEED_RATINGS)
                val aperture = exifInterface.getAttribute(ExifInterface.TAG_F_NUMBER)
                val exposure = exifInterface.getAttribute(ExifInterface.TAG_EXPOSURE_TIME)
                val focal = exifInterface.getAttribute(ExifInterface.TAG_FOCAL_LENGTH)
                val flash = exifInterface.getAttribute(ExifInterface.TAG_FLASH)

                // GPS calculation
                val latLongArray = FloatArray(2)
                val hasGps = exifInterface.getLatLong(latLongArray)
                val latitude = if (hasGps) latLongArray[0].toDouble() else null
                val longitude = if (hasGps) latLongArray[1].toDouble() else null

                val hasAnyDetails = make != null || model != null || date != null || hasGps || iso != null

                return ImageExifMetadata(
                    hasExif = hasAnyDetails,
                    deviceMake = make,
                    deviceModel = model,
                    dateTimeTaken = date,
                    latitude = latitude,
                    longitude = longitude,
                    width = width,
                    height = height,
                    iso = iso,
                    aperture = aperture,
                    exposureTime = exposure,
                    focalLength = focal,
                    flashStatus = flash,
                    software = software
                )
            }
        } catch (e: Exception) {
            Log.e("ExifExtractor", "Error reading image EXIF: ${e.message}", e)
        }

        return ImageExifMetadata(
            hasExif = false,
            deviceMake = null, deviceModel = null, dateTimeTaken = null,
            latitude = null, longitude = null, width = null, height = null,
            iso = null, aperture = null, exposureTime = null, focalLength = null,
            flashStatus = null, software = null
        )
    }
}
