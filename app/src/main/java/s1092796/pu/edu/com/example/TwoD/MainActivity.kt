package s1092796.pu.edu.com.example.TwoD

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.*
import kotlinx.android.synthetic.main.activity_main.*
import s1092796.pu.edu.com.example.TwoD.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    var secondsLeft:Int = 1000  //倒數

    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txv.text = secondsLeft.toString()
        binding.btnStart.isEnabled = true
        binding.btnStop.isEnabled = false

        binding.btnStart.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                job = GlobalScope.launch(Dispatchers.Main) {
                    while(secondsLeft > 0) {
                        secondsLeft--
                        binding.txv.text = secondsLeft.toString()
                        binding.btnStart.isEnabled = false
                        binding.btnStop.isEnabled = true
                        delay(25)
                    }
                }
            }
        })

        binding.btnStop.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                job.cancel()
                binding.btnStart.isEnabled = true
                binding.btnStop.isEnabled = false
            }
        })

    }
    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (binding.btnStart.isEnabled == false){
            job = GlobalScope.launch(Dispatchers.Main) {
                while(secondsLeft > 0) {
                    secondsLeft--
                    binding.txv.text = secondsLeft.toString()
                    delay(25)
                }
            }
        }
    }
}