package com.lzp.relativelayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = TestAdapter()
    }

    private inner class TestAdapter: BaseAdapter(){

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            return convertView ?: LayoutInflater.from(this@SecondActivity).inflate(R.layout.item_test, parent, false)
        }

        override fun getItem(position: Int): Any = 0

        override fun getItemId(position: Int): Long  = position.toLong()

        override fun getCount(): Int = 20

    }


}
