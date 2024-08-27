package com.example.satu.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.satu.R
import com.example.satu.databinding.ActivityMainBinding
import com.example.satu.ui.activities.qris.QrisActivity
import com.example.satu.ui.fragment.home.HomeFragment
import com.example.satu.ui.fragment.notification.NotifikasiFragment
import com.example.satu.ui.fragment.profile.ProfileFragment
import com.example.satu.ui.fragment.tabungan.TabunganFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.beranda -> loadFragment(HomeFragment())
                R.id.tabungan -> loadFragment(TabunganFragment())
                R.id.notifikasi -> loadFragment(NotifikasiFragment())
                R.id.qris -> {
                    val intent = Intent(this, QrisActivity::class.java)
                    startActivity(intent)
                }
                R.id.akun -> loadFragment(ProfileFragment())
            }
            true
        }

        val beranda = binding.bottomNavigation.menu.findItem(R.id.beranda)
        val tabungan = binding.bottomNavigation.menu.findItem(R.id.tabungan)
        val qris = binding.bottomNavigation.menu.findItem(R.id.qris)
        val notifikasi = binding.bottomNavigation.menu.findItem(R.id.notifikasi)
        val akun = binding.bottomNavigation.menu.findItem(R.id.akun)

        beranda.contentDescription = getString(R.string.beranda_description)
        tabungan.contentDescription = getString(R.string.tabungan_description)
        qris.contentDescription = getString(R.string.qris_description)
        notifikasi.contentDescription = getString(R.string.notifikasi_description)
        akun.contentDescription = getString(R.string.akun_description)
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}