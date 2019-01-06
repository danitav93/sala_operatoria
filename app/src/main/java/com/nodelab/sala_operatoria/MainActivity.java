package com.nodelab.sala_operatoria;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;

import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;


import com.nodelab.sala_operatoria.database.entity.Turno;
import com.nodelab.sala_operatoria.utility.Methods;
import com.nodelab.sala_operatoria.viewModel.TurnoViewModel;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static TurnoViewModel turnoViewModel;
    private static List<Turno> turni;
    protected String lastDate = "giovedì, 29/11/2018";
    private Intent intentSpeech;
    protected Turno turnoDiOggi;



    private CalendarAdapter mAdapter;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 1 && intentSpeech == null) {

                    if (turnoDiOggi == null) {
                        showAlertDialogButtonClicked();
                    } else {
                        displaySpeechRecognizer();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        turnoViewModel = ViewModelProviders.of(this).get(TurnoViewModel.class);


    }

    public CalendarAdapter getAdapter() {
        return mAdapter;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void updateLastDate() {
        Turno lastTurno = turnoViewModel.getLastInsertedTurno();
        if (lastTurno != null) {
            setLastDate(lastTurno.getData());
        } else {
            lastDate = Methods.getYesterdayDate();
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView;

            assert getArguments() != null;
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {
                rootView = inflater.inflate(R.layout.fragment_emergency_mode, container, false);

                final RelativeLayout mainLayout = rootView.findViewById(R.id.fragment_emergency_mode_main_layout);

                //setto le animazioni con il colore
                int colorFrom = getResources().getColor(R.color.white);
                int colorTo = getResources().getColor(R.color.orange);
                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(700); // milliseconds
                colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
                colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        mainLayout.setBackgroundColor((int) animator.getAnimatedValue());
                    }
                });
                colorAnimation.start();

            } else {

                rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

                final FloatingActionButton fab = rootView.findViewById(R.id.fab);

                

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Turno turno = new Turno();
                        turno.setData(Methods.getNextdate(((MainActivity) getActivity()).getLastDate()));
                        final FragmentManager fm = getActivity().getSupportFragmentManager();
                        SaveTurnoDialog.display(fm, turno, turnoViewModel, (MainActivity) getActivity());

                    }
                });

                RecyclerView mRecyclerView = rootView.findViewById(R.id.fragment_calendar_recycle_view);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                mRecyclerView.setHasFixedSize(true);

                // use a linear layout manager
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                turni = new ArrayList<>();
                ((MainActivity) getActivity()).setmAdapter(new CalendarAdapter(turni, (AppCompatActivity) getActivity(), turnoViewModel));
                mRecyclerView.setAdapter(((MainActivity) getActivity()).getAdapter());
                new GetTurniAsyncTask(((MainActivity) getActivity()).getAdapter(), ((MainActivity) getActivity())).execute();


           /* TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));*/


            }

            return rootView;

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    private static class GetTurniAsyncTask extends AsyncTask<Void, Void, Boolean> {

        CalendarAdapter calendarAdapter;
        WeakReference<MainActivity> mainActivity;

        private GetTurniAsyncTask(CalendarAdapter calendarAdapter, MainActivity mainActivity) {
            this.calendarAdapter = calendarAdapter;
            this.mainActivity = new WeakReference<>(mainActivity);
        }

        @Override
        protected Boolean doInBackground(final Void... params) {
            mainActivity.get().setTurnoDiOggi(turnoViewModel.getTurnoByData(Methods.getTodayDate()));
            if (mainActivity.get().getTurnoDiOggi() != null) {
                turni = turnoViewModel.getTurniOverOggi(mainActivity.get().getTurnoDiOggi().getUid());
            }

            mainActivity.get().updateLastDate();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {

            if (calendarAdapter != null) {
                calendarAdapter.setTurni(turni);
            }

        }
    }

    private static class ChiamaTask extends AsyncTask<Void, Void, Boolean> {

        WeakReference<MainActivity> mainActivity;
        Intent intent;
        private ChiamaTask(MainActivity mainActivity) {
            this.mainActivity = new WeakReference<>(mainActivity);
        }

        @Override
        protected Boolean doInBackground(final Void... params) {
            Turno turno = turnoViewModel.getTurnoByData(Methods.getTodayDate());
            String numero;
            if (Methods.getHour() > 8 && Methods.getHour() < 20) {
                numero = turno.getNumeroDottoreGiorno();
            } else {
                numero = turno.getNumeroDottoreNotte();
            }
            intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numero));
            if (ActivityCompat.checkSelfPermission(mainActivity.get(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity.get(),
                        Manifest.permission.READ_CONTACTS)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(mainActivity.get(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            SPEECH_REQUEST_CODE);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                mainActivity.get().startActivity(intent);
                mainActivity.get().getViewPager().setCurrentItem(0);
            }
        }

    }

    public void setmAdapter(CalendarAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }


    private static final int SPEECH_REQUEST_CODE = 0;

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        intentSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intentSpeech, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

            if (spokenText.equalsIgnoreCase("SALA OPERATORIA")) {
                new ChiamaTask(this).execute();
                //showAlertDialogButtonClicked();

                intentSpeech = null;
            } else {
                displaySpeechRecognizer();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showAlertDialogButtonClicked() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Attenzione!");
        builder.setMessage("Inserire il turno di oggi!!");

        // add a button
        builder.setPositiveButton("OK", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public Turno getTurnoDiOggi() {
        return turnoDiOggi;
    }

    public void setTurnoDiOggi(Turno turnoDiOggi) {
        this.turnoDiOggi = turnoDiOggi;
    }
}
