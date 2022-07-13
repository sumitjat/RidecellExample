package com.example.ridecellexample.presentation.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.ridecellexample.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments ?: return

        val args = CharacterDetailFragmentArgs.fromBundle(bundle)
        val characterData = args.selectedCharacter

        binding.apply {
            userImage.load(characterData.image)
            userName.text = characterData.name
            userGender.text = "Gender : ${characterData.gender}"
            userStatus.text = "Status :${characterData.status}"
            userSpecies.text = "Species :${characterData.species}"

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}