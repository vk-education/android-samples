package ru.hse.lection10

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.recyclerview.widget.DiffUtil
import androidx.room.*
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.hse.lection10.databinding.FragmentDashboardBinding
import ru.hse.lection10.databinding.FragmentItemBinding
import ru.hse.lection10.placeholder.PlaceholderContent
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ItemFragment @Inject constructor() : Fragment() {
    private var _navArgs: ItemFragmentArgs? = null
    private val navArgs get() = _navArgs!!

    @Inject
    lateinit var dummyProvier: DummyProvider
    val glide by lazy { Glide.with(this@ItemFragment) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _navArgs = ItemFragmentArgs.fromBundle(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the adapter
        val dummyAdapter = DummyAdapter(glide)
        val recycler = view as RecyclerView
        recycler.apply {
            adapter = dummyAdapter

            layoutManager = when {
                navArgs.columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, navArgs.columnCount)
            }
        }

        val config = PagingConfig(20)
        val dummies = Pager(config) { dummyProvier }.flow

        viewLifecycleOwner.lifecycleScope.launch {
            dummies.collectLatest {
                dummyAdapter.submitData(it)
            }
        }
    }


    @Entity
    data class Dummy(@PrimaryKey val id: Int, val title: String)

    class DummyHolder(val requestManager: RequestManager, val binding: FragmentItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.image.setRenderEffect(RENDER_EFFECT_FILTER)
        }

        fun bind(item: Dummy?) {
            item?.let {
                binding.itemNumber.text = it.id.toString()
                binding.content.text = it.title

                requestManager
                    .load("https://loremflickr.com/240/240?lock=${it.id}")
                    .into(binding.image)
            }
        }

        companion object {
            val COLOR_MATRIX = ColorMatrix().apply {
                setSaturation(0f)

            }
            val RENDER_EFFECT_FILTER = RenderEffect.createColorFilterEffect(ColorMatrixColorFilter(COLOR_MATRIX))

            val RENDER_EFFECT_BLUR = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
        }
    }

    class DummyCalculator: DiffUtil.ItemCallback<Dummy>() {
        override fun areItemsTheSame(oldItem: Dummy, newItem: Dummy): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Dummy, newItem: Dummy): Boolean {
            return oldItem == newItem
        }
    }

    class DummyAdapter(val requestManager: RequestManager): PagingDataAdapter<Dummy, DummyHolder>(DummyCalculator()) {
        override fun onBindViewHolder(holder: DummyHolder, position: Int) {
            val item = getItem(position)
            holder.bind(item)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyHolder {
            val binding = FragmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return DummyHolder(requestManager, binding)
        }
    }

    class DummyProvider(val cache: IDummyOfflineAccessor): PagingSource<Int, Dummy>() {
        override fun getRefreshKey(state: PagingState<Int, Dummy>): Int? {
            return state.anchorPosition
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Dummy> {
            val offset = params.key ?: 0
            val limit = params.loadSize

            var result = getFromCache(offset, limit)
            if (result.isNullOrEmpty()) {
                result = load(offset, limit)
                saveToCache(result)
            }

            return LoadResult.Page(result, null, offset + limit)
        }

        fun load(offset: Int, limit: Int): List<Dummy> {
            val maximum = offset + limit -1

            return (offset .. maximum)
                .map { Dummy(it, "Element number: $it") }
        }

        protected suspend fun getFromCache(offset: Int, limit: Int): List<Dummy>? {
            val ids = IntArray(limit) { offset + it }
            return cache.load(ids)?.map {
                Dummy(it.id, it.title + " (cached)")
            }
        }

        protected suspend fun saveToCache(result: List<Dummy>) {
            cache.insertAll(result)
        }
    }



    @Dao
    interface IDummyOfflineAccessor {
        @Query("SELECT * FROM dummy")
        suspend fun getAll(): List<Dummy>?

        @Query("SELECT * FROM dummy WHERE id IN (:ids)")
        suspend fun load(ids: IntArray): List<Dummy>?

        @Insert
        suspend fun insertAll(list: List<Dummy>)
        @Delete
        suspend fun delete(item: Dummy)
    }

    @Database(entities = arrayOf(Dummy::class), version = 1, exportSchema = false)
    abstract class AbstractDatabase: RoomDatabase() {
        abstract fun dummyOfflineAccessor(): IDummyOfflineAccessor
    }
}