package com.example.bitbnsclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bitbnsclone.databinding.FragmentSignUpBinding
import com.example.bitbnsclone.db.DataBaseHelper

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    lateinit var handler: DataBaseHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        handler = activity?.let { DataBaseHelper(it) }!!



        // Register Button Clicked
        binding.signupButton.setOnClickListener{
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            handler.insertuserData(email,password)
            Toast.makeText(requireContext(),"Credentials Generated",Toast.LENGTH_SHORT).show()
        }

         return binding.root
    }

}