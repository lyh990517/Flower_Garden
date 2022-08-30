package com.project.flower_garden

import android.content.ClipData
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.project.Entity.FlowerEntity
import com.project.Entity.OwnerEntity
import com.project.flower_garden.databinding.FragmentJoinOwnerBinding
import org.w3c.dom.Text
import java.net.PasswordAuthentication

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JoinOwner.newInstance] factory method to
 * create an instance of this fragment.
 */
class JoinOwner : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentJoinOwnerBinding
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
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJoinOwnerBinding.inflate(layoutInflater)
        OwnerDB = Firebase.database.reference.child("Owner")
        val view = inflater.inflate(R.layout.fragment_join_owner, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
        initJoinButton()
    }

    private fun initJoinButton(): View? = with(binding){

        joinButton.setOnClickListener {
            val id = valueIdCheck.text.toString()
            val pwd = valuePwCheck.text.toString()
            val nickname = valueNicknameCheck.text.toString()
            val store = valueStoreCheck.text.toString()
            val Owner = OwnerEntity(id,pwd,nickname,store, listOf(FlowerEntity("","")))
            OwnerDB.push().setValue(Owner)

            auth.createUserWithEmailAndPassword(id,pwd).addOnCompleteListener { Task  ->
                if(Task.isSuccessful){
                    OwnerDB.push().setValue(Owner)
                    Toast.makeText(context,"회원가입 성공", Toast.LENGTH_SHORT).show()

                    val database = Firebase.database("https://flowergarden-80899-default-rtdb.firebaseio.com/")

                    OwnerDB.child("nickName")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val value = dataSnapshot.getValue<String>()
                                Log.v("로그인 성공", "email : ${id}, pwd : ${pwd}, nickname : ${value}")
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                            }
                        })

                    navigationController.navigate(R.id.action_joinOwner_to_main)
                }else{
                    Log.e("1","${Task.exception?.message}")
                    when(Task.exception?.message){
                        "The email address is badly formatted." -> Toast.makeText(context,"이메일 형식으로 입력하시오.",
                            Toast.LENGTH_SHORT).show()
                        "The given password is invalid. [ Password should be at least 6 characters ]" -> Toast.makeText(context,"비밀번호는 6자리 이상",
                            Toast.LENGTH_SHORT).show()
                        "The email address is already in use by another account." -> Toast.makeText(context,"이미 존재하는 이메일",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JoinOwner.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JoinOwner().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}