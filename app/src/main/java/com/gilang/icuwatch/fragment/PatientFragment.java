package com.gilang.icuwatch.fragment;

import android.content.DialogInterface;
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

import java.util.Random;

/**
 * Created by Gilang on 13/04/2016.
 */
public class PatientFragment extends Fragment {

	public static final int FOR_PATIENT = 100;
	public static final int FOR_DOCTOR = 101;
	private AppCompatActivity activity;
	private TextView txtHeartBeat, txtTemperature, txtName, txtGender, txtAge, txtIndication;
	private Button btnFinish, btnHandle, btnDummyHandle, btnDummyDone;
	private Toolbar toolbar;
	int type;
	AlertDialog dialog;
	CountDownTimer timer;
	Random r;

	public PatientFragment(){}

	public static PatientFragment newInstance(AppCompatActivity activity, int type){
		PatientFragment fragment = new PatientFragment();
		fragment.activity = activity;
		fragment.r = new Random();
		fragment.type = type;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_patient, parent, false);

		bindViews(v);
		activity.setSupportActionBar(toolbar);

		timer = new CountDownTimer(3600000, 1000){
			int cycle = 0;

			@Override
			public void onTick(long millisUntilFinished) {
				updateView(cycle);
				cycle++;
			}

			@Override
			public void onFinish() {

			}
		};
		timer.start();

		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		if(type == FOR_DOCTOR) {
			dialog = builder.setMessage("Justin Bieber Condition has drop!")
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
		}else{
			dialog = builder.setMessage(txtName.getText().toString() + " Condition has drop!")
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).create();
		}

		return v;
	}

	@Override
	public void onDetach() {
		timer.cancel();
		super.onDetach();
	}

	public void updateView(int cycle){
		int heartbeat = r.nextInt(43) + 59;
		int temperature = r.nextInt(5) + 31;

		txtHeartBeat.setText(heartbeat + " bpm");
//		if(heartbeat < 60 || heartbeat > 100){
//			dialog.show();
//		}
		if(cycle % 60 == 0){
			txtTemperature.setText(temperature + "°C");
//			if(temperature < 32 || temperature > 34){
//				dialog.show();
//			}
		}
	}

	public void bindViews(View v){
		txtHeartBeat = (TextView) v.findViewById(R.id.txt_heartbeat);
		txtTemperature = (TextView) v.findViewById(R.id.txt_temperature);
		txtName = (TextView) v.findViewById(R.id.txt_name);
		txtGender = (TextView) v.findViewById(R.id.txt_gender);
		txtAge = (TextView) v.findViewById(R.id.txt_age);
		txtIndication = (TextView) v.findViewById(R.id.txt_indication);
		btnFinish = (Button) v.findViewById(R.id.btn_finish);
		btnHandle = (Button) v.findViewById(R.id.btn_handle);
		btnDummyHandle = (Button) v.findViewById(R.id.btn_dummy_handle);
		btnDummyDone = (Button) v.findViewById(R.id.btn_dummy_done);
		toolbar = (Toolbar) v.findViewById(R.id.toolbar);

		if(type == FOR_PATIENT){
			btnFinish.setVisibility(View.GONE);
			btnHandle.setVisibility(View.GONE);
			btnDummyDone.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					final AlertDialog dialog = builder.setMessage("Pasien telah selesai ditangani")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}).create();
					dialog.show();
				}
			});

			btnDummyHandle.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(activity);
					final AlertDialog dialog = builder.setMessage("Pasien sedang ditangani")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							}).create();
					dialog.show();
				}
			});
		}else{
			btnFinish.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(activity, "Finished", Toast.LENGTH_SHORT).show();
				}
			});

			btnHandle.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(activity, "Patient Handled", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}
