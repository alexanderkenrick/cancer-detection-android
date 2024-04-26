package com.dicoding.asclepius.view

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.WorkerThread
import androidx.lifecycle.lifecycleScope
import com.dicoding.asclepius.database.History
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.viewModel.HistoryViewModel
import com.dicoding.asclepius.viewModel.ResultViewModel
import com.dicoding.asclepius.viewModel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val resultViewModel by viewModels<ResultViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val uuid = intent.getStringExtra(EXTRA_UUID) ?: ""
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        val detectedText = intent.getStringExtra(EXTRA_RESULT)
        val detectedScore = intent.getStringExtra(EXTRA_SCORE)

        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            with(binding.resultImage) {
                setImageURI(it)
                contentDescription = detectedText
            }
            binding.resultImage.setImageURI(it)
        }
        binding.resultText.text = "$detectedText $detectedScore"

        binding.btnSave.setOnClickListener() {
            lifecycleScope.launch(Dispatchers.Default) {
                val currentTime = System.currentTimeMillis()
                val history: History =
                    History(uuid, imageUri.toString(), detectedText, detectedScore, currentTime)
                resultViewModel.addHistory(history)
                workerThread("Added to History", binding)
            }
        }
    }

    @WorkerThread
    fun workerThread(message: String, binding: ActivityResultBinding) {
        this.runOnUiThread {
            Toast.makeText(this@ResultActivity, message, Toast.LENGTH_SHORT).show()
            binding.btnSave.isEnabled = false
        }
    }

    companion object {
        const val EXTRA_UUID = "extra_uuid"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_SCORE = "extra_score"
    }
}