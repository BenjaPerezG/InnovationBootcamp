package com.example.innovation

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.widget.Toast
/*import com.google.android.material.snackbar.Snackbar*/
import androidx.appcompat.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_choose.*
import kotlinx.android.synthetic.main.content_choose.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*

class choose : AppCompatActivity() {
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mmSocket: BluetoothSocket? = null
    private var mmDevice: BluetoothDevice? = null
    private var mmOutputStream: OutputStream? = null
    private var mmInputStream: InputStream? = null
    private var workerThread: Thread? = null
    private var readBuffer: ByteArray? = null
    private var readBufferPosition: Int = 0
    private var counter: Int = 0
    @Volatile
    internal var stopWorker: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val PRIVATE_MODE = 0
        val PREFERENCE_NAME: String = getString(R.string.settings_preferences)
        val PREFERENCE_VALUE: String = getString(R.string.timer_preferences)
        val shared_preference: SharedPreferences = getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        setSupportActionBar(toolbar)
        title = "Clean n' Green"
        val timerIntent = Intent(applicationContext, timer::class.java)



        button2.setOnClickListener {
            try {
                Connect()
                sendData("0")
            }catch (IOException: Exception){
                showMessage("FAIL")
            }
            shared_preference.edit().putLong(PREFERENCE_VALUE, 30000).apply()
            shared_preference.edit().putString(getString(R.string.average_preferences), showerTimeText.text.toString()).commit()
            startActivity(timerIntent)
        }
        button3.setOnClickListener {
            try {
                Connect()
                shared_preference.edit().putLong(PREFERENCE_VALUE, 300000).apply()
                sendData("1")
                startActivity(timerIntent)
                shared_preference.edit().putString(getString(R.string.average_preferences), showerTimeText.text.toString()).commit()
            }catch (IOException: Exception){
                showMessage("FAIL")
            }
        }
        button4.setOnClickListener {
            try {
                Connect()
                shared_preference.edit().putLong(PREFERENCE_VALUE, 600000).apply()
                sendData("2")
                startActivity(timerIntent)
                shared_preference.edit().putString(getString(R.string.average_preferences), showerTimeText.text.toString()).commit()
            }catch (IOException: Exception){
                showMessage("FAIL")
            }
        }
        button5.setOnClickListener {
            try {
                Connect()
                shared_preference.edit().putLong(PREFERENCE_VALUE, 900000).apply()
                sendData("3")
                startActivity(timerIntent)
                shared_preference.edit().putString(getString(R.string.average_preferences), showerTimeText.text.toString()).commit()
            }catch (IOException: Exception){
                showMessage("FAIL")
            }
        }

    }

    fun sendData(msg: String) {
        mmOutputStream!!.write(Integer.parseInt(msg))
        arduinoMessage()
    }
    fun showMessage(theMsg: String) {
        val msg = Toast.makeText(
            baseContext,
            theMsg, Toast.LENGTH_LONG / 160
        )
        msg.show()
    }
    fun arduinoMessage() {
        val msg = Arrays.toString(readBuffer)
    }
    @Throws(IOException::class)
    fun openBT() {
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        val list = mmDevice!!.uuids//Standard //SerialPortService ID
        mmSocket = mmDevice!!.createRfcommSocketToServiceRecord(uuid)
        mmSocket!!.connect()
        mmOutputStream = mmSocket!!.outputStream
        mmInputStream = mmSocket!!.inputStream
        beginListenForData()
    }
    fun beginListenForData() {
        val handler = Handler()
        val delimiter: Byte = 10 //This is the ASCII code for a newline character

        stopWorker = false
        readBufferPosition = 0
        readBuffer = ByteArray(1024)
        workerThread = Thread(Runnable {
            while (!Thread.currentThread().isInterrupted && !stopWorker) {
                try {
                    val bytesAvailable = mmInputStream!!.available()
                    if (bytesAvailable > 0) {
                        val packetBytes = ByteArray(bytesAvailable)
                        mmInputStream?.read(packetBytes)
                        for (i in 0 until bytesAvailable) {
                            val b = packetBytes[i]
                            if (b == delimiter) {
                                val encodedBytes = ByteArray(readBufferPosition)
                                readBuffer?.let {
                                    System.arraycopy(it, 0, encodedBytes, 0, encodedBytes.size)
                                }
                                val charset: Charset = Charsets.US_ASCII
                                val data = String(encodedBytes, charset)
                                readBufferPosition = 0
                            } else {
                                readBuffer?.let {
                                    it[readBufferPosition++] = b
                                }
                            }
                        }
                    }
                } catch (ex: IOException) {
                    stopWorker = true
                }

            }
        })

        workerThread?.start()
    }
    @Throws(IOException::class)
    fun closeBT() {
        stopWorker = true
        mmOutputStream!!.close()
        mmInputStream!!.close()
        mmSocket!!.close()
    }


    @Throws(IOException::class)
    internal fun findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!mBluetoothAdapter!!.isEnabled) {
            val enableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivity(enableBluetooth)
            mBluetoothAdapter!!.startDiscovery()
        }

        val pairedDevices = mBluetoothAdapter!!.bondedDevices
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                if (device.name == "HC-06") {
                    mmDevice = device
                    break
                }
            }
        }
    }
    private fun Connect(){
        try {
            findBT()
            openBT()
        } catch (ex: IOException) { }

    }
}
