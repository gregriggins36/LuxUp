package com.frate.luxup.util

import android.content.Context
import android.os.Environment
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val IMAGE_EXTENSION = ".jpg"
private const val VIDEO_FILE_EXTENSION = ".mp4"

/**
 * Create an image file in a file provider folder
 * @param context [Context] - the context
 * @return a File pointing to the image or null if the File couldn't be created
 */
fun createImageFile(context: Context): File? {
    // Create an image file name
    val imageFileName = createUniqueMediaFileName()
    val image: File
    try {
        image = File.createTempFile(
                imageFileName, /* prefix */
                IMAGE_EXTENSION, /* suffix */
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) /* directory */
        )
    } catch (e: IOException) {
        Timber.e(e, e.message)
        return null
    }

    return image
}

private fun createUniqueMediaFileName(): String {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    return "ww_guests_$timeStamp"
}

/**
 * Create a video file in a file provider folder
 * @param context [Context] - the context
 * @return a File pointing to the video or null if the File couldn't be created
 */
fun createVideoFile(context: Context): File? {
    // Create a video file name
    val videoFileName = createUniqueMediaFileName()
    val video: File
    try {
        video = File.createTempFile(
                videoFileName, /* prefix */
                VIDEO_FILE_EXTENSION, /* suffix */
                context.getExternalFilesDir(Environment.DIRECTORY_MOVIES) /* directory */
        )
    } catch (e: IOException) {
        Timber.e(e, e.message)
        return null
    }

    return video
}
