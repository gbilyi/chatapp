package com.gordbilyi.chatapp.chatdetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gordbilyi.chatapp.EventObserver
import com.gordbilyi.chatapp.R
import com.gordbilyi.chatapp.databinding.ChatdetailFragBinding
import com.gordbilyi.chatapp.util.getViewModelFactory
import timber.log.Timber

/**
 * Main UI for the chat detail screen.
 */
class ChatDetailFragment : Fragment() {
    private lateinit var viewDataBinding: ChatdetailFragBinding

    private val args: ChatDetailFragmentArgs by navArgs()

    private val viewModel by viewModels<ChatDetailViewModel> { getViewModelFactory() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chatdetail_frag, container, false)
        viewDataBinding = ChatdetailFragBinding.bind(view).apply {
            viewmodel = viewModel
        }
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewDataBinding.contactId = args.contactId

        viewModel.start(args.contactId)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupListAdapter()
        setupEditText()
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_receive_message -> {
                viewModel.sendMessage(args.contactId, viewDataBinding.newMessage!!, false)
                true
            }
            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chatdetail_fragment_menu, menu)
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {

            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.stackFromEnd = true
            viewDataBinding.bubblesList.layoutManager = linearLayoutManager
            viewDataBinding.bubblesList.adapter = ChatDetailAdapter(viewModel)
            viewDataBinding.bubblesList.smoothScrollToPosition(0)
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupEditText() {
        viewModel.clearSentMessageEvent.observe(viewLifecycleOwner, EventObserver {
            viewDataBinding.newMessage = ""
        })
    }

}
