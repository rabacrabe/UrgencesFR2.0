package urgencesfr.gtheurillat.urgencesfr.adapter

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import urgencesfr.gtheurillat.urgencesfr.activity.MainTabProsActivity;
import urgencesfr.gtheurillat.urgencesfr.activity.MainTabPersosActivity;

class MainTabAdapter//Constructor to the class
(fm: FragmentManager, //integer to count number of tabs
 internal var tabCount: Int) : FragmentStatePagerAdapter(fm) {
    private val tabTitles = arrayOf("Professionnels", "Personnels")

    init {

    }//Initializing tab count

    //Overriding method getItem
    override fun getItem(position: Int): Fragment? {
        //Returning the current tabs
        when (position) {
            0 -> return MainTabProsActivity.newInstance()

            1 -> return MainTabPersosActivity.newInstance()
            else -> return null
        }
    }

    //Overriden method getCount to get the number of tabs
    override fun getCount(): Int {
        return tabCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // Generate title based on item position
        return tabTitles[position]
    }
}