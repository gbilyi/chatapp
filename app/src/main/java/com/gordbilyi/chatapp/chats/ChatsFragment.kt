package com.gordbilyi.chatapp.chats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.gordbilyi.chatapp.EventObserver
import com.gordbilyi.chatapp.data.Stubs.Companion.CONTACTS
import com.gordbilyi.chatapp.databinding.ChatsFragBinding
import com.gordbilyi.chatapp.util.getViewModelFactory
import timber.log.Timber


/**
 * Display a list of chats
 */
class ChatsFragment : Fragment() {

    private val viewModel by viewModels<ChatsViewModel> { getViewModelFactory() }

    private lateinit var viewDataBinding: ChatsFragBinding

    private lateinit var listAdapter: ChatsAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = ChatsFragBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        setupListAdapter()
        setupNavigation()
        hideSoftKeyboard()
    }

    private fun hideSoftKeyboard() {
        val view = activity?.currentFocus
        view?.let { v ->
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    private fun setupNavigation() {
        viewModel.openChatEvent.observe(viewLifecycleOwner, EventObserver {
            openChatDetails(it)
        })
    }

    private fun openChatDetails(contactId: Int) {
        (activity as AppCompatActivity).supportActionBar?.title = CONTACTS[contactId - 1]
        val action = ChatsFragmentDirections.actionChatsFragmentToChatDetailFragment(contactId)
        findNavController().navigate(action)
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {

            listAdapter = ChatsAdapter(viewModel)
            viewDataBinding.chatsList.adapter = listAdapter
            viewDataBinding.chatsList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }
}
