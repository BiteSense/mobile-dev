package com.c23ps323.bitesense.ui.camera

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.c23ps323.bitesense.R
import com.c23ps323.bitesense.databinding.FragmentCameraBinding
import com.c23ps323.bitesense.ui.preview.PreviewFragment
import com.c23ps323.bitesense.utils.createFile

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null
    private var isFlashOn: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButton()
    }

    private fun setupButton() {
        binding.apply {
            captureImage.setOnClickListener {
                takePhoto()
            }
            switchCamera.setOnClickListener {
                cameraSelector =
                    if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
            }
            btnFlash.setOnClickListener {
                isFlashOn = !isFlashOn
                startCamera()
            }
            gallery.setOnClickListener {
                startGallery()
            }
            btnClose.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, TITLE)
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            val bundle = Bundle()
            val previewFragment = PreviewFragment()

            bundle.putParcelable(PreviewFragment.EXTRA_URI, selectedImg)
            previewFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_camera, previewFragment, PreviewFragment::class.java.simpleName)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createFile(requireActivity().application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val intent = Intent()
                    intent.putExtra(PICTURE, photoFile)
                    intent.putExtra(
                        ISBACKCAMERA,
                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
                    )
                    activity!!.setResult(200, intent)

                    val bundle = Bundle()
                    val previewFragment = PreviewFragment()
                    bundle.putSerializable(PreviewFragment.EXTRA_PICTURE, photoFile)
                    previewFragment.arguments = bundle

                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame_camera,
                            previewFragment,
                            PreviewFragment::class.java.simpleName
                        )
                        .addToBackStack(null)
                        .commit()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.take_picture_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                val camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                val cameraControl = camera.cameraControl
                cameraControl.enableTorch(isFlashOn)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.failed_find_camera),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    @Suppress("DEPRECATION")
    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TITLE = "Choose a Picture"
        private const val PICTURE = "picture"
        private const val ISBACKCAMERA = "isBackCamera"
    }
}