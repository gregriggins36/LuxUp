package com.frate.luxup.profile.mvvm

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.frate.luxup.R
import com.frate.luxup.app.App.Companion.app
import com.frate.luxup.mvvm.BaseFragment
import com.frate.luxup.profile.DaggerProfileComponent
import com.frate.luxup.profile.ProfileComponent

class ProfileFragment : BaseFragment<ProfileViewModel, ProfileViewModel.Factory, ProfileComponent>() {
    override val viewModelClass = ProfileViewModel::class.java
    override val layoutResId = R.layout.fragment_profile

    override fun createComponent(): ProfileComponent = DaggerProfileComponent
        .builder()
        .appComponent(app.component)
        .build()
        .also { it.inject(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.finish.observe(this, Observer { event ->
            event?.getContentIfNotHandled {
                findNavController().popBackStack()
            }
        })
    }
}
