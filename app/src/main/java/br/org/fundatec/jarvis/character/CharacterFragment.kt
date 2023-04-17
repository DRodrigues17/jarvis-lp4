package br.org.fundatec.jarvis.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.org.fundatec.jarvis.adapter.CharacterListAdapter
import br.org.fundatec.jarvis.character.domain.model.CharacterType
import br.org.fundatec.jarvis.databinding.FragmentCharacterBinding
import com.google.android.material.snackbar.Snackbar

private const val CHARACTER_TYPE = "CHARACTER_TYPE"

class CharacterFragment : Fragment() {
    private lateinit var binding: FragmentCharacterBinding

    private val adapter: CharacterListAdapter by lazy { CharacterListAdapter() }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(CharacterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(arguments?.getInt(CHARACTER_TYPE))

        binding.characterList.adapter = adapter
        configObservers()

    }


    private fun configObservers() {
        viewModel.viewState.observe(requireActivity()) { state ->
            when (state) {
                is CharacterViewState.ShowLoading -> binding.pbLoading.isVisible = true
                is CharacterViewState.ShowContent -> {
                    binding.pbLoading.isVisible = false
                    adapter.adicionarItensLista(state.listaPersonagens)
                }
                is CharacterViewState.ShowMessageError -> {
                    Snackbar.make(
                        binding.container, state.messageId, Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    companion object {
        fun newInstance(type: CharacterType) =
            CharacterFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHARACTER_TYPE, type.ordinal)
                }
            }
    }
}