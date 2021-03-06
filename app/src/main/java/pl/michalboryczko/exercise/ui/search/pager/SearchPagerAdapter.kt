package pl.michalboryczko.exercise.ui.search.pager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import pl.michalboryczko.exercise.R
import pl.michalboryczko.exercise.ui.markwords.MarkWordsFragment

class SearchPagerAdapter(val fm: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int  = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MarkWordsFragment()
            1 -> WordsToLearnFragment()
            else -> WordsLearnedFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.search_menu_all)
            1 -> context.getString(R.string.search_menu_to_learn)
            else -> context.getString(R.string.search_menu_learned)
        }
    }
}