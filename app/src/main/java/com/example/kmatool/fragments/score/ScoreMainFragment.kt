package com.example.kmatool.fragments.score

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.kmatool.databinding.FragmentScoreMainBinding
import com.example.kmatool.view_model.score.ScoreMainViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kmatool.R
import com.example.kmatool.utils.KEY_PASS_MINISTUDENT_ID
import com.example.kmatool.utils.KIT_URL
import com.example.kmatool.utils.TAG_SHOW_DIALOG_FRAGMENT

class ScoreMainFragment : Fragment() {
    private val TAG = ScoreMainFragment::class.java.simpleName
    private lateinit var binding: FragmentScoreMainBinding
    private val navController: NavController by lazy { findNavController() }
    private val scoreMainViewModel: ScoreMainViewModel by lazy {
        ViewModelProvider(requireActivity())[ScoreMainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "on create view $TAG")
        binding = FragmentScoreMainBinding.inflate(inflater, container, false)
        // mapping to UI
        binding.btnSearchFeature.setOnClickListener() { onClickShowSearchDialog() }
        binding.tvKit.setOnClickListener() { onClickTagFooter() }
        binding.scoreMainViewModel = scoreMainViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "show $TAG")

        // show statistical data
        scoreMainViewModel.getStatisticData()
    }

    private fun onClickShowSearchDialog() {
        Log.d(TAG, "on click show search dialog")
        // action (open dialog -> search -> callback to main fragment -> receive data -> nav detail fragment)
        val searchDataDialogFragment = SearchDataDialogFragment() { id ->
            navigateStudentDetailFragment(id)
        }
        activity?.supportFragmentManager?.let {
            searchDataDialogFragment.show(it, TAG_SHOW_DIALOG_FRAGMENT)
        }
    }

    private fun navigateStudentDetailFragment(id: String) {
        Log.d(TAG, "navigate detail fragment with student = $id")
        // action
        val bundle = bundleOf(
            KEY_PASS_MINISTUDENT_ID to id
        )
        navController.navigate(R.id.studentDetailFragment, bundle)
    }

    private fun onClickTagFooter() {
        Log.d(TAG, "on click tag footer")
        // action
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(KIT_URL)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "destroy $TAG")
    }
}