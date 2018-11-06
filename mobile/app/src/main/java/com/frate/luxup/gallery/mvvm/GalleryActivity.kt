package com.frate.luxup.gallery.mvvm

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.widget.toast
import com.frate.luxup.R
import com.frate.luxup.ext.app
import com.frate.luxup.gallery.DaggerGalleryComponent
import com.frate.luxup.gallery.GalleryComponent
import com.frate.luxup.gallery.adapter.GalleryAdapter
import com.frate.luxup.gallery.adapter.GridSpacesItemDecoration
import com.frate.luxup.mvvm.BaseActivity
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.toolbar.*

private const val ARG_SCROLL_VIEW_POSITION = "scrollViewPositionArg"

const val CAMERA_REQUEST = 200
const val EXTRA_MEDIA = "GalleryActivity.extraMedia"

class GalleryActivity : BaseActivity<GalleryViewModel, GalleryViewModel.Factory, GalleryComponent>() {
    private val requestedPermission = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA)
    private val permissionRequests = 1

    override val layoutResId = R.layout.activity_gallery
    override val viewModelClass = GalleryViewModel::class.java

    override fun createComponent(): GalleryComponent = DaggerGalleryComponent.builder()
        .appComponent(app.component)
        .build()
        .also { it.inject(this) }

    override fun onViewLoad(savedInstanceState: Bundle?) {
        checkPermissions()
        setupToolbar()
        setupAdapter()
    }

    private fun checkPermissions() {
        if (allPermissionsGranted(requestedPermission)) {
            viewModel.onPermissionGranted()
        } else {
            getRuntimePermissions(requestedPermission, permissionRequests)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.gallery)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_gallery, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when {
            item.itemId == android.R.id.home -> {
                finish()
                true
            }
            item.itemId == R.id.action_done -> {
                if (viewModel.state.mediaFile != null) {
                    setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_MEDIA, viewModel.state.mediaFile))
                }
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setupAdapter() {
        val adapter = GalleryAdapter(viewModel.state.galleryItems, viewModel)
        adapter.registerObserver(this)

        val columnCount = 3
        photos.layoutManager = GridLayoutManager(this, columnCount)
        photos.adapter = adapter
        val spacingXSmall = resources.getDimension(R.dimen.spacing_xsmall).toInt()
        photos.addItemDecoration(GridSpacesItemDecoration(spacingXSmall, columnCount, spacingXSmall))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == permissionRequests && allPermissionsGranted(requestedPermission)) {
            viewModel.onPermissionGranted()
        } else {
            toast(R.string.gallery_permission, Toast.LENGTH_LONG)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(
            ARG_SCROLL_VIEW_POSITION,
            (photos.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_SCROLL_VIEW_POSITION)) {
            photos.scrollToPosition(savedInstanceState.getInt(ARG_SCROLL_VIEW_POSITION))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST) {
            viewModel.onCameraResult(resultCode == Activity.RESULT_OK)
        }
    }
}
