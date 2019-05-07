package id.test.kostbenhil

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.View
import android.widget.Toast
import id.test.kostbenhil.screen.beranda.BerandaFragment
import id.test.kostbenhil.screen.keuangan.KeuanganFragment
import id.test.kostbenhil.screen.pengaturan.PengaturanFragment
import id.test.kostbenhil.screen.penyewa.PenyewaFragment
import id.test.kostbenhil.shared.supportclasses.NavBottomKos
import id.test.kostbenhil.shared.supportclasses.NonSwipeableViewPager
import id.test.kostbenhil.shared.supportclasses.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private var fragmentBeranda: Fragment? = null
    private var fragmentHistoryTransaction: Fragment? = null
    private var fragmentNotification: Fragment? = null
    private var fragmentSetting: Fragment? = null

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        initView()
        setUpViewPager(viewPagerHome)
//        initSideBar()
    }

    private fun initView() {
        navBottom.setOnNavbarClick(navBarOnClickListener)
    }

    private val navBarOnClickListener = object : NavBottomKos.NavBottomListener {
        override fun onClickHome() { setSelected(0) }
        override fun onClickHistory() { setSelected(1) }
        override fun onClickNotification() { setSelected(2) }
        override fun onClickOther() { setSelected(3) }
    }

    private fun setSelected(position: Int) {
        Log.d(TAG, "onNewIntent-setSelected : $position")
        viewPagerHome.currentItem = position
    }

    private fun setUpViewPager(viewPagerHome: NonSwipeableViewPager?) {
        viewPagerHome?.offscreenPageLimit = 5
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(BerandaFragment.newInstance())
        viewPagerAdapter.addFragment(PenyewaFragment.newInstance())
        viewPagerAdapter.addFragment(KeuanganFragment.newInstance())
        viewPagerAdapter.addFragment(PengaturanFragment.newInstance())
        viewPagerHome?.adapter = viewPagerAdapter
        fragmentBeranda = viewPagerAdapter.getItem(0)
        fragmentHistoryTransaction = viewPagerAdapter.getItem(1)
        fragmentNotification = viewPagerAdapter.getItem(2)
        fragmentSetting = viewPagerAdapter.getItem(3)

        viewPagerHome?.addOnPageChangeListener(onPageChangeListener)
    }

    private val onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageSelected(position: Int) {
            when(position) {
                0 -> (fragmentBeranda as BerandaFragment).refreshPage()
                1 -> (fragmentHistoryTransaction as PenyewaFragment).refreshPage()
//                2 -> (fragmentNotification as KeuanganFragment).refreshPage()
//                3 -> (fragmentSetting as PengaturanFragment).onAttach()
            }
        }
    }

//    private fun initSideBar(){
//
//        val actionBar = supportActionBar
//        actionBar?.title = "Hello Toolbar"
//
//        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
//            this,
//            drawer_layout,
//            toolbar,
//            R.string.open,
//            R.string.close
//        ){
//            override fun onDrawerClosed(view: View){
//                super.onDrawerClosed(view)
//                //toast("Drawer closed")
//            }
//
//            override fun onDrawerOpened(drawerView: View){
//                super.onDrawerOpened(drawerView)
//                //toast("Drawer opened")
//            }
//        }
//
//        // Configure the drawer layout to add listener and show icon on toolbar
//        drawerToggle.isDrawerIndicatorEnabled = true
//        drawer_layout.addDrawerListener(drawerToggle)
//        drawerToggle.syncState()
//
//        // Set navigation view navigation item selected listener
//        navigation_view.setOnClickListener{
//            when (it.id){
//                R.id.action_cut -> {
//                        // Multiline action
//                        drawer_layout.setBackgroundColor(Color.RED)
//                    }
//                }
//
//            }
//            // Close the drawer
//            drawer_layout.closeDrawer(GravityCompat.START)
//            true
//        }

}
