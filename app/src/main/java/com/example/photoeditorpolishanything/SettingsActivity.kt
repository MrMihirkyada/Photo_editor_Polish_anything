
package com.example.photoeditorpolishanything

import LocaleHelper
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.photoeditorpolishanything.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding
    private lateinit var showLanDialog: RelativeLayout
    private var langSelected: Int = 0
    private lateinit var context: Context
    private lateinit var resources: Resources
    private lateinit var dialogLanguage: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale() // Load the saved language preference
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LocaleHelper.onAttach(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.lightBlack)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.lightBlack)
        }

        intiview()
    }

    private fun intiview() {
        binding.imgPro.setOnClickListener {
            val i = Intent(this@SettingsActivity, Polish_Pro_PaymentActivity::class.java)
            startActivity(i)
        }

        binding.imgback.setOnClickListener {
            onBackPressed()
        }

        binding.lnrFeedback.setOnClickListener {
            showFeedbackDialog()
        }

        binding.lnrResolution.setOnClickListener {
            showResolutionSelectionDialog()
        }

        binding.lnrLanguage.setOnClickListener {
            showLanguageSelectDialog()
        }

        binding.lnrManageSubscription.setOnClickListener {
            val i = Intent(this@SettingsActivity, SubscriptionsActivity::class.java)
            startActivity(i)
        }
    }

    private fun showResolutionSelectionDialog() {
        val dialogView = layoutInflater.inflate(R.layout.resolution_dialog, null)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroup)

        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val savedResolution = sharedPreferences.getString("SelectedResolution", "DefaultResolution")

        when (savedResolution) {
            "Resolution1" -> radioGroup.check(R.id.radio_high_resolution)
            "Resolution2" -> radioGroup.check(R.id.radio_regular_resolution)
            else -> radioGroup.clearCheck()
        }

        for (i in 0 until radioGroup.childCount) {
            val childView = radioGroup.getChildAt(i)
            if (childView is RadioButton) {
                val radioButton = childView
                val colorStateList = if (radioButton.isChecked) {
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
                } else {
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                }
                radioButton.buttonTintList = colorStateList
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until group.childCount) {
                val childView = group.getChildAt(i)
                if (childView is RadioButton) {
                    val radioButton = childView
                    val colorStateList = if (radioButton.id == checkedId) {
                        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
                    } else {
                        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                    }
                    radioButton.buttonTintList = colorStateList
                }
            }

            val selectedRadioButton = dialogView.findViewById<RadioButton>(checkedId)
            val selectedResolution = when (selectedRadioButton.id) {
                R.id.radio_high_resolution -> "Resolution1"
                R.id.radio_regular_resolution -> "Resolution2"
                else -> "DefaultResolution"
            }

            val editor = sharedPreferences.edit()
            editor.putString("SelectedResolution", selectedResolution)
            editor.apply()

            dialog.dismiss()
        }

        dialog.show()
    }

    @SuppressLint("MissingInflatedId")
    private fun showLanguageSelectDialog() {
        val dialogView = layoutInflater.inflate(R.layout.select_language, null)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.selectLanguageRadioGroup)

        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("SelectedLanguage", "en")

        when (savedLanguage)
        {
            "en" -> radioGroup.check(R.id.rbEnglish)
            "da" -> radioGroup.check(R.id.rbDansk)
            "de" -> radioGroup.check(R.id.rbDeutsch)
            "es" -> radioGroup.check(R.id.rbEspañol)
            "fr" -> radioGroup.check(R.id.rbFrançais)
            "hi" -> radioGroup.check(R.id.rbHindi)
            "it" -> radioGroup.check(R.id.rbItaliano)
            "ja" -> radioGroup.check(R.id.rbJapanese)
            "ko" -> radioGroup.check(R.id.rbKorean)
            "ms" -> radioGroup.check(R.id.rbBahasaMelayu)
            else -> radioGroup.clearCheck()
        }

        for (i in 0 until radioGroup.childCount)
        {
            val childView = radioGroup.getChildAt(i)
            if (childView is RadioButton)
            {
                val radioButton = childView
                val colorStateList = if (radioButton.isChecked)
                {
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
                }
                else
                {
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                }
                radioButton.buttonTintList = colorStateList
            }
        }

        dialogLanguage = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogLanguage.window?.setBackgroundDrawableResource(android.R.color.transparent)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            for (i in 0 until group.childCount)
            {
                val childView = group.getChildAt(i)
                if (childView is RadioButton)
                {
                    val radioButton = childView
                    val colorStateList = if (radioButton.id == checkedId)
                    {
                        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue))
                    }
                    else
                    {
                        ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                    }
                    radioButton.buttonTintList = colorStateList
                }
            }

            val selectedRadioButton = dialogView.findViewById<RadioButton>(checkedId)
            val selectedLanguage = when (selectedRadioButton.id)
            {
                R.id.rbEnglish -> "en"
                R.id.rbDansk -> "da"
                R.id.rbDeutsch -> "de"
                R.id.rbEspañol -> "es"
                R.id.rbFrançais -> "fr"
                R.id.rbHindi -> "hi"
                R.id.rbItaliano -> "it"
                R.id.rbJapanese -> "ja"
                R.id.rbKorean -> "ko"
                R.id.rbBahasaMelayu -> "ms"
                else -> "en"
            }

            val editor = sharedPreferences.edit()
            editor.putString("SelectedLanguage", selectedLanguage)
            editor.apply()

            LocaleHelper.setLocale(this, selectedLanguage)

            updateUIForSelectedLanguage()

            dialogLanguage.dismiss()
            recreate()
        }

        dialogLanguage.show()
    }

    private fun loadLocale()
    {
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("SelectedLanguage", "en")
        LocaleHelper.setLocale(this, savedLanguage!!)
    }

    open fun updateUIForSelectedLanguage()
    {
        context = LocaleHelper.onAttach(this)
        resources = context.resources

        binding.apply {
            binding.txtSettings.text = resources.getString(R.string.Setting)
            binding.txtAppName.text = resources.getString(R.string.app_name)
            binding.txtGeneral.text = resources.getString(R.string.General)
            binding.txtResoluction.text = resources.getString(R.string.Resolution)
            binding.txtLanguage.text = resources.getString(R.string.Language)
            binding.txtFeedback.text = resources.getString(R.string.Feedback)
            binding.txtSavePath.text = resources.getString(R.string.SavePath)
            binding.txtShare.text = resources.getString(R.string.Share)
            binding.txtRemoveAds.text = resources.getString(R.string.RemoveAds)
            binding.txtRestorePurchase.text = resources.getString(R.string.RestorePurchase)
            binding.txtManageSubscription.text = resources.getString(R.string.ManageSubscription)
            binding.txtFollowUs.text = resources.getString(R.string.FollowUs)
            binding.txtInstagram.text = resources.getString(R.string.Instagram)
            binding.txtAbout.text = resources.getString(R.string.About)
            binding.txtTermsofUse.text = resources.getString(R.string.TermsofUse)
            binding.txtPrivacyPolicy.text = resources.getString(R.string.PrivacyPolicy)
            binding.txtGridArt221151.text = resources.getString(R.string.GridArt221151)

            val dialogView = layoutInflater.inflate(R.layout.feedfack_layout, null)

            val textFeedback = dialogView.findViewById<TextView>(R.id.txtFeedbacks)
            val textNoThanks = dialogView.findViewById<TextView>(R.id.txtNoThanks)
            val textSendFeedback = dialogView.findViewById<TextView>(R.id.txtFeedback)

            textFeedback.text = resources.getString(R.string.FeedbackCaption)
            textNoThanks.text = resources.getString(R.string.Nothanks)
            textSendFeedback.text = resources.getString(R.string.NotReally)

        }
    }

    private fun showFeedbackDialog() {
        // Ensure this method is called on the UI thread
        runOnUiThread {
            // Check if the activity is still valid
            if (isFinishing || isDestroyed) {
                Log.e("ShowFeedbackDialog", "Activity is not in a valid state")
                return@runOnUiThread
            }

            // Use this as the context for the dialog
            val context = this // Ensure `this` is a valid Activity context

            if (context == null) {
                Log.e("ShowFeedbackDialog", "Context is null")
                return@runOnUiThread
            }

            // Create and show the dialog
            val dialog = Dialog(context)
            val dialogView = layoutInflater.inflate(R.layout.feedfack_layout, null)

            dialog.setContentView(dialogView)

            // Use the current resources to get localized strings
            val textFeedback = dialogView.findViewById<TextView>(R.id.txtFeedbacks)
            val textNoThanks = dialogView.findViewById<TextView>(R.id.txtNoThanks)
            val textSendFeedback = dialogView.findViewById<TextView>(R.id.txtFeedback)

            // Update the texts with the current locale
            textFeedback.text = resources.getString(R.string.FeedbackCaption)
            textNoThanks.text = resources.getString(R.string.Nothanks)
            textSendFeedback.text = resources.getString(R.string.NotReally)

            textNoThanks.setOnClickListener {
                dialog.dismiss()
            }

            textSendFeedback.setOnClickListener {
                // Implement feedback sending logic here
                dialog.dismiss()
            }

            dialog.show()
        }
    }



//    private fun showFeedbackDialog() {
//        // Ensure this method is called on the UI thread
//        runOnUiThread {
//            // Check if the activity is still valid
//            if (isFinishing || isDestroyed) {
//                Log.e("ShowFeedbackDialog", "Activity is not in a valid state")
//                return@runOnUiThread
//            }
//
//            // Use this as the context for the dialog
//            val context = this // Ensure `this` is a valid Activity context
//
//            if (context == null) {
//                Log.e("ShowFeedbackDialog", "Context is null")
//                return@runOnUiThread
//            }
//
//            // Create and show the dialog
//            val dialog = Dialog(context)
//            val dialogView = layoutInflater.inflate(R.layout.feedfack_layout, null)
//
//            dialog.setContentView(dialogView)
//
//            val textFeedback = dialogView.findViewById<TextView>(R.id.txtFeedbacks)
//            val textNoThanks = dialogView.findViewById<TextView>(R.id.txtNoThanks)
//            val textSendFeedback = dialogView.findViewById<TextView>(R.id.txtFeedback)
//
//            textFeedback.text = context.getString(R.string.FeedbackCaption)
//            textNoThanks.text = context.getString(R.string.Nothanks)
//            textSendFeedback.text = context.getString(R.string.NotReally)
//
//            textNoThanks.setOnClickListener {
//                dialog.dismiss()
//            }
//
//            textSendFeedback.setOnClickListener {
//                // Implement feedback sending logic here
//                dialog.dismiss()
//            }
//
//            dialog.show()
//        }
//    }



    override fun onResume()
    {
        super.onResume()
        updateUIForSelectedLanguage()
    }
}
