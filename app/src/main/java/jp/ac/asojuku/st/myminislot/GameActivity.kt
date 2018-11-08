package jp.ac.asojuku.st.myminislot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.drawable.Drawable
import android.R.array
import android.content.res.TypedArray
import android.support.v4.content.res.TypedArrayUtils.getResourceId
import android.widget.Toast


class GameActivity : AppCompatActivity() {

    val img: Array<Int> = arrayOf(
            R.drawable.cherry,
            R.drawable.banana,
            R.drawable.bar,
            R.drawable.bigwin,
            R.drawable.grape,
            R.drawable.lemon,
            R.drawable.orange,
            R.drawable.seven,
            R.drawable.waltermelon
    );

    val flg: Array<Int> = arrayOf<Int>(-1,-1,-1)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        setCoin2();

        btn_back.setOnClickListener{onBackTap()};
        btn_slot1.setOnClickListener{onSlotTap(0)};
        btn_slot2.setOnClickListener{onSlotTap(1)};
        btn_slot3.setOnClickListener{onSlotTap(2)};

    }

    fun onBackTap() {

        val intent = Intent(this, MainActivity::class.java);
        val data1 = -1;
        intent.putExtra("EXTRA_DATA", data1);
        this.startActivity(intent);

    }

    fun onSlotTap(int: Int){

        if (flg[int] == -1) {

            var i = (Math.random() * 10).toInt();
            flg[int]=i;

            when(int){

                0 -> btn_slot1.setImageResource(img[i]);
                1 -> btn_slot2.setImageResource(img[i]);
                2 -> btn_slot3.setImageResource(img[i]);

            }

            if(flg[0] != -1 && flg[1] != -1 && flg[2] != -1) {
                checkResult();
            }
        }

    }

    fun checkResult(){

        var bed=getBedCoin2();
        var my=getMyCoin2();
        var plus=0;

        //get
        if (flg[0] == flg[1] && flg[1] == flg[2]){

            setResultchange(1);

            if(flg[0]==7){
                plus=20;
            }else if(flg[0]==3){
                plus=10;
            }else if(flg[0]==2){
                plus=5;
            }else{
                plus=2;
            }

        }else if(flg[0]==flg[1] || flg[1]==flg[2] || flg[0]==flg[2]){
            if (flg[0]==7 || flg[1]==7){
                plus=3;
            }else{
                plus=1;
            }
        }else if(flg[0]==8||flg[1]==8||flg[2]==8){
            plus=1;
        }else if(flg[0]==6&&flg[1]==0&&flg[2]==5){
            plus=30;
        }else if(flg[0]==8&&flg[1]==1&&flg[2]==4){
            plus=10;
        }else

        //lost
        {

            setResultchange(0);
            plus=(-10);
        }

        var myplus = my+plus*bed;

        putMyCoin2(myplus);

        setCoin2();
    }

    fun setCoin2(){

        var my = getMyCoin2();
        var bed = getBedCoin2();

        txt_my_coin2.setText(my.toString());
        txt_bed_coin2.setText(bed.toString());
    }

    fun getMyCoin2(): Int {

        val pref=PreferenceManager.getDefaultSharedPreferences(this);
        var my=pref.getInt("MY_COIN",1000);

        return my;

    }

    fun getBedCoin2(): Int {

        val pref=PreferenceManager.getDefaultSharedPreferences(this);
        var bed=pref.getInt("BED_COIN",10);

        return bed;

    }

    fun putMyCoin2(my : Int){

        val pref=PreferenceManager.getDefaultSharedPreferences(this);
        val editor=pref.edit();



        editor.putInt("MY_COIN",my).apply();

    }

    /*fun putBedCoin(bed : Int){

        val pref=PreferenceManager.getDefaultSharedPreferences(this);
        val editor=pref.edit();

        editor.putInt("BED_COIN", bed).apply();

    }*/

    fun setResultchange(int: Int) {

        when(int){
            1 -> img_result.setImageResource(R.drawable.osatsu_money_yamadumi);
            0 -> img_result.setImageResource(R.drawable.gamble_chuudoku_izonsyou);
        }

        when(int){
            1 -> txt_result.setText("おめ");
            0 -> txt_result.setText("どんまい");
        }

    }
}
