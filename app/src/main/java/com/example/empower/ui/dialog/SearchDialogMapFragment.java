package com.example.empower.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.empower.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Objects;


/**
 * class name: SearchDialogMapFragment.java
 * function: Main aim of this dialog fragment is to display and let user choose filter in the venue search function
 */
public class SearchDialogMapFragment extends DialogFragment {
    private static final String TAG = "SearchDialogMapFragment";


    private View root;
    private FoldingCell foldingCell, foldingCell2;

    private EditText editText_postcode;
    private String inputPostcode;

    private CheckBox checkBox_sportVenue;
    private CheckBox checkBox_openSpace;
    private ArrayList<String> venueArrayList;

    private CheckBox checkBox_allSport;
    private CheckBox checkBox_football;
    private CheckBox checkBox_basketball;
    private CheckBox checkBox_cricket;
    private ArrayList<String> sportArrayList;



    private RadioGroup nearbyRadioGroup;
    private String nearbyResult;


    private Button button_searchResult;



    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.dialog_search_map, container, true);

        foldingCell = root.findViewById(R.id.folding_cell);
        foldingCell.initialize(1000, Color.GRAY, 4);

        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });


        foldingCell2 = root.findViewById(R.id.folding_cell_2);
        foldingCell2.initialize(1000, Color.GRAY, 3);

        foldingCell2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell2.toggle(false);
            }
        });


        editText_postcode = root.findViewById(R.id.dialog_inputPostcode);

        checkBox_sportVenue = root.findViewById(R.id.dialog_search_map_sport_venue);
        checkBox_openSpace = root.findViewById(R.id.dialog_search_map_open_space);

        checkBox_allSport = root.findViewById(R.id.dialog_search_map_allSport);
        checkBox_football = root.findViewById(R.id.dialog_search_map_football);
        checkBox_basketball = root.findViewById(R.id.dialog_search_map_basketball);
        checkBox_cricket = root.findViewById(R.id.dialog_search_map_cricket);

        nearbyRadioGroup = root.findViewById(R.id.dialog_search_map_radioGroup);

        button_searchResult = root.findViewById(R.id.dialog_search_map_confirm_button);



//        // venue array list management
        venueArrayList = new ArrayList<>();
//        checkBox_sportVenue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    venueArrayList.add("sport venue");
//                }
//            }
//        });
//
//        checkBox_sportVenue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    venueArrayList.add("open space");
//                }
//            }
//        });
//
//
//        // sport array list management
        sportArrayList = new ArrayList<>();

        checkBox_allSport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkBox_football.setChecked(true);
                    checkBox_basketball.setChecked(true);
                    checkBox_cricket.setChecked(true);
                }else {
                    checkBox_football.setChecked(false);
                    checkBox_basketball.setChecked(false);
                    checkBox_cricket.setChecked(false);
                }
            }
        });


//        checkBox_football.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    sportArrayList.add("football");
//                }
//            }
//        });
//
//        checkBox_basketball.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    sportArrayList.add("basketball");
//                }else {
//                    if (sportArrayList.contains("basketball")){
//                        sportArrayList.remove("basketball");
//                    }
//                }
//            }
//        });
//
//        checkBox_cricket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    sportArrayList.add("cricket");
//                }else {
//                    if (sportArrayList.contains("cricket")){
//                        sportArrayList.remove("cricket");
//                    }
//                }
//            }
//        });
//
        nearbyResult = null;
//        nearbyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                RadioButton radioButton = root.findViewById(checkedId);
//                nearbyResult = radioButton.getText().toString();
//            }
//        });



        button_searchResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputPostcode = null;
                inputPostcode = editText_postcode.getText().toString();

                if (checkBox_sportVenue.isChecked()){
                    venueArrayList.add("sport venue");
                }

                if (checkBox_openSpace.isChecked()){
                    venueArrayList.add("open space");
                }


                if (checkBox_football.isChecked()){
                    sportArrayList.add("football");
                }


                if (checkBox_basketball.isChecked()){
                    sportArrayList.add("basketball");
                }

                if (checkBox_cricket.isChecked()){
                    sportArrayList.add("cricket");
                }

                for (int i = 0; i< nearbyRadioGroup.getChildCount(); i++){
                    RadioButton radioButton = (RadioButton)nearbyRadioGroup.getChildAt(i);
                    if (radioButton.isChecked()){
                        nearbyResult = radioButton.getText().toString();
                        break;
                    }
                }



                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("inputPostcode", inputPostcode);
                bundle.putStringArrayList("venueArrayList", venueArrayList);
                bundle.putStringArrayList("sportArrayList", sportArrayList);
                bundle.putString("nearbyResult", nearbyResult);
                intent.putExtras(bundle);
                Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

                // reset postcode input to empty when closing this search dialog
                editText_postcode.setText("");
                dismiss();
            }
        });


        return root;

    }

}
