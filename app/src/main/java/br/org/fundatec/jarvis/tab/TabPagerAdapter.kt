package br.org.fundatec.jarvis.tab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.org.fundatec.jarvis.data.TabCharacter

class TabPagerAdapter(fm: FragmentManager, private val tabItems: List<TabCharacter>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return tabItems[position].fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabItems[position].title
    }

    override fun getCount(): Int {
        return tabItems.size
    }
}
