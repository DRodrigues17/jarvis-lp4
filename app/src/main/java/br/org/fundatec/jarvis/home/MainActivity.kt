package br.org.fundatec.jarvis.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import br.org.fundatec.jarvis.character.CharacterFragment
import br.org.fundatec.jarvis.character.CreateCharacterActivity
import br.org.fundatec.jarvis.character.domain.model.CharacterType
import br.org.fundatec.jarvis.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configTab()

        binding.fbNewCharacter.setOnClickListener {
            val navegateToNewCharacterActivity = Intent(this, CreateCharacterActivity::class.java)
            startActivity(navegateToNewCharacterActivity)
        }
    }

    private fun configTab() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        binding.vpHome.adapter = adapter
        binding.tlHome.setupWithViewPager(binding.vpHome)
    }
}

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return CharacterType.values()[position].type
    }

    override fun getItem(position: Int): Fragment {
        return CharacterFragment.newInstance(CharacterType.values()[position])
    }

}
