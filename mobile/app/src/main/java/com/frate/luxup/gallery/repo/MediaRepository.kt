package com.frate.luxup.gallery.repo

import android.content.Context
import android.os.Environment
import java.io.File
import java.util.regex.Pattern
import javax.inject.Inject

class MediaRepository @Inject constructor() {
    private val directorySeparator = Pattern.compile("/")
    private val compareFileByLastModified: Comparator<File> = Comparator { o1, o2 ->
        val file1LastModified = o1.lastModified()
        val file2LastModified = o2.lastModified()

        when {
            file1LastModified < file2LastModified -> 1
            file1LastModified == file2LastModified -> 0
            else -> -1
        }
    }

    /**
     * @return a [List] of file representing all the user personal photos
     */
    fun getAllUserPhotos(context: Context): List<File> {
        val listPhotos = populateListPhotos(getStorageDirectories(false, context))
        // Sort the list by last added/modified
        return listPhotos.sortedWith(compareFileByLastModified)
    }

    /**
     * @return a [List] of file representing all the user personal photos
     */
    fun getAllUserPhotosAndVideos(context: Context): List<File> {
        val listPhotos = populateListPhotosAndVideos(getStorageDirectories(true, context))
        // Sort the list by last added/modified
        return listPhotos.sortedWith(compareFileByLastModified)
    }

    private fun getStorageDirectories(includeVideos: Boolean, context: Context): Array<String> {
        // Final set of paths
        val rv = HashSet<String>()
        // Primary physical SD-CARD (not emulated)
        val rawExternalStorage = System.getenv("EXTERNAL_STORAGE")
        // All Secondary SD-CARDs (all exclude primary) separated by ":"
        val rawSecondaryStorageStr = System.getenv("SECONDARY_STORAGE")
        // Primary emulated SD-CARD
        val rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET")
        if (rawEmulatedStorageTarget.isNullOrBlank()) {
            // Device has physical external storage; use plain paths.
            if (rawExternalStorage.isNullOrBlank()) {
                // EXTERNAL_STORAGE undefined; falling back to default.
                rv.add("/storage/sdcard0")
            } else {
                rv.add(rawExternalStorage + "/" + Environment.DIRECTORY_DCIM)
                rv.add(rawExternalStorage + "/" + Environment.DIRECTORY_DOWNLOADS)
                rv.add(rawExternalStorage + "/" + Environment.DIRECTORY_PICTURES)
                rv.add(rawExternalStorage + "/" + Environment.DIRECTORY_DOCUMENTS)
                if (includeVideos) rv.add(rawExternalStorage + "/" + Environment.DIRECTORY_MOVIES)
            }
        } else {
            // Device has emulated storage; external storage paths should have
            // userId burned into them.
            val rawUserId: String
            val path = Environment.getExternalStorageDirectory().absolutePath
            val folders = directorySeparator.split(path)
            val lastFolder = folders[folders.size - 1]
            var isDigit = false
            try {
                Integer.valueOf(lastFolder)
                isDigit = true
            } catch (ignored: NumberFormatException) {
                // Intentionally ignore
            }

            rawUserId = if (isDigit) lastFolder else ""

            // /storage/emulated/0[1,2,...]
            if (rawUserId.isBlank()) {
                rv.add(rawEmulatedStorageTarget)
            } else {
                rv.add(rawEmulatedStorageTarget + File.separator + rawUserId)
            }
        }
        // Add all secondary storages
        if (!rawSecondaryStorageStr.isNullOrEmpty()) {
            // All Secondary SD-CARDs split into array
            val rawSecondaryStorages = rawSecondaryStorageStr.split(File.pathSeparator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            rv.addAll(rawSecondaryStorages)
        }

        // Add private storage
        val picturesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (picturesDirectory != null) {
            // Only add external Pictures directory if it's available on the device
            rv.add(picturesDirectory.absolutePath)
        }
        val moviesDirectory = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        if (includeVideos && moviesDirectory != null) {
            // Only add external Movies directory if it's available on the device
            rv.add(moviesDirectory.absolutePath)
        }

        return rv.toTypedArray()
    }

    private fun populateListPhotos(files: Array<String>): List<File> {
        val listPhotos = ArrayList<File>()
        for (file in files) {
            val tempFile = File(file)
            if (tempFile.canRead()) {
                listPhotos.addAll(getListPhotos(tempFile))
            }
        }
        return listPhotos
    }

    private fun populateListPhotosAndVideos(files: Array<String>): List<File> {
        val listPhotosAndVideos = ArrayList<File>()
        for (file in files) {
            val tempFile = File(file)
            if (tempFile.canRead()) {
                listPhotosAndVideos.addAll(getListPhotosAndVideos(tempFile))
            }
        }
        return listPhotosAndVideos
    }

    private fun getListPhotos(file: File): List<File> {
        val photos = ArrayList<File>()
        val listFiles = file.listFiles() ?: return photos

        for (f in listFiles) {
            if (!f.isHidden) {
                if (f.isFile) {
                    if (isImage(f)) { // Here to prevent else from causing null pointer exception.
                        photos.add(f)
                    }
                } else {
                    photos.addAll(getListPhotos(f))
                }
            }
        }

        return photos
    }

    private fun getListPhotosAndVideos(file: File): List<File> {
        val photosAndVideos = ArrayList<File>()

        val listFiles = file.listFiles() ?: return photosAndVideos

        for (f in listFiles) {
            if (!f.isHidden) {
                if (f.isFile) {
                    if (isImage(f) || isVideo(f)) { // Here to prevent else from causing null pointer exception.
                        photosAndVideos.add(f)
                    }
                } else {
                    photosAndVideos.addAll(getListPhotosAndVideos(f))
                }
            }
        }

        return photosAndVideos
    }

    private fun isImage(file: File): Boolean {
        val path = file.path
        return path.endsWith(".jpeg") || path.endsWith(".jpg") || path.endsWith(".png")
    }

    fun isVideo(file: File?): Boolean {
        if (file == null) return false

        val path = file.path
        return path.endsWith(".mp4") || path.endsWith(".3gp") || path.endsWith(".mkv")
    }
}
