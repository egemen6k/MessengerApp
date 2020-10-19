package com.example.messengerapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TableLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.messengerapp.Fragments.ChatsFragment
import com.example.messengerapp.Fragments.SearchFragment
import com.example.messengerapp.Fragments.SettingsFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        /*default behaviouru değiştirmek için  attaki kısmı yazıyoruz*/
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        /*hiç bir zaman null olmasını istemiyoruz çünkü oraya username gelecek
        eğer bu  işlemi yapmazsak(null olması durumunda) o kısımda application name default behaviour
        olarak gösterilir ve biz bunu istemiyoruz*/
        supportActionBar!!.title=""

        /*accessing tablayout*/
        val tabLayout: TabLayout =  findViewById(R.id.tab_layout)

        /*accessing  viewpager*/
        val viewPager: ViewPager =  findViewById(R.id.view_pager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(ChatsFragment(),"Chats")
        viewPagerAdapter.addFragment(SearchFragment(),"Search")
        viewPagerAdapter.addFragment(SettingsFragment(),"Settings")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this,WelcomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()

                return true
            }
        }
        return false
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager)  :
        FragmentPagerAdapter(fragmentManager){

        /*iki tane array list oluşturduk. biri fragmentlardan diğeri stringlerden
        oluşacak diye  belirttik. Burada sadece tanımlama yaptık*/
        private val fragments:ArrayList<Fragment>
        private val titles:ArrayList<String>

        init {
            /*burada, yukarıda tipini tanımlamış olduğumuz variableları initialize  ettik
             arratlistof(.....)  diye statik  data girebilirdik. ilk durumu boş olan bir liste
             yarattık ve bıraktık*/
            fragments = ArrayList<Fragment>()
            titles = ArrayList<String>()
        }


        override fun getCount(): Int {
        return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment,title:String){
            fragments.add(fragment)
            titles.add(title)
        }

       /* tablayout page title'ı bize  veriyor*/
        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }
    }
}