package com.application.assignmentkotlin.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.application.assignmentkotlin.R
import com.application.assignmentkotlin.addFragment
import com.application.assignmentkotlin.db.TaskData
import com.bumptech.glide.Glide

class TaskAdapter(context: Context ,listResponseDashboard:List<TaskData>,activity: AppCompatActivity) :  RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var activity: AppCompatActivity
    private var context: Context = context
    private var listResponseDashboard:List<TaskData>


    init {
        this.listResponseDashboard=listResponseDashboard
        this.activity=activity
    }
    class TaskViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_task_item, parent, false)) {
        var txtId: TextView? = null
        var txtName: TextView? = null
        var txtFullName: TextView? = null
        var imgAvatar:ImageView?=null
        var layoutList:RelativeLayout?=null
        init {
            txtId = itemView.findViewById(R.id.txt_id)
            txtName= itemView.findViewById(R.id.txt_name)
            txtFullName= itemView.findViewById(R.id.txt_full_name)
            imgAvatar= itemView.findViewById(R.id.img_avatar)
            layoutList=itemView.findViewById(R.id.layout_list)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(
        holder:TaskViewHolder,
        position: Int
    ) {
        holder.txtId?.setText( "Id :" + listResponseDashboard.get(position).id)

        holder.txtName?.setText(  "Name :"+ listResponseDashboard.get(position).name)
        holder.txtFullName?.setText("Full Name :"+ listResponseDashboard.get(position).full_name)
        val url:String? =listResponseDashboard.get(position).owner.avatar_url
        holder.imgAvatar?.let {
            Glide.with(context)  //2
                .load(url) //3
                .circleCrop()
                .placeholder(R.drawable.no_img) //5
                .error(R.drawable.no_img) //6
                .fallback(R.drawable.no_img) //7
                .into(it)
        } //8
        holder.layoutList?.setOnClickListener {
            activity.addFragment(DetailedFragment(listResponseDashboard.get(position)))

        }

    }
    /*companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        private fun loadImage(imgAvatar: ImageView, url:String) {
            Glide.with(context).load(url).into(this)
        }


    }*/

    override fun getItemCount(): Int {
        return listResponseDashboard.size
    }

}
