package com.space.test.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.space.sdk.callbacks.FetchRoomsCallback
import com.space.sdk.models.network.RoomModel
import com.space.sdk.shared.SpaceSDKManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RoomListViewModel : ViewModel(), FetchRoomsCallback {
    private val dateFormatter: DateFormat = SimpleDateFormat("h:mm aa dd, MMM")
    private val _roomsLiveData = MutableLiveData<List<RoomModel>>(emptyList())
    val roomItems = Transformations.map(_roomsLiveData) {
        it.map {
            RoomItem(
                it.id,
                it.name,
                it.description,
                dateFormatter.format(Date(it.createdAt * 1000))
            )
        }
    }

    fun fetchRooms() {
        SpaceSDKManager.fetchRooms(this)
    }

    override fun onFetchRoomsError(error: Throwable) {
        _roomsLiveData.value = emptyList()
    }

    override fun onRoomsFetched(rooms: List<RoomModel>) {
        _roomsLiveData.value = rooms
    }
}