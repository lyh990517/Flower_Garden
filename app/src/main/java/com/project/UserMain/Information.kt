package com.project.UserMain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.project.flower_garden.R
import com.project.flower_garden.databinding.FragmentInformationBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 [Fragment] subclass.
 [Information.newInstance]
 */
class Information : Fragment() {

    private lateinit var binding: FragmentInformationBinding


    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentInformationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveFragment(R.id.ic_information)
    }

    private fun moveFragment(fragment: Int) = with(binding) {
        val navController = Navigation.findNavController(requireView())

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.ic_home -> {
                    navController.navigate(R.id.action_information_to_userMain)
                    return@setOnItemSelectedListener true
                }

                R.id.ic_bottom_map -> {
                    navController.navigate(R.id.action_information_to_nearLocation)
                    return@setOnItemSelectedListener true
                }

                R.id.ic_heart -> {
                    navController.navigate(R.id.action_information_to_like)
                    return@setOnItemSelectedListener true
                }

                else -> { false }
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
         * @return A new instance of fragment Information.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Information().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}