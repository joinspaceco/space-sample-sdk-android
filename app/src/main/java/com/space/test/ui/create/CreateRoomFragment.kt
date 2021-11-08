package com.space.test.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.space.sdk.callbacks.CreateRoomCallback
import com.space.sdk.extensions.showToast
import com.space.sdk.models.network.RoomModel
import com.space.sdk.shared.SpaceSDKManager
import com.space.test.databinding.FragmentCreateRoomBinding
import com.space.test.ui.start.RoomListViewModel

class CreateRoomFragment : Fragment(), CreateRoomCallback {
    private val binding by lazy {
        FragmentCreateRoomBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }
    private val roomsViewModel by activityViewModels<RoomListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btCreate.setOnClickListener {
            createRoom()
        }
    }

    private fun createRoom() {
        val email = binding.etEmail.text.toString()
        val userName = binding.etUsername.text.toString()
        val title = binding.etTitle.text.toString()
        val desc = binding.etDesc.text.toString()
        val allowRequests = binding.swRequests.isChecked
        val enableChat = binding.swChat.isChecked
        val enableReactions = binding.swReactions.isChecked
        val joinAsSpeakers = binding.swSpeaker.isChecked
        SpaceSDKManager.createRoom(
            roomName = title,
            roomDescription = desc,
            email = email,
            userName = userName,
            allowRequestsToSpeak = allowRequests,
            enableChat = enableChat,
            enableReactions = enableReactions,
            usersJoinAsSpeakers = joinAsSpeakers,
            callback = this
        )
    }

    override fun onFailedToCreateRoom(error: Throwable) {
        activity?.showToast(error.localizedMessage)
    }

    override fun onRoomsCreated(room: RoomModel) {
        activity?.showToast("Room Created")
        roomsViewModel.fetchRooms()
        findNavController().popBackStack()
    }
}