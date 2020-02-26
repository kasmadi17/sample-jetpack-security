package com.cx.jetpacksecurity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        readFile()
        encrypt()
    }

    private fun encrypt(){
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        val file = File(this.getExternalFilesDir("text"),"test.txt")
        if (file.exists()){
            file.mkdir()
        }
        val file1 = File("test.txt")
        val write = FileWriter(file1)
        write.append("hello")
        write.flush()
        write.close()

        val encryptedFile = EncryptedFile.Builder(
            File(this.getExternalFilesDir("text"),"test.txt"),
            this,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val content = encryptedFile.openFileInput().bufferedReader()
        for (i in content.readLine()){
            Log.d("encrypt",i.toString())
        }
    }
    private fun readFile(){
        val  reader :BufferedReader? = null
        reader.use { reader ->
            val reader =  BufferedReader(InputStreamReader(assets.open("sample.txt")))
            val array = arrayListOf<String>()
            for (i in reader.readLine()){
                array.add(i.toString())
            }
            tvHello.text = array.toString()
        }
    }

}
