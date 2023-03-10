package com.example.kmatool.fragments.score

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kmatool.adapter.score.StudentDetailAdapter
import com.example.kmatool.databinding.FragmentScoreStudentDetailBinding
import com.example.kmatool.models.score.Student
import com.example.kmatool.utils.KEY_PASS_MINISTUDENT_ID
import com.example.kmatool.utils.KIT_URL
import com.example.kmatool.view_model.score.StudentDetailViewModel

class StudentDetailFragment : Fragment() {
    private val TAG = StudentDetailFragment::class.java.simpleName
    private lateinit var binding: FragmentScoreStudentDetailBinding
    private val studentDetailViewModel: StudentDetailViewModel by lazy {
        ViewModelProvider(requireActivity())[StudentDetailViewModel::class.java]
    }
    private var studentId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "on create view $TAG")
        binding = FragmentScoreStudentDetailBinding.inflate(inflater, container, false)
        binding.tvKit.setOnClickListener() { onClickTagFooter() }
        // set view model
        binding.studentDetailVM = studentDetailViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "show $TAG")

        // receive data from main fragment
        receiveDataFromScoreMainFragment()
        // show detail student
        studentDetailViewModel.getDetailStudent(studentId) { student ->
            showDetailStudent(student)
        }
    }

    private fun receiveDataFromScoreMainFragment() {
        val bundle = arguments
        bundle?.let {
            studentId = it.getString(KEY_PASS_MINISTUDENT_ID).toString()
        }
        Log.i(TAG, "receive student id = $studentId")
    }

    private fun showDetailStudent(student: Student) {
        Log.d(TAG, "show to UI detail student id = ${student.id}")
        binding.student = student
        // set adapter data for rcv
        val studentDetailAdapter = StudentDetailAdapter()
        studentDetailAdapter.setScores(student.scores)

        // update list to UI
        binding.rvScores.layoutManager = LinearLayoutManager(context?.applicationContext)
        binding.rvScores.isFocusable = false
        binding.rvScores.isNestedScrollingEnabled = false
        binding.rvScores.adapter = studentDetailAdapter
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