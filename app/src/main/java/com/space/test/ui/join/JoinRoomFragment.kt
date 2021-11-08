package com.space.test.ui.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.space.sdk.callbacks.JoinRoomCallback
import com.space.sdk.extensions.showToast
import com.space.sdk.models.Roles
import com.space.sdk.shared.SpaceSDKManager
import com.space.test.databinding.FragmentJoinRoomBinding
import com.space.test.ui.start.RoomListViewModel

class JoinRoomFragment : Fragment(), JoinRoomCallback {
    private val binding by lazy {
        FragmentJoinRoomBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
        }
    }
    private val roomsViewModel by activityViewModels<RoomListViewModel>()
    private val args by navArgs<JoinRoomFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etRoomId.setText(args.roomID)
        binding.btJoin.setOnClickListener {
            joinRoom()
        }
    }

    private fun joinRoom() {
        context?.let {
            val roomID = binding.etRoomId.text.toString()
            val email = binding.etEmail.text.toString()
            val name = binding.etUsername.text.toString()
            val role = when (binding.spRole.selectedItemPosition) {
                0 -> {
                    Roles.COHOST
                }
                1 -> {
                    Roles.SPEAKER
                }
                else -> {
                    Roles.AUDIENCE
                }
            }
            SpaceSDKManager.joinLiveRoom(
                context = requireContext(),
                roomID = roomID,
                email = email,
                name = name,
                roles = role,
                callback = this
            )
        }
    }

    override fun onJoinRoomError(error: Throwable) {
        activity?.showToast(error.localizedMessage)
    }

    override fun onRoomStarted() {
        findNavController().popBackStack()
    }
}