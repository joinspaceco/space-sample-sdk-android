package com.space.test.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.space.sdk.callbacks.FetchRoomsCallback
import com.space.sdk.callbacks.SpaceSDKInitListener
import com.space.sdk.extensions.showToast
import com.space.sdk.models.network.RoomModel
import com.space.sdk.shared.SpaceSDKManager
import com.space.test.R
import com.space.test.databinding.ActivityMainBinding
import com.space.test.ui.start.RoomListViewModel

class MainActivity : AppCompatActivity(), SpaceSDKInitListener {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val roomsViewModel by lazy { ViewModelProvider(this).get(RoomListViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        SpaceSDKManager.initialize(
            clientKey = getString(R.string.space_client_key),
            secretKey = getString(R.string.space_secret_key),
            sdkInitListener = this
        )
    }

    override fun onSpaceSDKFailed(error: Throwable) {
        showToast(error.localizedMessage)
    }

    override fun onSpaceSDKInitSuccess() {
        roomsViewModel.fetchRooms()
    }

}