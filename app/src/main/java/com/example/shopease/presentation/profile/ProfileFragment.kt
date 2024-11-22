package com.example.shopease.presentation.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.shopease.databinding.FragmentProfileBinding
import com.example.shopease.presentation.login.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding

    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataUser = viewModel.getCurrentUser()
        binding?.apply {
            tvNameProfile.text = dataUser?.name
            tvEmailProfile.text = dataUser?.email
            Glide.with(requireContext())
                .load(dataUser?.photoUrl)
                .circleCrop()
                .into(ivProfile)
        }

        binding?.cvLogout?.setOnClickListener {
            showDeleteDialog()
        }
    }

    private fun showDeleteDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Logout")
        dialog.setMessage("Are you sure want to logout?")
        dialog.setPositiveButton("Yes") { _, _ ->

            viewModel.logout()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
            requireActivity().finish()


        }
        dialog.setNegativeButton("No") { _, _ ->
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}