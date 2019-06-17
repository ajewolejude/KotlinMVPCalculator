package com.thegreychain.kotlincalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.thegreychain.kotlincalculator.dependencyinjection.Injector
import com.thegreychain.kotlincalculator.view.CalculatorFragment

class CalculatorActivity : AppCompatActivity() {

    companion object {
        val VIEW: String = "VIEW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val manager: FragmentManager = this.supportFragmentManager

        //Elvis OperatorDataModel:
        //if (object/function) returns/is null :? do this
        val view = manager.findFragmentByTag(VIEW) as CalculatorFragment?
                ?: CalculatorFragment.newInstance(Injector(this))

        manager.beginTransaction().replace(R.id.root_activity_calculator, view).commit()

    }
}