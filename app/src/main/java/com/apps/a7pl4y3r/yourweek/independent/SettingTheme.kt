package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

import com.apps.a7pl4y3r.yourweek.R

import kotlinx.android.synthetic.main.activity_setting_theme.*


class SettingTheme : AppCompatActivity() {

    private var theme: String = "SkyBlue"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme()
        setContentView(R.layout.activity_setting_theme)
        //setSwitch()
        setCheckedRadioButton()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val roundBackground = getButtonDrawable(this)
            btCancel.background = roundBackground
            btSaveTheme.background = roundBackground

        }

        /*switchThemes.setOnClickListener {

            if(switchThemes.isChecked) {
                getSharedPreferences(setSwitchTheme, Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean(valSetSwitchTheme, true)
                    .apply()

                startActivity(Intent(this, ThemeSwitcher::class.java))

            } else {

                getSharedPreferences(setSwitchTheme, Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean(valSetSwitchTheme, false)
                    .apply()

                Toast.makeText(this, "Switch is off", Toast.LENGTH_SHORT).show()
            }
        }*/

        btCancel.setOnClickListener {
            finish()
        }

        btSaveTheme.setOnClickListener {
            saveTheme()
            finish()
        }

    }

    private fun setAppTheme() {

        val sharedPreferences = getSharedPreferences(settTheme, Context.MODE_PRIVATE)
        when(sharedPreferences.getInt(valueSettTheme, 1)) {

            //SkyBlue
            1 -> {
                setTheme(R.style.AppTheme)
                theme = "SkyBlue"
            }
            //OceanDarkBlueTheme
            2 -> {
                setTheme(R.style.OceanDarkBlueTheme)
                theme = "OceanDarkBlueTheme"
            }
            //GrassGreenTheme
            3 -> {
                setTheme(R.style.GrassGreenTheme)
                theme = "GrassGreenTheme"
            }
            //MarsRedTheme
            4 -> {
                setTheme(R.style.MarsRedTheme)
                theme = "MarsRedTheme"
            }
            5 -> {
                setTheme(R.style.AMOLEDDark)
                theme = "AMOLEDDark"
            }
            //ImperialPurple
            6 -> {
            setTheme(R.style.ImperialPurple)
            theme = "ImperialPurple"
            }
            //OrangeBrick
            7 -> {
                setTheme(R.style.OrangeBrick)
                theme = "OrangeBrick"
            }
        }
    }

    /*private fun setSwitch() {
        switchThemes.isChecked = getSharedPreferences(setSwitchTheme, Context.MODE_PRIVATE)
            .getBoolean(valSetSwitchTheme, false)
    }*/

    private fun setCheckedRadioButton() {

        val sharedPreferences = getSharedPreferences(settTheme, Context.MODE_PRIVATE)
        when(sharedPreferences.getInt(valueSettTheme, 1)) {

            //SkyBlue
            1 -> rbAppTheme.isChecked = true
            //OceanDarkBlueTheme
            2 -> rbOceanDarkBlue.isChecked = true
            //GrassGreenTheme
            3 -> rbGrassGreenTheme.isChecked = true
            //MarsRedTheme
            4 -> rbMarsRedTheme.isChecked = true
            //AmoledDarkTheme
            5 -> rbAmoledDark.isChecked = true
            //Imperial Purple
            6 -> rbImperialPurple.isChecked = true
            //OrangeBrick
            7 -> rbOrangeBrick.isChecked = true
        }
    }

    private fun saveTheme() {

        val radioButtonId = rgTheme.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(radioButtonId)

        val sharedPreferences = getSharedPreferences(settTheme, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val sharedPreferencesTheme = getSharedPreferences(SettChangedTheme, Context.MODE_PRIVATE)
        val editorTheme = sharedPreferencesTheme.edit()

        getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).edit().putBoolean(valueSettTaskWasAdded, true).apply()

        when(radioButton.text.toString()) {

            "Sky blue" -> {
                editor.putInt(valueSettTheme, 1)
                editorTheme.putBoolean(valueSettChangedTheme, true)
                editor.apply()
                editorTheme.apply()
            }
            "Ocean dark blue" -> {
                editor.putInt(valueSettTheme, 2)
                editorTheme.putBoolean(valueSettChangedTheme, true)
                editor.apply()
                editorTheme.apply()
            }
            "Grass green" -> {
                editor.putInt(valueSettTheme, 3)
                editorTheme.putBoolean(valueSettChangedTheme, true)
                editor.apply()
                editorTheme.apply()
            }
            "Mars red" -> {
                editor.putInt(valueSettTheme, 4)
                editorTheme.putBoolean(valueSettChangedTheme, true)
                editor.apply()
                editorTheme.apply()
            }
            "AMOLED dark" -> {
                editor.putInt(valueSettTheme, 5)
                editorTheme.putBoolean(valueSettChangedTheme, true)
                editor.apply()
                editorTheme.apply()
            }
            "Imperial Purple" -> {
                editor.putInt(valueSettTheme, 6)
                editorTheme.putBoolean(valueSettChangedTheme, true)
                editor.apply()
                editorTheme.apply()
            }
            "Orange Brick" -> {
                editor.putInt(valueSettTheme, 7)
                editorTheme.putBoolean(valueSettChangedTheme, true)
                editor.apply()
                editorTheme.apply()
            }
        }
    }

}