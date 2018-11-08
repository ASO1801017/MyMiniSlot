package jp.ac.asojuku.st.myminislot

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.nfc.NfcAdapter.EXTRA_DATA



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //共有プリファレンスの初期化
        /*val pref=PreferenceManager.getDefaultSharedPreferences(this);
        val editor=pref.edit();
        editor.clear().apply();*/

        /*editor.putInt("MY_COIN",1000)
                .putInt("BED_COIN",10)
                .apply();*/

        val intent = intent
        val data1 = intent.getIntExtra("EXTRA_DATA", 0)

        if (data1!=-1) {
            onRestTap();
        }


        btn_reset.setOnClickListener{onRestTap()};
        btn_start.setOnClickListener{onStartTap()};
        btn_up.setOnClickListener{onUpTap()};
        btn_down.setOnClickListener{onDownTap()};

    }

    override fun onResume() {

        super.onResume();

        setCoin();
    }

    fun onRestTap(){

        val pref=PreferenceManager.getDefaultSharedPreferences(this);
        val editor=pref.edit();
        editor.clear().apply();

        editor.putInt("MY_COIN",1000)
                .putInt("BED_COIN",10)
                .apply();

        setCoin();

    }

    fun onStartTap(){

        var my =getMyCoin();

        if(my>0) {
            val intent = Intent(this, GameActivity::class.java);
            this.startActivity(intent);
        }
    }

    fun onUpTap(){

        var my=getMyCoin();
        var bed=getBedCoin();

        if(my>bed) {
            putBedCoin(bed+10)
        }

        setCoin();

    }

    fun onDownTap(){

        var bed = getBedCoin();

        if(bed>10) {
            putBedCoin(bed-10);
        }

        setCoin();

    }

    fun setCoin(){

        var my = getMyCoin();
        var bed = getBedCoin();

        txt_my_coin.setText(my.toString());
        txt_bed_coin.setText(bed.toString());
    }

    fun getMyCoin(): Int {

        val pref=PreferenceManager.getDefaultSharedPreferences(this);
        var my=pref.getInt("MY_COIN",1000);

        return my;

    }

    fun getBedCoin(): Int {

        val pref=PreferenceManager.getDefaultSharedPreferences(this);
        var bed=pref.getInt("BED_COIN",10);

        return bed;

    }

    fun putMyCoin(my : Int){

        val pref=PreferenceManager.getDefaultSharedPreferences(this);
        val editor=pref.edit();

        editor.putInt("MY_COIN",my ).apply();

    }

    fun putBedCoin(bed : Int){

        val pref=PreferenceManager.getDefaultSharedPreferences(this);
        val editor=pref.edit();

        editor.putInt("BED_COIN", bed).apply();

    }

}
