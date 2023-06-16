package com.c23ps323.bitesense.ui.generateqr

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.ActivityInputGenerateQractivityBinding
import com.c23ps323.bitesense.ui.generateqr.GenerateQRActivity.Companion.EXTRA_DATA
import com.c23ps323.bitesense.utils.ViewModelFactory
import com.c23ps323.bitesense.utils.animateVisibility
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.Calendar

class InputGenerateQRActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputGenerateQractivityBinding

    private var generateQrJob: Job = Job()
    private val inputGenerateQrViewModel: InputGenerateQrViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputGenerateQractivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setActions()

    }

    @Suppress("DEPRECATION")
    private fun setActions() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivDate.setOnClickListener {
            clickDatePicker()
        }
        binding.ivDate2.setOnClickListener {
            clickDatePicker2()
        }
        binding.btnGenerateQr.setOnClickListener {
            handleGenerateQR()
        }
    }

    private fun handleGenerateQR() {
        val name = binding.edtNameProduct.text.toString().trim()
        val komposisi_ = binding.edtNameProduct.text.toString().trim()
        val expired = binding.tvDate.text.toString().trim()
        val tgl_produksi = binding.tvDate2.text.toString().trim()

        lifecycleScope.launchWhenResumed {
            if (generateQrJob.isActive) generateQrJob.cancel()

            generateQrJob = launch {
                inputGenerateQrViewModel.createQrCode(name, komposisi_, expired, tgl_produksi)
                    .collect { result ->
                        result.onSuccess { data ->
                            Toast.makeText(
                                this@InputGenerateQRActivity,
                                getString(R.string.generate_succes),
                                Toast.LENGTH_SHORT
                            ).show()
                            data.data?.let { data ->
                                Intent(
                                    this@InputGenerateQRActivity,
                                    GenerateQRActivity::class.java
                                ).also { intent ->
                                    intent.putExtra(EXTRA_DATA, data)
                                    startActivity(intent)
                                    finish()
                                }
                            }

                        }
                        result.onFailure {
                            Snackbar.make(
                                binding.root,
                                getString(R.string.generate_error_message),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                    }
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        binding.apply {
            edtNameProduct.isEnabled = !isLoading
            edtKomposisi.isEnabled = !isLoading
            tvDate.isEnabled = !isLoading
            tvDate2.isEnabled = !isLoading

            if (isLoading) {
                viewLoading.animateVisibility(true)
            } else {
                viewLoading.animateVisibility(false)
            }
        }
    }

    private fun clickDatePicker2() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this, { _, selectedYear,
                    selectedMonth,
                    selectedDayOfMonth ->

                val selectedDate = "${selectedYear}-${selectedMonth + 1}-$selectedDayOfMonth"
                binding.tvDate2.text = selectedDate
            },
            year,
            month,
            day
        )
        dpd.show()
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener { _, selectedYear,
                                                       selectedMonth,
                                                       selectedDayOfMonth ->

                val selectedDate = "${selectedYear}-${selectedMonth + 1}-$selectedDayOfMonth"
                binding.tvDate.text = selectedDate

            },
            year,
            month,
            day
        )
        dpd.show()
    }
}