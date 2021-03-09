package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

private const val TEXT = "param1"

class ResultFragment : Fragment() {

    private var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text = it.getString(TEXT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val root = inflater.inflate(
        R.layout.fragment_result,
        container,
        false
    )
        root.findViewById<TextView>(R.id.text_res).text = text
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(result: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString(TEXT, result)
                }
            }
    }
}