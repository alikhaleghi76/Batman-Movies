package ali.khaleghi.batman.view.ui.activity

import ali.khaleghi.batman.R
import ali.khaleghi.batman.glide.DrawableAlwaysCrossFadeFactory
import ali.khaleghi.batman.service.model.video_list.VideoListItem
import ali.khaleghi.batman.util.DimenConverter
import ali.khaleghi.batman.util.NetworkState
import ali.khaleghi.batman.util.Toaster
import ali.khaleghi.batman.view.adapter.VideoListAdapter
import ali.khaleghi.batman.view.ui.custom.HeaderFooterAdapter
import ali.khaleghi.batman.view.ui.custom.RecyclerViewUtils
import ali.khaleghi.batman.view.ui.fragment.DetailFragment
import ali.khaleghi.batman.viewmodel.VideoListViewModel
import ali.khaleghi.batman.viewmodel.ViewModelFactory
import android.animation.Animator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.ViewSkeletonScreen
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : BaseActivity() {

    private lateinit var videoListViewModel: VideoListViewModel
    private lateinit var header: RelativeLayout
    private lateinit var headerTitle: TextView
    private lateinit var headerYear: TextView
    private lateinit var homePlaceholder: View

    private var loadingState: Int = 0
    private var isHeaderSet = false

    private lateinit var detailFragment: DetailFragment

    private lateinit var skeleton: ViewSkeletonScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homePlaceholder = findViewById(R.id.homePlaceholder)

        initViews()

        videoListViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(application)
        ).get(VideoListViewModel::class.java)

        getVideoList()

    }

    private fun initViews() {

        skeleton = Skeleton.bind(homePlaceholder)
                .load(R.layout.home_placeholder)
                .shimmer(true)
                .show()

        listRecyclerView.layoutManager = LinearLayoutManager(this)

        listRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    when (loadingState) {
                        0 -> { //not loading
                            progressBar.visibility = View.INVISIBLE
                        }
                        1 -> progressBar.visibility = View.VISIBLE   //loading
                        2 -> { //error
                            progressBar.visibility = View.INVISIBLE
                        }
                    }
                }


                val colorBlack = "#000000"
                val actionBarColor = setColorAlpha(
                    colorBlack,
                    DimenConverter.convertPixelsToDp(
                        recyclerView.computeVerticalScrollOffset().toFloat(),
                        this@MainActivity
                    )
                        .toDouble() / 255
                )
                appBarLayout.setBackgroundColor(Color.parseColor(actionBarColor))
            }
        })

    }

    private fun getVideoList() {
        videoListViewModel.getObservableGroupList()?.observe(this,
            Observer<PagedList<VideoListItem>> { videoListItems ->
                if (videoListItems == null || (videoListItems.size == 0 && listRecyclerView.childCount == 0)) {
                    if (!NetworkState.isOnline(this)) {
                        noNet.visibility = View.VISIBLE
                        return@Observer
                    }
                }
                setupRecycler(videoListItems)

            })

        videoListViewModel.getObservableLoadingState()?.observe(this, Observer<Int> {
            loadingState = it
            when (it) {
                0 -> {
                    skeleton.hide()
                    progressBar.visibility = View.INVISIBLE
                }
                2 -> {
                    skeleton.hide()
                    progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }

    var lastHeaderRow = -1
    private fun setupHeader(videoListItems: PagedList<VideoListItem>?) {
        header.visibility = View.VISIBLE
        if (videoListItems != null) {
            if (videoListItems.size > 0) {
                var row = -1
                while (row == lastHeaderRow || row == -1)
                    row = (0..9).random()
                lastHeaderRow = row

                header.setOnClickListener {
                    if (videoListItems[row]?.imdbID != null)
                        loadDetailFragment(videoListItems[row]?.imdbID!!)
                }

                val headerPoster = header.findViewById<ImageView>(R.id.poster)
                headerTitle = header.findViewById(R.id.poster_title)
                headerYear = header.findViewById(R.id.poster_year)

                if (!isFinishing)
                    Glide
                        .with(this)
                        .load(videoListItems[row]?.poster)
                        .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                        .centerCrop()
                        .placeholder(headerPoster.drawable)
                        .into(headerPoster)

                fadeOutPosterTitle()

                Handler().postDelayed({
                    headerTitle.text = videoListItems[row]?.title
                    headerYear.text = videoListItems[row]?.year
                }, 450)

                Handler().postDelayed({
                    if (isFinishing) return@postDelayed
                    setupHeader(videoListItems)
                }, 10000)
            }
        }
    }

    private fun setupRecycler(videoListItems: PagedList<VideoListItem>) {

        val listAdapter = VideoListAdapter(this@MainActivity)
        header = LayoutInflater.from(this@MainActivity).inflate(R.layout.home_header, null) as RelativeLayout
        header.visibility = View.INVISIBLE

        listAdapter.submitList(videoListItems)
        val headerFooterAdapter = HeaderFooterAdapter(listAdapter)
        listRecyclerView.adapter = headerFooterAdapter
        RecyclerViewUtils.setHeaderView(listRecyclerView, header)

        listAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                if (!isHeaderSet) {
                    skeleton.hide()
                    setupHeader(videoListItems)
                    isHeaderSet = true
                }
            }
        })

    }


    /***
     * To Show DetailFragment
     */
    fun loadDetailFragment(imdbID: String) {
        if (fragmentContainer.visibility == View.VISIBLE) return

        detailFragment = DetailFragment(imdbID)

        fragmentContainer.visibility = View.VISIBLE
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.fade_out)
            .add(
                R.id.fragmentContainer,
                detailFragment,
                "detail"
            )
            .commit()
    }

    /***
     * To Remove DetailFragment
     *
     * @return <tt>true</tt> if the detail fragment was loaded
     *
     */
    fun removeDetailFragment(): Boolean {
        if (fragmentContainer == null) return false
        if (fragmentContainer.visibility == View.GONE) return false
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in, R.anim.exit_to_right)
            .remove(detailFragment)
            .commit()
        Handler().postDelayed({
            if (isFinishing) return@postDelayed
            fragmentContainer.visibility = View.GONE
        }, 500)
        return true
    }



    private fun setColorAlpha(originalColor: String, alpha: Double): String {
        var originalColor = originalColor
        if (originalColor.length == 9) originalColor = "#" + originalColor.substring(3, 9)
        var alphaFixed = (alpha * 255).roundToInt()
        if (alphaFixed > 255) alphaFixed = 255
        var alphaHex = java.lang.Long.toHexString(alphaFixed.toLong())
        if (alphaHex.length == 1) {
            alphaHex = "0$alphaHex"
        }
        originalColor = originalColor.replace("#", "#$alphaHex")


        return originalColor
    }

    private fun fadeOutPosterTitle() {
        headerTitle
            .animate()
            .alpha(0f)
            .setInterpolator(DecelerateInterpolator())
            .duration = 300
        headerTitle
            .animate()
            .translationYBy(0f)
            .translationY(DimenConverter.convertDpToPixel(40f, this@MainActivity))
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    fadeInPosterTitle()
                }

                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            .duration = 300

        headerYear
            .animate()
            .alpha(0f)
            .setInterpolator(DecelerateInterpolator())
            .duration = 300
        headerYear
            .animate()
            .translationYBy(0f)
            .translationY(DimenConverter.convertDpToPixel(40f, this@MainActivity))
            .setInterpolator(DecelerateInterpolator())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            .duration = 300
    }

    private fun fadeInPosterTitle() {
        Handler().postDelayed({
            headerTitle
                .animate()
                .alpha(1f)
                .setInterpolator(AccelerateInterpolator())
                .duration = 300
            headerTitle
                .animate()
                .translationYBy(DimenConverter.convertDpToPixel(40f, this@MainActivity))
                .translationY(0f)
                .setInterpolator(AccelerateInterpolator())
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {}
                })
                .duration = 300

            headerYear
                .animate()
                .alpha(1f)
                .setInterpolator(AccelerateInterpolator())
                .duration = 300
            headerYear
                .animate()
                .translationYBy(DimenConverter.convertDpToPixel(40f, this@MainActivity))
                .translationY(0f)
                .setInterpolator(AccelerateInterpolator())
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationEnd(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {}
                })
                .duration = 300
        }, 300)
    }

    override fun onBackPressed() {
        if (removeDetailFragment()) return
        super.onBackPressed()
    }

}
