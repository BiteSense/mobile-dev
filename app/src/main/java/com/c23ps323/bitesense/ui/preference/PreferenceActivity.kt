package com.c23ps323.bitesense.ui.preference

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.c23ps323.bitesense.MainActivity
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.data.Result
import com.c23ps323.bitesense.data.remote.response.HealthConditionResponse
import com.c23ps323.bitesense.data.remote.response.UploadProductResponse
import com.c23ps323.bitesense.databinding.ActivityPreferenceBinding
import com.c23ps323.bitesense.entities.ConditionItem
import com.c23ps323.bitesense.entities.FoodItem
import com.c23ps323.bitesense.entities.HealthCondition
import com.c23ps323.bitesense.entities.PenyakitItem
import com.c23ps323.bitesense.utils.ViewModelFactory
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.launch


class PreferenceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferenceBinding

    private val preferenceViewModel: PreferenceViewModel by viewModels {
        ViewModelFactory(this)
    }
    private val dataKondisi = mutableListOf<String>()
    private val dataPenyakit = mutableListOf<String>()
    private val dataFood = mutableListOf<String>()
    private var json: JsonElement? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferenceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupCheckboxListener()

        getUserHealthCondition()

        binding.btnSubmit.setOnClickListener {
            uploadProcess()
            preferenceViewModel.updatePreference(json!!).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> showLoading(true)
                        is Result.Success -> successUpdate(result.data)
                        is Result.Error -> showError(getString(R.string.general_error))
                    }
                }
            }
        }
    }

    private fun showError(msg: String) {
        showLoading(false)
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun successUpdate(result: UploadProductResponse) {
        showLoading(false)
        Toast.makeText(
            this,
            result.message,
            Toast.LENGTH_SHORT
        ).show()
        finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun getUserHealthCondition() {
        preferenceViewModel.getUserHealthCondition.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        insertDataConditionToList(result.data)
                        setupCheckbox()
                    }

                    is Result.Error -> showError(result.error)
                }
            }
        }
    }

    private fun uploadProcess() {
        val listDataKondisiInt = mutableListOf<ConditionItem>()
        val listDataFoodInt = mutableListOf<FoodItem>()
        val listDataPenyakitInt = mutableListOf<PenyakitItem>()

        if (dataKondisi.isNotEmpty()) {
            for (i in 0 until dataKondisi.size) {
                when (dataKondisi[i]) {
                    getString(R.string.diabetes) -> listDataKondisiInt.add(ConditionItem(1))
                    getString(R.string.hypertension) -> listDataKondisiInt.add(ConditionItem(2))
                    getString(R.string.obesity) -> listDataKondisiInt.add(ConditionItem(3))
                    getString(R.string.pregnant) -> listDataKondisiInt.add(ConditionItem(4))
                    getString(R.string.gerd_maag) -> listDataKondisiInt.add(ConditionItem(5))
                    getString(R.string.stroke) -> listDataKondisiInt.add(ConditionItem(6))
                    getString(R.string.uric_acid) -> listDataKondisiInt.add(ConditionItem(7))
                }
            }
        }

        if (dataFood.isNotEmpty()) {
            for (i in 0 until dataFood.size) {
                when (dataFood[i]) {
                    getString(R.string.dairy) -> listDataFoodInt.add(FoodItem(1))
                    getString(R.string.gluten) -> listDataFoodInt.add(FoodItem(2))
                    getString(R.string.fruktosa) -> listDataFoodInt.add(FoodItem(4))
                    getString(R.string.caffeine) -> listDataFoodInt.add(FoodItem(3))
                    getString(R.string.grains) -> listDataFoodInt.add(FoodItem(5))
                    getString(R.string.msg) -> listDataFoodInt.add(FoodItem(6))
                    getString(R.string.salicylates) -> listDataFoodInt.add(FoodItem(7))
                    getString(R.string.ragi) -> listDataFoodInt.add(FoodItem(8))
                    getString(R.string.food_coloring) -> listDataFoodInt.add(FoodItem(9))
                }
            }
        }

        if (dataPenyakit.isNotEmpty()) {
            for (i in 0 until dataPenyakit.size) {
                when (dataPenyakit[i]) {
                    getString(R.string.peanut) -> listDataPenyakitInt.add(PenyakitItem(1))
                    getString(R.string.egg) -> listDataPenyakitInt.add(PenyakitItem(2))
                    getString(R.string.soy) -> listDataPenyakitInt.add(PenyakitItem(3))
                    getString(R.string.wheat) -> listDataPenyakitInt.add(PenyakitItem(4))
                    getString(R.string.fish) -> listDataPenyakitInt.add(PenyakitItem(5))
                    getString(R.string.milk) -> listDataPenyakitInt.add(PenyakitItem(6))
                    getString(R.string.shellfish) -> listDataPenyakitInt.add(PenyakitItem(7))
                    getString(R.string.tree_nut) -> listDataPenyakitInt.add(PenyakitItem(8))
                }
            }
        }

        // Save the result to Health Condition object then convert to json
        val healthConditionData =
            HealthCondition(listDataPenyakitInt, listDataKondisiInt, listDataFoodInt)
        json = Gson().toJsonTree(healthConditionData)
    }

    private fun setupCheckboxListener() {
        binding.apply {
            // Data Penyakit Section
            cbPeanut.setOnClickListener {
                addToDataPenyakit(cbPeanut)
            }
            cbEgg.setOnClickListener {
                addToDataPenyakit(cbEgg)
            }
            cbSoy.setOnClickListener {
                addToDataPenyakit(cbSoy)
            }
            cbMilk.setOnClickListener {
                addToDataPenyakit(cbMilk)
            }
            cbFish.setOnClickListener {
                addToDataPenyakit(cbFish)
            }
            cbWheat.setOnClickListener {
                addToDataPenyakit(cbWheat)
            }
            cbShellfish.setOnClickListener {
                addToDataPenyakit(cbShellfish)
            }
            cbTreeNut.setOnClickListener {
                addToDataPenyakit(cbTreeNut)
            }

            // Data Food Section
            cbDairy.setOnClickListener {
                addToDataFood(cbDairy)
            }
            cbGluten.setOnClickListener {
                addToDataFood(cbGluten)
            }
            cbCaffeine.setOnClickListener {
                addToDataFood(cbCaffeine)
            }
            cbMsg.setOnClickListener {
                addToDataFood(cbMsg)
            }
            cbRagi.setOnClickListener {
                addToDataFood(cbRagi)
            }
            cbFruktosa.setOnClickListener {
                addToDataFood(cbFruktosa)
            }
            cbBiji.setOnClickListener {
                addToDataFood(cbBiji)
            }
            cbSalicylates.setOnClickListener {
                addToDataFood(cbSalicylates)
            }
            cbFoodColor.setOnClickListener {
                addToDataFood(cbFoodColor)
            }

            // Data Kondisi Section
            cbDiabetes.setOnClickListener {
                addToDataKondisi(cbDiabetes)
            }
            cbHypertension.setOnClickListener {
                addToDataKondisi(cbHypertension)
            }
            cbPregnant.setOnClickListener {
                addToDataKondisi(cbPregnant)
            }
            cbObesity.setOnClickListener {
                addToDataKondisi(cbObesity)
            }
            cbGerd.setOnClickListener {
                addToDataKondisi(cbGerd)
            }
            cbUricAcid.setOnClickListener {
                addToDataKondisi(cbUricAcid)
            }
            cbStroke.setOnClickListener {
                addToDataKondisi(cbStroke)
            }
        }
    }

    private fun addToDataKondisi(checkBox: CheckBox) {
        if (checkBox.isChecked) {
            if (!dataKondisi.contains(checkBox.text)) {
                dataKondisi.add(checkBox.text.toString())
            }
        } else {
            dataKondisi.remove(checkBox.text.toString())
        }
    }

    private fun addToDataFood(checkBox: CheckBox) {
        if (checkBox.isChecked) {
            if (!dataFood.contains(checkBox.text)) {
                dataFood.add(checkBox.text.toString())
            }
        } else {
            dataFood.remove(checkBox.text.toString())
        }
    }

    private fun addToDataPenyakit(checkBox: CheckBox) {
        if (checkBox.isChecked) {
            if (!dataPenyakit.contains(checkBox.text)) {
                dataPenyakit.add(checkBox.text.toString())
            }
        } else {
            dataPenyakit.remove(checkBox.text.toString())
        }
    }

    private fun setupCheckbox() {
        lifecycleScope.launch {
            if (dataKondisi.isNotEmpty()) {
                for (i in 0 until dataKondisi.size) {
                    when {
                        binding.cbHypertension.text == dataKondisi[i] -> binding.cbHypertension.isChecked =
                            true

                        binding.cbDiabetes.text == dataKondisi[i] -> binding.cbDiabetes.isChecked =
                            true

                        binding.cbPregnant.text == dataKondisi[i] -> binding.cbPregnant.isChecked =
                            true

                        binding.cbObesity.text == dataKondisi[i] -> binding.cbObesity.isChecked =
                            true

                        binding.cbGerd.text == dataKondisi[i] -> binding.cbGerd.isChecked = true
                        binding.cbUricAcid.text == dataKondisi[i] -> binding.cbUricAcid.isChecked =
                            true

                        binding.cbStroke.text == dataKondisi[i] -> binding.cbStroke.isChecked = true
                    }
                }
            }
            if (dataPenyakit.isNotEmpty()) {
                for (i in 0 until dataPenyakit.size) {
                    when {
                        binding.cbPeanut.text == dataPenyakit[i] -> binding.cbPeanut.isChecked =
                            true

                        binding.cbEgg.text == dataPenyakit[i] -> binding.cbEgg.isChecked = true
                        binding.cbSoy.text == dataPenyakit[i] -> binding.cbSoy.isChecked = true
                        binding.cbMilk.text == dataPenyakit[i] -> binding.cbMilk.isChecked = true
                        binding.cbWheat.text == dataPenyakit[i] -> binding.cbWheat.isChecked = true
                        binding.cbShellfish.text == dataPenyakit[i] -> binding.cbShellfish.isChecked =
                            true

                        binding.cbTreeNut.text == dataPenyakit[i] -> binding.cbTreeNut.isChecked =
                            true
                    }
                }
            }
            if (dataFood.isNotEmpty()) {
                for (i in 0 until dataFood.size) {
                    when {
                        binding.cbDairy.text == dataFood[i] -> binding.cbDairy.isChecked = true
                        binding.cbGluten.text == dataFood[i] -> binding.cbGluten.isChecked = true
                        binding.cbCaffeine.text == dataFood[i] -> binding.cbCaffeine.isChecked =
                            true

                        binding.cbMsg.text == dataFood[i] -> binding.cbMsg.isChecked = true
                        binding.cbRagi.text == dataFood[i] -> binding.cbRagi.isChecked = true
                        binding.cbFruktosa.text == dataFood[i] -> binding.cbFruktosa.isChecked =
                            true

                        binding.cbBiji.text == dataFood[i] -> binding.cbBiji.isChecked = true
                        binding.cbSalicylates.text == dataFood[i] -> binding.cbSalicylates.isChecked =
                            true

                        binding.cbFoodColor.text == dataFood[i] -> binding.cbFoodColor.isChecked =
                            true
                    }
                }
            }
        }
    }

    private fun insertDataConditionToList(result: HealthConditionResponse) {
        val responsePenyakit = result.data?.dataPenyakit
        val responseKondisi = result.data?.dataKondisi
        val responseFood = result.data?.dataFood
        lifecycleScope.launch {
            if (responseFood!!.isNotEmpty()) {
                for (i in 0 until result.data.dataFood.size) {
                    dataFood.add(result.data.dataFood[i]?.nameFood!!)
                }
            }
            if (responsePenyakit!!.isNotEmpty()) {
                for (i in 0 until result.data.dataPenyakit.size) {
                    dataPenyakit.add(result.data.dataPenyakit[i]?.namaPenyakit!!)
                }
            }
            if (responseKondisi!!.isNotEmpty()) {
                for (i in 0 until result.data.dataKondisi.size) {
                    dataKondisi.add(result.data.dataKondisi[i]?.nameCondition!!)
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.btnSubmit.alpha = 0.6f
            binding.btnSubmit.isEnabled = false
        } else {
            binding.btnSubmit.alpha = 1f
            binding.btnSubmit.isEnabled = true
        }
    }
}