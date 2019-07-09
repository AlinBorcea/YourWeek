package com.apps.a7pl4y3r.yourweek.independent

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.apps.a7pl4y3r.yourweek.R
import com.apps.a7pl4y3r.yourweek.databases.Daydb
import com.apps.a7pl4y3r.yourweek.helpers.RecyclerViewAdapter
import com.example.alin.yourweek.helpers.ItemOfRV

import kotlinx.android.synthetic.main.activity_update_task.*
import java.util.Collections
import kotlin.collections.ArrayList


class UpdateTask : AppCompatActivity() {


    private var delData = false
    private val idList = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppTheme(this)
        setContentView(R.layout.activity_update_task)
        setSupportActionBar(toolbarEditTasks)
        setData()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val roundBackground = getButtonDrawable(this)
            btExit.background = roundBackground
            btDeletion.background = roundBackground

        }

        btExit.setOnClickListener {
            getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).edit().putBoolean(valueSettTaskWasAdded, true).apply()
            finish()
        }

        btDeletion.setOnClickListener {


            if (!delData) {

                delData = true
                toastMessage(this, "Tap to select and press done to delete", true)
                btDeletion.text = "Done"

            } else {

                delData = false
                btDeletion.text = "Delete"
                deleteData()
                toastMessage(this, "Data deleted!", false)
                getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).edit().putBoolean(valueSettTaskWasAdded, true).apply()
                finish()

            }

        }
    }

    override fun onResume() {

        if (getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).getBoolean(valueSettTaskWasAdded, false)) {
            getSharedPreferences(settTaskWasAdded, Context.MODE_PRIVATE).edit().putBoolean(valueSettTaskWasAdded, false).apply()
            setData()
        }

        super.onResume()
    }

    private fun deleteData() {

        val db = Daydb(this, dayId())
        for (element in idList)
            db.deleteData(element.toString())
    }

    private fun dayId(): String {

        when (intent.getIntExtra("DAYID", 0)) {

            0 -> return "Monday"
            1 -> return "Tuesday"
            2 -> return "Wednesday"
            3 -> return "Thursday"
            4 -> return "Friday"
            5 -> return "Saturday"
            6 -> return "Sunday"
        }
        return "LOL"
    }


    private fun setData() {

        val dayId = dayId()
        val db = Daydb(this, dayId)
        val res = db.getData()

        if (res.count == 0) {
            toolbarEditTasks.title = "Nothing to edit - $dayId"

        } else {

        toolbarEditTasks.title = "Tap to edit - $dayId"
        res.moveToFirst()

        val itemList = ArrayList<ItemOfRV>()
         do {

            val id = res.getInt(0)
            itemList.add(
                ItemOfRV(
                    "${res.getString(5)}\n${res.getString(1)}:${res.getString(2)} - ${res.getString(3)}:${res.getString(4)}", id))
        } while (res.moveToNext())

            val mAdapter = RecyclerViewAdapter(this, itemList)
            recyclerUpdateTasks.setHasFixedSize(true)
            recyclerUpdateTasks.layoutManager = LinearLayoutManager(this)
            recyclerUpdateTasks.adapter = mAdapter

            mAdapter.setOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {

                override fun onItemClick(position: Int, card: CardView) {

                    if (delData) {

                        if (card.cardBackgroundColor == ContextCompat.getColorStateList(this@UpdateTask, android.R.color.holo_red_light)) {
                            card.setCardBackgroundColor(ContextCompat.getColorStateList(this@UpdateTask, R.color.greyText))
                            idList.remove(itemList[position].id)

                        } else {
                            card.setCardBackgroundColor(ContextCompat.getColorStateList(this@UpdateTask, android.R.color.holo_red_light))
                            idList.add(itemList[position].id)
                        }

                    } else {
                        startActivity(Intent(this@UpdateTask, EditTask::class.java).putExtra("DAYID", dayId).putExtra("TASKID", itemList[position].id))
                    }

                }
            })


            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT,
                ItemTouchHelper.DOWN or ItemTouchHelper.UP) {

                override fun onMove(recyclerView: RecyclerView, oldHolder: RecyclerView.ViewHolder, newHolder: RecyclerView.ViewHolder): Boolean {

                    val oldPosition = oldHolder.adapterPosition
                    val newPosition = newHolder.adapterPosition

                    Collections.swap(itemList, oldPosition, newPosition)
                    mAdapter.notifyItemMoved(oldPosition, newPosition)

                    return true
                }

                override fun onSwiped(holder: RecyclerView.ViewHolder, p1: Int) {



                }
            }).attachToRecyclerView(recyclerUpdateTasks)


         }

        }
    }