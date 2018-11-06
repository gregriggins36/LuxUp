package com.frate.luxup.manager

import android.net.Uri
import com.frate.luxup.model.Article
import com.frate.luxup.net.ArticleRepository
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import io.reactivex.Single
import javax.inject.Inject

class InventoryManager @Inject constructor(
    private val articleRepository: ArticleRepository,
    private val firebaseStorage: FirebaseStorage) {
    fun articles(): Single<List<Article>> = articleRepository.articles()

    fun addArticle(name: String, price: Float, image: Uri): Single<Article> {
        return Single.create { emitter ->
            run {
                val ref = firebaseStorage.reference
                    .child("images/${image.lastPathSegment}")
                ref.putFile(image)
                    .continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                emitter.onError(task.exception!!.cause!!)
                            }
                        }
                        return@Continuation ref.downloadUrl
                    }).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri: Uri? = task.result
                            articleRepository.addArticle(Article(name = name, image = downloadUri.toString(), price = price, id = null))
                                .subscribe(
                                    { emitter.onSuccess(it) },
                                    { emitter.onError(it) }
                                )
                        } else {
                            emitter.onError(task.exception!!.cause!!)
                        }
                    }
                    .addOnFailureListener { emitter.onError(it) }
            }
        }
    }
}
