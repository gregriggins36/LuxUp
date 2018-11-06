package com.frate.luxup.additem.mvvm

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.frate.luxup.R
import com.frate.luxup.additem.AddItemComponent
import com.frate.luxup.additem.AddItemModule
import com.frate.luxup.additem.DaggerAddItemComponent
import com.frate.luxup.app.App.Companion.app
import com.frate.luxup.gallery.mvvm.GalleryActivity
import com.frate.luxup.main.MainViewModel
import com.frate.luxup.mvvm.BaseFragment
import com.frate.luxup.util.Event
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.android.synthetic.main.toolbar.*

private const val REQUEST_GALLERY = 100

class AddItemFragment : BaseFragment<AddItemViewModel, AddItemViewModel.Factory, AddItemComponent>() {
    override val viewModelClass = AddItemViewModel::class.java
    override val layoutResId = R.layout.fragment_add_item

    override fun createComponent(): AddItemComponent = DaggerAddItemComponent
        .builder()
        .appComponent(app.component)
        .addItemModule(AddItemModule())
        .build()
        .also { it.inject(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(toolbar) {
            title = "Upload your listing"
            setTitleTextColor(Color.WHITE)
        }

        add_photo.setOnClickListener { startActivityForResult(
            Intent(context, GalleryActivity::class.java),
            REQUEST_GALLERY) }

        viewModel.state.uploadSuccess.observe(this, Observer { event ->
            event?.getContentIfNotHandled {
                parentViewModel(MainViewModel::class.java).onUploadSuccess.postValue(Event(true))
                findNavController().popBackStack()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) viewModel.onPhotoResult(data)
    }
}