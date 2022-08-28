package com.project.flower_garden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter(val profileList: ArrayList<Profiles>): RecyclerView.Adapter<ProfileAdapter.CustomViewHolder>() {

    //Activity onCreate와 유사 = list_item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
            }
        }
    }

    //리스트를 출력한 총 갯수
    override fun getItemCount(): Int = profileList.size


    //실제 연결구간 (스크롤 할 때마다 매치)
    override fun onBindViewHolder(holder: ProfileAdapter.CustomViewHolder, position: Int) {
        holder.img.setImageResource(profileList.get(position).img)
        holder.storeName.text = profileList.get(position).storeName
        holder.storeLocation.text = profileList.get(position).storeLocation
        holder.likeImageView.setImageResource(profileList.get(position).likeImg)
        holder.likeNumber.text = profileList.get(position).likeNumber.toString()
    }

    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        //Data class Model 활용구간
        //뷰 연결시 사용
        val img = itemView.findViewById<AppCompatImageView>(R.id.item_Img) //성별
        val storeName = itemView.findViewById<TextView>(R.id.storeNameTextView)
        val storeLocation  = itemView.findViewById<TextView>(R.id.storeLocationTextView)
        val likeImageView = itemView.findViewById<AppCompatImageView>(R.id.likeImageView) //성별
        val likeNumber = itemView.findViewById<TextView>(R.id.likeNumber)
    }
}