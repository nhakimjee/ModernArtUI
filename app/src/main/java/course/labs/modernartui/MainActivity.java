package course.labs.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String MOMA_URL = "http://www.moma.org/m#home";

    private TextView panelOne;
    private TextView panelTwo;
    private TextView panelThree;
    private TextView panelFive;

    private int panelOneBaseColor;
    private int panelTwoBaseColor;
    private int panelThreeBaseColor;
    private int panelFiveBaseColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        panelOne = findViewById(R.id.panelOne);
        panelTwo = findViewById(R.id.panelTwo);
        panelThree = findViewById(R.id.panelThree);
        panelFive = findViewById(R.id.panelFive);

        panelOneBaseColor = ((ColorDrawable) panelOne.getBackground()).getColor();
        panelTwoBaseColor = ((ColorDrawable) panelTwo.getBackground()).getColor();
        panelThreeBaseColor = ((ColorDrawable) panelThree.getBackground()).getColor();
        panelFiveBaseColor = ((ColorDrawable) panelFive.getBackground()).getColor();

        final SeekBar seekbar = findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float factor = calculateFactor(i);
//                Log.d(TAG, "seeker moved to "+i+" , mapped to "+factor);

                int darkerColorOne = darker(panelOneBaseColor, 1-factor);
                ((ColorDrawable) panelOne.getBackground()).setColor(darkerColorOne);

                int darkerColorTwo = darker(panelTwoBaseColor, 1-factor);
                ((ColorDrawable) panelTwo.getBackground()).setColor(darkerColorTwo);

                int darkerColorThree = darker(panelThreeBaseColor, 1-factor);
                ((ColorDrawable) panelThree.getBackground()).setColor(darkerColorThree);

                int darkerColorFive = darker(panelFiveBaseColor, 1-factor);
                ((ColorDrawable) panelFive.getBackground()).setColor(darkerColorFive);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.more_information) {
            // Create a new AlertDialogFragment
            DialogFragment infoDialog = InfoDialogFragment.newInstance();

            // Show AlertDialogFragment
            infoDialog.show(getFragmentManager(), null);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    private void gotoMOMA() {
//        Intent momaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MOMA_URL));
//        startActivity(momaIntent);
//    }

    private float calculateFactor(int progress) {
        return ((0.5f * progress) / (100));
    }

    private static int darker(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

    // Class that creates the AlertDialog
    public static class InfoDialogFragment extends DialogFragment {

        public static InfoDialogFragment newInstance() {
            return new InfoDialogFragment();
        }

        // Build InfoDialog using InfoDialog.Builder
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage("Inspired by Modern Art UI sample!!\n   Click below to learn more")

                    // User cannot dismiss dialog by hitting back button
                    .setCancelable(false)

                    // Set up Yes Button
                    .setPositiveButton("Visit MOMA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
//                                    ((MainActivity) getActivity())
//                                            .gotoMOMA();
                                    Intent momaIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MOMA_URL));
                                    startActivity(momaIntent);
                                }
                            })

                    // Set up No Button
                    .setNegativeButton("Not now",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        final DialogInterface dialog, int id) {
                                    return;
                                }
                            }).create();
        }

        @Override
        public void onStart() {
            super.onStart();
            TextView messageView = (TextView) getDialog().findViewById(android.R.id.message);
            messageView.setGravity(Gravity.CENTER);
        }
    }

}
