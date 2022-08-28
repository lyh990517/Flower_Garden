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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.flower_garden.databinding.FragmentLoginBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [login.newInstance] factory method to
 * create an instance of this fragment.
 */
class login : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var auth : FirebaseAuth? = null
    
    private lateinit var UserDB: DatabaseReference
    private lateinit var OwnerDB: DatabaseReference

    private lateinit var binding: FragmentLoginBinding

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
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        UserDB = Firebase.database.reference.child("User")
        OwnerDB = Firebase.database.reference.child("Owner")
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        signIn()
    }

    private fun signIn() = with(binding) {
        val id = valueIdCheck.text.toString()
        val pwd = valuePwCheck.text.toString()

        finalUserLoginButton.setOnClickListener {

            if (id.isNotEmpty() && pwd.isNotEmpty()) {
                auth?.signInWithEmailAndPassword(id, pwd)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.v("로그인 성공", "email : ${id}, pwd : ${pwd}")
                            Toast.makeText(activity, "로그인에 성공 하였습니다.", Toast.LENGTH_SHORT).show()
                            navigationController.navigate(R.id.action_login_to_secondMain)
                        } else {
                            Toast.makeText(activity, "로그인에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        finalOwnerLoginButton.setOnClickListener {
            if (id.isNotEmpty() && pwd.isNotEmpty()) {
                auth?.signInWithEmailAndPassword(id, pwd)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.v("로그인 성공", "email : ${id}, pwd : ${pwd}")
                            Toast.makeText(activity, "로그인에 성공 하였습니다.", Toast.LENGTH_SHORT).show()
                            navigationController.navigate(R.id.action_login_to_secondMain)
                        } else {
                            Toast.makeText(activity, "로그인에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            login().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}