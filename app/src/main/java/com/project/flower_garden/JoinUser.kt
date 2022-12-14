package com.project.flower_garden

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.Entity.UserEntity
import com.project.flower_garden.databinding.FragmentJoinUserBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JoinUser.newInstance] factory method to
 * create an instance of this fragment.
 */
class JoinUser : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val auth : FirebaseAuth by lazy{
        Firebase.auth
    }
    private lateinit var UserDB : DatabaseReference

    private lateinit var binding: FragmentJoinUserBinding

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
        binding = FragmentJoinUserBinding.inflate(layoutInflater)
        UserDB = Firebase.database.reference.child("User")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
        initJoinButton()
    }

    private fun initJoinButton() = with(binding){

        joinButton.setOnClickListener {

            val id = valueIdCheck.text.toString()
            val pwd = valuePwCheck.text.toString()
            val nickname = valueNicknameCheck.text.toString()
            val User = UserEntity(id,pwd,nickname)
            auth.createUserWithEmailAndPassword(id,pwd).addOnCompleteListener { Task  ->
                if(Task.isSuccessful){
                    UserDB.push().setValue(User)
                    Toast.makeText(context,"???????????? ??????",Toast.LENGTH_SHORT).show()
                    navigationController.navigate(R.id.action_joinUser_to_main)
                }else{
                    Log.e("1","${Task.exception?.message}")
                    when(Task.exception?.message){
                        "The email address is badly formatted." -> Toast.makeText(context,"????????? ???????????? ???????????????.",Toast.LENGTH_SHORT).show()
                        "The given password is invalid. [ Password should be at least 6 characters ]" -> Toast.makeText(context,"??????????????? 6?????? ??????",Toast.LENGTH_SHORT).show()
                        "The email address is already in use by another account." -> Toast.makeText(context,"?????? ???????????? ?????????",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JoinUser.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JoinUser().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}