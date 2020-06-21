package com.application.assignmentkotlin.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.application.assignmentkotlin.R
import com.application.assignmentkotlin.db.TaskData

class DetailedFragment(taskData: TaskData) : Fragment() {

     val taskData: TaskData
    lateinit var txtname:TextView
    lateinit var txtFullName:TextView
    lateinit var txtFullDescription:TextView
    lateinit var txtLink:TextView

    init {
        this.taskData=taskData
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(taskData!=null) {
            txtname.setText("Name:"+taskData.name)
            txtFullName.setText("Full Name:"+taskData.full_name)
            txtFullDescription.setText("Description: "+taskData.description)
            txtLink.setText("URL: "+taskData.html_url)
            txtLink.setOnClickListener {

                val uris = Uri.parse(taskData.html_url)
                val intents = Intent(Intent.ACTION_VIEW, uris)
                val b = Bundle()
                b.putBoolean("new_window", true)
                intents.putExtras(b)
                context?.startActivity(intents)
            }
        }
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
            val view=  inflater.inflate(R.layout.fragment_detail, container, false)
        txtname=view.findViewById(R.id.txt_names)
        txtFullName=view.findViewById(R.id.txt_full_names)
        txtFullDescription=view.findViewById(R.id.txt_full_descriptions)
        txtLink=view.findViewById(R.id.txt_links)
        return view
    }
}
