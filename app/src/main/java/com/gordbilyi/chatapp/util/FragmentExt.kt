package com.gordbilyi.chatapp.util

/**
 * Extension functions for Fragment.
 */

import androidx.fragment.app.Fragment
import com.gordbilyi.chatapp.ChatsApplication
import com.gordbilyi.chatapp.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as ChatsApplication).messagesRepository
    return ViewModelFactory(repository, this)
}
