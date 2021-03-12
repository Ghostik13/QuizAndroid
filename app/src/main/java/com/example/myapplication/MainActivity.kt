package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlin.random.Random.Default.nextInt


interface MyInterface {
    fun startQuestions()
    fun finishQuestions()
    fun answerQuestion(result: Boolean)
    fun checkAnswer(result: Boolean): Boolean
}

class MainActivity : AppCompatActivity(), MyInterface {

    private var currentIndex = 0
    private var trueAnswers = 0

    private val mQuestionsFragment = arrayOf(
        QuestionsFragment.newInstance(true, "Озеро Байкал - самое глубокое в мире"),
        QuestionsFragment.newInstance(true, "Воздух на 78% состоит из азота"),
        QuestionsFragment.newInstance(false, "50% планеты Земля занимает вода"),
        QuestionsFragment.newInstance(false, "В радуге 8 цветов"),
        QuestionsFragment.newInstance(true, "Звезды - не самые яркие объекты во вселенной"),
        QuestionsFragment.newInstance(true, "Канберра - столица Австралии"),
        QuestionsFragment.newInstance(false, "Самый глубокий океан на Земле - Атлантический"),
        QuestionsFragment.newInstance(false, "Nissan Skyline GT-R оснащается турбированным двигателем"),
        QuestionsFragment.newInstance(true, "Apple Maps является неудачным проектом компании Apple"),
        QuestionsFragment.newInstance(false, "Население Земли больше 8 миллиардов человек")
    )


    private fun shuffle(array: Array<QuestionsFragment>) {
        for (i in array.size - 1 downTo 1) {
            val index = nextInt(i + 1)
            val a = array[index]
            array[index] = array[i]
            array[i] = a
        }
    }

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
        shuffle(mQuestionsFragment)
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
            currentIndex+=1
            replaceFragment(mQuestionsFragment[currentIndex])
        }
    }

    override fun checkAnswer (result: Boolean): Boolean{
        val getBundleArgs = mQuestionsFragment[currentIndex].arguments
        val booleanArg = getBundleArgs?.getBoolean("param1")
        return result==booleanArg
    }
}