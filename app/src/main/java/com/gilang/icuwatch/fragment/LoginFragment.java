package com.gilang.icuwatch.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gilang.icuwatch.R;

/**
 * Created by Gilang on 13/04/2016.
 */
public class LoginFragment extends Fragment {

	private AppCompatActivity activity;
	private EditText formUser, formPassword;
	private Button btnLogin;

	public LoginFragment(){}

	public static LoginFragment newInstance(AppCompatActivity activity){
		LoginFragment fragment = new LoginFragment();
		fragment.activity = activity;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_login, parent, false);

		bindViews(v);

		return v;
	}

	public void bindViews(View v){
		formUser = (EditText) v.findViewById(R.id.form_user);
		formPassword = (EditText) v.findViewById(R.id.form_password);
		btnLogin = (Button) v.findViewById(R.id.btn_login);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String user = formUser.getText().toString().toLowerCase();
				String password = formPassword.getText().toString();
				if(user.contains("dr")){
					openDoctorPage();
				}else{
					openPatientPage();
				}
			}
		});
	}

	public void openDoctorPage(){
		activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, PatientListFragment.newInstance(activity)).commit();
	}

	public void openPatientPage(){
		activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, PatientFragment.newInstance(activity, PatientFragment.FOR_PATIENT)).commit();
	}
}
