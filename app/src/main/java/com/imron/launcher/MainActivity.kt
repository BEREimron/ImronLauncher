package com.imron.launcher

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listView = ListView(this)
        setContentView(listView)

        val pm = packageManager

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val apps = pm.queryIntentActivities(intent, 0)
            .sortedBy { it.loadLabel(pm).toString() }

        val names = apps.map { it.loadLabel(pm).toString() }

        listView.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            names
        )

        listView.setOnItemClickListener { _, _, position, _ ->
            val info = apps[position]
            val launchIntent = pm.getLaunchIntentForPackage(
                info.activityInfo.packageName
            )
            startActivity(launchIntent)
        }
    }
}
