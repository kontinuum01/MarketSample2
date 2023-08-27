package ru.gb.android.lesson2.mvx.features.product.presentation.mvp4

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import ru.gb.android.lesson2.marketsample.R
import ru.gb.android.lesson2.mvx.features.product.presentation.ProductVO

interface ProductView {
    fun onViewAttached(presenter: ProductPresenter)

    fun showProgress()
    fun hideProgress()

    fun showProduct(product: ProductVO)
    fun hideProduct()

    fun showError()
}

class ProductViewImpl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
): ConstraintLayout(context, attrs), ProductView {

    init {
        inflate(context, R.layout.product_view, this@ProductViewImpl)
    }

    private lateinit var presenter: ProductPresenter

    private val progress: View by lazy { findViewById(R.id.progress) }
    private val image: ImageView by lazy { findViewById(R.id.image) }
    private val name: TextView by lazy { findViewById(R.id.name) }
    private val price: TextView by lazy { findViewById(R.id.price) }
    private val promo: TextView by lazy { findViewById(R.id.promo) }
    private val addToCart: View by lazy { findViewById(R.id.addToCart) }

    override fun onViewAttached(presenter: ProductPresenter) {
        this.presenter = presenter
        presenter.onViewAttached(this)

        addToCart.setOnClickListener {
            presenter.addToCart()
        }

        presenter.loadProduct()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showProduct(product: ProductVO) {
        image.load(product.image)
        image.visibility = View.VISIBLE

        name.text = product.name
        name.visibility = View.VISIBLE

        price.text = "${product.price} руб"
        price.visibility = View.VISIBLE

        if (product.hasDiscount) {
            promo.visibility = View.VISIBLE
            promo.text = "${product.discount} %"
        } else {
            promo.visibility = View.GONE
        }

        addToCart.visibility = View.VISIBLE
    }

    override fun hideProduct() {
        image.visibility = View.GONE
        name.visibility = View.GONE
        price.visibility = View.GONE
        promo.visibility = View.GONE
        addToCart.visibility = View.GONE
    }

    override fun showError() {
        Toast.makeText(
            rootView.context,
            "Error wile loading data",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        presenter.dispose()
    }
}
