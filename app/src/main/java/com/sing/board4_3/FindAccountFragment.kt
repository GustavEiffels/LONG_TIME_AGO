package com.sing.board4_3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sing.board4_3.databinding.FragmentFindAccountBinding


class FindAccountFragment : Fragment() {

    lateinit var binding : FragmentFindAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFindAccountBinding.inflate(layoutInflater)





        return binding.root
    }

}