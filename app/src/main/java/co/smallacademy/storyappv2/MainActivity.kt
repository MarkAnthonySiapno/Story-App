package co.smallacademy.storyappv2

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var storyTitles = arrayOf<String>()
    var storyContents = arrayOf<String>()
    var storyImages = arrayOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        storyTitles = resources.getStringArray(R.array.storyTitles)
        storyContents = resources.getStringArray(R.array.storyContents)
        storyImages = resources.getStringArray(R.array.storyImages)


        val adapter = ItemAdapter(storyTitles,storyContents,storyImages)
        storyList.layoutManager = LinearLayoutManager(this)
        storyList.adapter = adapter
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        if(item.itemId == R.id.random){
            val randPosition = Random.nextInt(0,storyTitles.size)
            val intent = Intent(applicationContext,Details::class.java)
            intent.putExtra("storyTitle",storyTitles[randPosition])
            intent.putExtra("storyContent",storyContents[randPosition])
            intent.putExtra("storyImage",storyImages[randPosition])
            startActivity(intent)

        }
        if(item.itemId == R.id.contact){
            val emailIntent = Intent(Intent.ACTION_SENDTO,
            Uri.fromParts("mailto", "marksiapno8@gmail.com", null))
            startActivity(Intent.createChooser(emailIntent, "send email.."))
        }
        if(item.itemId == R.id.rate){
            val uri: Uri = Uri.parse("market;//details?id=au.com.rateus")
            val goToMarktet = Intent(Intent.ACTION_VIEW,uri)
            goToMarktet.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                                  Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                  Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            try{
                startActivity(goToMarktet)
            }catch (e: ActivityNotFoundException){
                startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=au.com.rateus")))

            }
        }
        return true
    }
}