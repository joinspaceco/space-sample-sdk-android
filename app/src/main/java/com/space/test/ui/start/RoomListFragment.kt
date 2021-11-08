package com.space.test.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.space.test.databinding.FragmentRoomListBinding
import com.space.test.databinding.FragmentRoomListBindingImpl

class RoomListFragment : Fragment() {
    private val binding by lazy {
        FragmentRoomListBinding.inflate(layoutInflater)
            .apply { lifecycleOwner = viewLifecycleOwner }
    }
    private val adapter = RoomListAdapter(::onRoomClicked)
    private val viewModel by activityViewModels<RoomListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRooms.adapter = adapter
        binding.btJoin.setOnClickListener {
            findNavController().navigate(RoomListFragmentDirections.actionRoomListToJoinRoom())
        }
        binding.btCreate.setOnClickListener {
            findNavController().navigate(RoomListFragmentDirections.actionRoomListToCreateRoom())
        }
        viewModel.roomItems.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })
    }

    private fun onRoomClicked(roomItem: RoomItem) {
        findNavController().navigate(RoomListFragmentDirections.actionRoomListToJoinRoom(roomItem.id))
    }
}