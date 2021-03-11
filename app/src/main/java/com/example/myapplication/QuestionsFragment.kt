package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

private const val CORRECT_ANSWER = "param1"
private const val TEXT = "param2"

class QuestionsFragment() : Fragment() {

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
    ): View? {
        val root = inflater.inflate(
            R.layout.fragment_questions,
            container,
            false
        )
        root.findViewById<TextView>(R.id.question1).text = text
        return root
    }

    override fun onStart() {
        super.onStart()
        val checkTrue = (requireActivity() as MyInterface).checkAnswer(true)
        val checkFalse = (requireActivity() as MyInterface).checkAnswer(false)
        val qfragment = view?.findViewById<View>(R.id.question_fragment)
        view?.findViewById<View>(R.id.button_yes)?.setOnClickListener {
            setBackground(checkTrue)
            qfragment?.setOnClickListener {
                (requireActivity() as MyInterface).answerQuestion(true)
            }
        }
        view?.findViewById<View>(R.id.button_no)?.setOnClickListener {
            setBackground(checkFalse)
            qfragment?.setOnClickListener {
                (requireActivity() as MyInterface).answerQuestion(false)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun setBackground(check: Boolean) {
        val resultView = view?.findViewById<TextView>(R.id.result)
        val qfragment = view?.findViewById<View>(R.id.question_fragment)
        view?.findViewById<View>(R.id.button_yes)?.visibility=View.GONE
        view?.findViewById<View>(R.id.button_no)?.visibility=View.GONE
        if (check) {
            resultView?.text = "Верно!"
            qfragment?.setBackgroundColor(resources.getColor(R.color.light_green))
        } else if (!check) {
            resultView?.text = "Неверно!"
            qfragment?.setBackgroundColor(resources.getColor(R.color.light_red))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(res: Boolean, text: String) =
            QuestionsFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(CORRECT_ANSWER, res)
                    putString(TEXT, text)
                }
            }
    }
}