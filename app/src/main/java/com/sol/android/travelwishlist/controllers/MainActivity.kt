package com.sol.android.travelwishlist.controllers

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import com.sol.android.travelwishlist.R
import com.sol.android.travelwishlist.adapters.TravelListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

  /*------------------------------------------------------------------*\
  |*							                ATTRIBUTES
  \*------------------------------------------------------------------*/

  lateinit private var staggeredLayoutManager: StaggeredGridLayoutManager
  lateinit private var adapter: TravelListAdapter
  lateinit private var menu: Menu
  private var isListView: Boolean = false

  private val onItemClickListener = object : TravelListAdapter.OnItemClickListener {

    override fun onItemClick(view: View, position: Int) {
      val transitionIntent = DetailActivity.newIntent(this@MainActivity, position)
      val placeImage = view.findViewById<ImageView>(R.id.placeImage)
      val placeNameHolder = view.findViewById<LinearLayout>(R.id.placeNameHolder)

      val navigationBar = findViewById<View>(android.R.id.navigationBarBackground)
      val statusBar = findViewById<View>(android.R.id.statusBarBackground)

      val imagePair = Pair.create(placeImage as View, "tImage")
      val holderPair = Pair.create(placeNameHolder as View, "tNameHolder")

      val navPair =
        Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME)

      val statusPair =
        Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)

      val toolbarPair = Pair.create(toolbar as View, "tActionBar")

      val pairs = mutableListOf(imagePair, holderPair, statusPair, toolbarPair)
      if (navigationBar != null && navPair != null) {
        pairs += navPair
      }

      val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
              this@MainActivity,
              *pairs.toTypedArray()
      )
      ActivityCompat.startActivity(
              this@MainActivity,
              transitionIntent,
              options.toBundle()
      )
    }

  }

  /*------------------------------------------------------------------*\
  |*							                HOOKS
  \*------------------------------------------------------------------*/

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    isListView = true

    val spanCount = 1
    val orientation = StaggeredGridLayoutManager.VERTICAL
    staggeredLayoutManager = StaggeredGridLayoutManager(spanCount, orientation)
    list.layoutManager = staggeredLayoutManager

    adapter = TravelListAdapter(this)
    list.adapter = adapter

    adapter.setOnItemClickListener(onItemClickListener)
    setUpActionBar()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.menu_main, menu)
    this.menu = menu
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId
    if (id == R.id.action_toggle) {
      toggle()
      return true
    }
    return super.onOptionsItemSelected(item)
  }

  /*------------------------------------------------------------------*\
  |*							                METHODES
  \*------------------------------------------------------------------*/

  private fun setUpActionBar() {
    toolbar.setTitleTextColor(Color.WHITE)
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(false)
    supportActionBar?.setDisplayShowTitleEnabled(true)
    supportActionBar?.elevation = 7.0f
  }

  private fun toggle() {
    staggeredLayoutManager.spanCount = 2
    if (isListView) {
      showGridView()
    } else {
      showListView()
    }
  }

  private fun showListView() {
    staggeredLayoutManager.spanCount = 1
    val item = menu.findItem(R.id.action_toggle)
    item.setIcon(R.drawable.ic_action_grid)
    item.title = getString(R.string.show_as_grid)
    isListView = true
  }

  private fun showGridView() {
    val item = menu.findItem(R.id.action_toggle)
    item.setIcon(R.drawable.ic_action_list)
    item.title = getString(R.string.show_as_list)
    isListView = false
  }
}
