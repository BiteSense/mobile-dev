package com.c23ps323.bitesense.ui.preview

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.FragmentPreviewBinding
import com.c23ps323.bitesense.ui.scannedProduct.ScannedProductFragment
import java.io.File

class PreviewFragment : Fragment() {
    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        if(arguments != null) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable(EXTRA_PICTURE, File::class.java)
            } else {
                arguments?.getSerializable(EXTRA_PICTURE)
            } as? File
            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable(EXTRA_URI, Uri::class.java)
            } else {
                arguments?.getParcelable(EXTRA_URI)
            }

            if(uri != null) {
                binding.ivPreview.setImageURI(uri)
            }

            myFile?.let { file ->
                binding.ivPreview.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }

        binding.btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnAnalyze.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_camera, ScannedProductFragment(), ScannedProductFragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_URI = "extra_uri"
        const val EXTRA_PICTURE = "extra_picture"
    }
}