package com.project.flower_garden

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.system.Os.uname
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.project.Entity.FlowerEntity
import com.project.flower_garden.databinding.FragmentOwnerMainBinding
import java.nio.file.Files.find


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [OwnerMain.newInstance] factory method to
 * create an instance of this fragment.
 */
public class OwnerMain : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentOwnerMainBinding
    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }

    private lateinit var OwnerDB : DatabaseReference
    private lateinit var navigationController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        OwnerDB = Firebase.database.reference.child("Owner")
        binding = FragmentOwnerMainBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_owner_main, container, false)
        return view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addStoreImg()
        storeName()
    }

    private fun storeName() = with(binding) {
        OwnerDB.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if(snapshot.child("nickName").value != null) {
                    val storeName = snapshot.child("nickName").value.toString()
                    nickNameTextView.text = storeName
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun addStoreImg() = with(binding) {
        storeImageButton.setOnClickListener {
            Log.d(TAG, "눌렸음")
            when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                            navigatePhotos()
                }
            }
        }
    }

    private fun navigatePhotos() {
        //Content Provider
        //SAF(Storage Access Framwork)
        val intent = Intent(Intent.ACTION_GET_CONTENT) //SAF 기능에서 컨텐츠를 가져오는 함수
        intent.type = "image/*" //이미지의 모든 확장자를 가져옴 png, jpg . . .
        startActivityForResult(intent, 2000) // 콜백을 통해서 가져와야 되기 때문에 result key value를 2000으로 지정
        //다음으로 넘어가는 액티비티에서 result 값을 넘겨줘야할 때 (액티비티와 액티비티간에 데이터를 주고받을 수 없기 때문에)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OwnerMain.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OwnerMain().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}