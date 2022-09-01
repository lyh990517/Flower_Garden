package com.project.flower_garden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.project.flower_garden.databinding.FragmentUserMainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UserMain : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentUserMainBinding

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
        binding = FragmentUserMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewFlipper()
        moveFragment(R.id.ic_home)

    }


    private fun moveFragment(fragment: Int) = with(binding) {
        val navController = Navigation.findNavController(requireView())
//        val navController = NavHostFragment.findNavController(fragment = UserMain())
        bottomNavigationView.setOnItemSelectedListener { item->

            when(item.itemId) {
                R.id.ic_heart -> {
                    navController.navigate(R.id.action_userMain_to_like)
                    return@setOnItemSelectedListener true
                }
                R.id.ic_bottom_map -> {
                    navController.navigate(R.id.action_userMain_to_nearLocation)
                    return@setOnItemSelectedListener true
                }
                R.id.ic_information -> {
                    navController.navigate(R.id.action_userMain_to_information)
                }
                else -> { false }

            }
            return@setOnItemSelectedListener true
        }

    }

    private fun viewFlipper() = with(binding) {
        viewFlipper.startFlipping()
        viewFlipper.flipInterval = 3000
        viewFlipper.setInAnimation(activity?.applicationContext, android.R.anim.slide_in_left)
        viewFlipper.setOutAnimation(activity?.applicationContext, android.R.anim.slide_out_right)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondMain.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserMain().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

