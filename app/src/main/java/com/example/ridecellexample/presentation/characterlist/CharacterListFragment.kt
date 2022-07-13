package com.example.ridecellexample.presentation.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ridecellexample.R
import com.example.ridecellexample.data.model.CharacterData
import com.example.ridecellexample.data.util.Resource
import com.example.ridecellexample.databinding.FragmentCharacterListBinding
import com.example.ridecellexample.presentation.adapter.CharacterAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val viewModel: CharacterListViewModel by viewModels()
    private lateinit var imageAdapter: CharacterAdapter

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSearchedImage("", 40)

        initRecyclerView()
        initActions()
        setupObserver()


        binding.simpleSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getSearchedImage(query, 40)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getSearchedImage(newText, 40)
                return false
            }
        }
        )
    }

    private fun initRecyclerView() {
        imageAdapter = CharacterAdapter()
        binding.recyclerview.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun initActions() {
        imageAdapter.setOnItemClickListener(object : CharacterAdapter.OnItemClickListener {
            override fun onItemClick(characterData: CharacterData) {
                (requireActivity() as AppCompatActivity).supportActionBar?.hide()
                val bundle = Bundle().apply {
                    putParcelable("selected_character", characterData)
                }
                findNavController().navigate(
                    R.id.action_characterListFragment_to_characterDetailFragment,
                    bundle
                )
            }
        })
    }

    private fun setupObserver() {
        viewModel.characters.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.progressIndicator.isVisible = true
                is Resource.Error -> {
                    binding.progressIndicator.isVisible = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    binding.progressIndicator.isVisible = false
                    it.data?.let { it1 -> imageAdapter.submitList(it1) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
