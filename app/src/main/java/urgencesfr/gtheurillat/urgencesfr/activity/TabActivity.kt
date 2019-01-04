package urgencesfr.gtheurillat.urgencesfr.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import urgencesfr.gtheurillat.urgencesfr.R
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import urgencesfr.gtheurillat.urgencesfr.adapter.MainTabAdapter
import android.content.DialogInterface
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem


class TabActivity : AppCompatActivity() {

   // private var tabLayout: TabLayout? = null
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: MainTabAdapter
    private lateinit var mainContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        mainContext = this;

        try {
            tabLayout = findViewById<TabLayout>(R.id.tabLayout_main)
            //Adding the tabs using addTab() method
            tabLayout.addTab(tabLayout.newTab().setText("Professionnels"))
            tabLayout.addTab(tabLayout.newTab().setText("Personnels"))

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL)

            //Creating our pager adapter
            adapter = MainTabAdapter(supportFragmentManager, tabLayout.getTabCount())

            viewPager = findViewById<View>(R.id.pager) as ViewPager
            //Adding adapter to pager
            viewPager.setAdapter(adapter)

            // Give the TabLayout the ViewPager
            val tabLayout = findViewById(R.id.tabLayout_main) as TabLayout
            tabLayout.setupWithViewPager(viewPager)

        } catch (e: Exception) {
            showError(e.toString())
        }


    }

    fun showError(message: String) {
        val alertDialogBuilder: AlertDialog.Builder
        alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle("Erreur")

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id ->
                    // if this button is clicked, close
                    // current activity
                    this@TabActivity.finish()
                })
        // create alert dialog
        val alertDialog = alertDialogBuilder.create()

        // show it
        alertDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_about -> {
                //Snackbar.make(, "Version: 3.0", Snackbar.LENGTH_LONG).setAction("A propos", null).show()
                val builder_about = AlertDialog.Builder(this)

                // Set the alert dialog title
                builder_about.setTitle("A propos")
                builder_about.setMessage("Version 2.0 (Janvier 2019). Developpeur: Gael THEURILLAT")
                val dialog_about: AlertDialog = builder_about.create()

                // Display the alert dialog on app interface
                dialog_about.show()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
