package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface MyInterface {
    fun startQuestions()
    fun finishQuestions()
    fun answerQuestion(result: Boolean)
    fun checkAnswer(result: Boolean): Boolean
}

class MainActivity : AppCompatActivity(), MyInterface {

    private val mQuestionsFragment = arrayOf(
        QuestionsFragment.newInstance(true, "Вопрос №1:\nОзеро Байкал - самое глубокое в мире"),
        QuestionsFragment.newInstance(true, "Вопрос №2:\nВоздух на 78% состоит из азота"),
        QuestionsFragment.newInstance(false, "Вопрос №3:\n50% планеты Земля занимает вода"),
        QuestionsFragment.newInstance(false, "Вопрос №4:\nВ радуге 8 цветов"),
        QuestionsFragment.newInstance(true, "Вопрос №5:\nЗвезды - не самые яркие объекты во вселенной"),
        QuestionsFragment.newInstance(true, "Вопрос №6:\nКанберра - столица Австралии"),
        QuestionsFragment.newInstance(false, "Вопрос №7:\nСамый глубокий океан на Земле - Атлантический"),
        QuestionsFragment.newInstance(false, "Вопрос №8:\nNissan Skyline GT-R оснащается турбированным двигателем"),
        QuestionsFragment.newInstance(true, "Вопрос №9:\nApple Maps является неудачным проектом компании Apple"),
        QuestionsFragment.newInstance(false, "Вопрос №10:\nНаселение Земли больше 8 миллиардов человек")
    )

    private var currentIndex = 0
    private var trueAnswers = 0

    private val KEY_INDEX1="index1"
    private val KEY_INDEX2="index2"

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX1, currentIndex)
        outState.putInt(KEY_INDEX2, trueAnswers)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState!=null){
            currentIndex=savedInstanceState.getInt(KEY_INDEX1, 0)
            trueAnswers=savedInstanceState.getInt(KEY_INDEX2,0)
            replaceFragment(mQuestionsFragment[currentIndex])
        }
        else initFragment()
    }

    private fun initFragment() {
        replaceFragment(FirstFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun finishQuestions() {
        replaceFragment(ResultFragment.newInstance("Верных ответов: $trueAnswers"))
    }

    override fun startQuestions() {
       replaceFragment(mQuestionsFragment[currentIndex])
    }

    override fun answerQuestion(result: Boolean) {
        if(checkAnswer(result)) trueAnswers++
        if(currentIndex==mQuestionsFragment.size-1){
            finishQuestions()
        }
        else {
            currentIndex=(currentIndex+1)
            replaceFragment(mQuestionsFragment[currentIndex])
        }
    }

    override fun checkAnswer (result: Boolean): Boolean{
        val getBundleArgs = mQuestionsFragment[currentIndex].arguments
        val booleanArg = getBundleArgs?.getBoolean("param1")
        return result==booleanArg
    }
}