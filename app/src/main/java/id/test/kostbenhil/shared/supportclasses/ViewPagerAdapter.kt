package id.test.kostbenhil.shared.supportclasses

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val listFragments = mutableListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        return listFragments[position]
    }

    override fun getCount(): Int {
        return listFragments.size
    }

    fun addFragment(fragment: Fragment) {
        listFragments.add(fragment)
    }
}

