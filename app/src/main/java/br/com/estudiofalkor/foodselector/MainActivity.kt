package br.com.estudiofalkor.foodselector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.estudiofalkor.foodselector.extension.setupViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = setupViewModel()
    }
}
