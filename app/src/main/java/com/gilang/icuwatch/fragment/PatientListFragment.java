package com.gilang.icuwatch.fragment;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gilang.icuwatch.R;
import com.gilang.icuwatch.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Gilang on 13/04/2016.
 */
public class PatientListFragment extends Fragment {

	AppCompatActivity activity;
	List<Patient> patients;
	Toolbar toolbar;
	ViewGroup container;
	AlertDialog dialog;
	CountDownTimer timer;
	Button btnDummyWarn;
	Random r;

	public PatientListFragment(){}

	public static PatientListFragment newInstance(AppCompatActivity activity){
		PatientListFragment fragment = new PatientListFragment();
		fragment.activity = activity;
		fragment.patients = new ArrayList<>();
		fragment.r = new Random();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_patient_list, parent, false);

		btnDummyWarn = (Button) v.findViewById(R.id.btn_dummy_warn);
		btnDummyWarn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createDialog("Justin Bieber");
			}
		});

		container = (ViewGroup) v.findViewById(R.id.container);
		toolbar = (Toolbar) v.findViewById(R.id.toolbar);
		activity.setSupportActionBar(toolbar);

		if(patients.size() == 0)
			addDummyData();

		timer = new CountDownTimer(3600000, 1000){
			int cycle = 0;

			@Override
			public void onTick(long millisUntilFinished) {
				updateList(container, cycle);
				cycle++;
			}

			@Override
			public void onFinish() {

			}
		};
		timer.start();

		return v;
	}

	@Override
	public void onDetach() {
		timer.cancel();
		super.onDetach();
	}

	public void createDialog(String name){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		dialog = builder.setMessage(name + " Condition has drop!")
				.setPositiveButton("Handle", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create();
		dialog.show();
	}

	public void updateList(ViewGroup parent, int cycle){
		LayoutInflater inflater = activity.getLayoutInflater();
		container.removeAllViews();

		for(Patient p : patients){
			final Patient fp = p;
			int heartbeat = r.nextInt(10) + 90;
			int temperature = r.nextInt(1) + 36;

			if(cycle > 30 && cycle < 40){
				heartbeat = 60;
			}
			if(cycle > 60 && cycle <= 70){
				temperature = 31;
			}

			p.heartbeat = heartbeat;
			p.temperature = temperature;

			Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/Brandon_med.otf");

			View child = inflater.inflate(R.layout.card_patient, parent, false);
			TextView txtName, txtStatus, txtKamar;
			ViewGroup card;
			final Button btnHandle;

			txtName = (TextView) child.findViewById(R.id.txt_name);
			txtStatus = (TextView) child.findViewById(R.id.txt_status);
			txtKamar = (TextView) child.findViewById(R.id.txt_kamar);
			btnHandle = (Button) child.findViewById(R.id.btn_handle);
			txtName.setTypeface(font);
			txtStatus.setTypeface(font);
			txtKamar.setTypeface(font);
			btnHandle.setTypeface(font);

			card = (ViewGroup) child.findViewById(R.id.card);
			if(p.status == 1){
				btnHandle.setText("DONE");
			}else if(p.status == 0){
				btnHandle.setText("HANDLE");
			}

			txtName.setText(p.name);
			txtStatus.setText(p.heartbeat + " bpm | " + p.temperature + "°C");
			txtKamar.setText("Kamar " + p.room);
			btnHandle.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(btnHandle.getText().toString().equals("HANDLE")) {
						Toast.makeText(activity, "Patient Handled", Toast.LENGTH_SHORT).show();
						fp.status = 1;
					}else{
						fp.status = 0;
					}
				}
			});

			card.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, PatientFragment.newInstance(activity, PatientFragment.FOR_DOCTOR, fp)).addToBackStack(null).commit();
				}
			});

			container.addView(child);
		}
	}

	public void addDummyData(){
		int roomNum = r.nextInt(100) + 200;
		patients.add(new Patient("BARRACK OBAMA", "Male", "Hard to Breath", 30, roomNum));
		patients.add(new Patient("JACK DANIEL", "Male", "Hard to Breath", 45, roomNum + 20));
		patients.add(new Patient("JUSTIN BIEBER", "Male", "Hard to Breath", 23, roomNum + 67));
	}


}
