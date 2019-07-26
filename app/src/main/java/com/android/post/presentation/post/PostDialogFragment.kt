package com.android.post.presentation.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.post.R
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_post_dialog.view.*


class PostDialogFragment : DaggerDialogFragment() {

    private lateinit var root: View
    private var mPostId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.containsKey(KEY_POST_ID) == true) {
            mPostId = arguments?.getInt(KEY_POST_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_post_dialog, container, false)

        root.post_id_text_view?.text =
            StringBuilder().append(getString(R.string.post_id)).append(" $mPostId").toString()
        root.post_okay_text_view?.setOnClickListener {
            dialog.dismiss()
        }

        return root
    }

    companion object {
        val FRAGMENT_NAME = PostDialogFragment::class.java.name
        const val KEY_POST_ID = "KEY_POST_ID"

        fun newInstance(postId: Int) = PostDialogFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_POST_ID, postId)
            }
        }
    }

}