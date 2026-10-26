package com.example.tenantsys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tenantsys.databinding.FragmentAddTenantBinding

class AddTenantFragment : Fragment() {

    private var _binding: FragmentAddTenantBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTenantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            val name = binding.tenantNameEditText.text.toString().trim()
            val phone = binding.phoneEditText.text.toString().trim()
            val rent = binding.rentEditText.text.toString().trim()

            var isValid = true

            if (name.isEmpty()) {
                binding.tenantNameEditText.error = "Required"
                isValid = false
            }

            if (phone.isEmpty()) {
                binding.phoneEditText.error = "Required"
                isValid = false
            } else if (phone.length < 10) {
                binding.phoneEditText.error = "Enter a valid phone number"
                isValid = false
            }

            if (rent.isEmpty()) {
                binding.rentEditText.error = "Required"
                isValid = false
            }

            if (!isValid) {
                Toast.makeText(requireContext(), "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tenant = Tenant(name, phone, rent)
            TenantRepository.addTenant(tenant)

            val action = AddTenantFragmentDirections.actionAddTenantFragmentToViewTenantsFragment(tenant)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
