package pl.michalboryczko.exercise.ui.activesession

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentPagerAdapter(supportFragmentManager) {
    private val mList = mutableListOf<Fragment>()
    private val mTitleList = mutableListOf<String>()

    override fun getCount(): Int = mList.size

    override fun getItem(i: Int): Fragment {
        return mList.get(i)
    }

    fun addFragment(fragment: Fragment, title: String) {
        mList.add(fragment)
        mTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitleList.get(position)
    }
}